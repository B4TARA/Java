package by.baturel.spring.security.jwt;
import by.baturel.spring.logging.Loggable;
import by.baturel.spring.models.UsersEntity;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {

    @Loggable
    public static JwtUser create(UsersEntity user) {
        ArrayList<String> authorities = new ArrayList<>();
        authorities.add(user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER");
        return new JwtUser(
                Math.toIntExact(user.getId()),
                user.getUserLogin(),
                user.getUserPassword(),
                mapToGrantedAuthorities(authorities),
                true
        );
    }

    @Loggable
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role)
                ).collect(Collectors.toList());
    }
}
