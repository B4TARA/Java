package by.baturel.spring.security.jwt;

import io.jsonwebtoken.JwtException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)
    throws  IOException,ServletException, JwtException {

        if(request.getCookies() == null)
        {
            chain.doFilter(request, response);
            return;
        }
        Optional<Cookie> cookieOptional = List.of(request.getCookies()).stream().filter(x -> x.getName().equals("token")).findFirst();
        if(cookieOptional.isEmpty())
        {
            chain.doFilter(request, response);
            return;
        }

        String cookie = cookieOptional.get().getValue().replace("%20"," ");
        if (isEmpty(cookie) || !cookie.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }


        final String token = cookie.split(" ")[1].trim();
        if (!jwtTokenUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsername(token));
        if (userDetails == null) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, "",
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

         SecurityContextHolder.getContext().setAuthentication(authentication);
       chain.doFilter(request, response);
    }

}
