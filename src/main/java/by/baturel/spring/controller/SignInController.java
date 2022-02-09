package by.baturel.spring.controller;

import by.baturel.spring.DTO.LogInForm;
import by.baturel.spring.exception.UserNameNotFoundException;
import by.baturel.spring.logging.Loggable;
import by.baturel.spring.models.UsersEntity;
import by.baturel.spring.security.jwt.JwtTokenProvider;
import by.baturel.spring.services.impl.AuthAndRegServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SignInController {

    private final AuthAndRegServiceImpl usersService;
    AuthenticationManager authenticationManager;
    JwtTokenProvider jwtTokenProvider;


    @Autowired
    public SignInController(AuthAndRegServiceImpl usersService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = {"/login"})
    public void userPostLogin(@RequestParam(required = false) String username, @RequestParam(required = false) String password, HttpServletResponse response, Model model) throws UserNameNotFoundException, IOException {

        Optional<UsersEntity> user = usersService.findByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("user with username: " + username + " not found");
        }

        List<SimpleGrantedAuthority> listAuth = new ArrayList<String>(Collections.singleton(user.get().isAdmin() ? "ROLE_ADMIN" : "ROLE_USER")).stream()
                .map(role ->
                        new SimpleGrantedAuthority(role)
                ).collect(Collectors.toList());            //выдавание роли


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.get().getUserLogin(),
                password));
        String token = jwtTokenProvider.createToken(username);
        List<String> roleNames = Collections.singletonList(user.get().isAdmin() ? "ROLE_ADMIN" : "ROLE_USER");
        Cookie cookieUserName = new Cookie("username",username);
        Cookie cookieToken = new Cookie("token","Bearer%20" + token);
        Cookie cookieRoles = new Cookie("roles",roleNames.stream().findFirst().get());
        Cookie cookieId = new Cookie("id",String.valueOf(user.get().getId()));
        cookieUserName.setMaxAge(Integer.MAX_VALUE);
        cookieToken.setMaxAge(Integer.MAX_VALUE);
        cookieRoles.setMaxAge(Integer.MAX_VALUE);
        cookieId.setMaxAge(Integer.MAX_VALUE);

        cookieUserName.setPath("/");
        cookieToken.setPath("/");
        cookieRoles.setPath("/");
        cookieId.setPath("/");

        response.addCookie(cookieUserName);
        response.addCookie(cookieToken);
        response.addCookie(cookieRoles);
        response.addCookie(cookieId);
        response.sendRedirect("/");
        }

    @Loggable
    @GetMapping(value = {"/login"})
    public ModelAndView loginMain(Model model) {
        return new ModelAndView("login");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<Object, Object>> handleException(Exception ex) {
        Map<Object, Object> response = new HashMap<>();
        response.put("errorMessage", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
