package by.baturel.spring.security.jwt;



import by.baturel.spring.logging.Loggable;
import by.baturel.spring.models.UsersEntity;
import by.baturel.spring.repositories.UsersEntityRepository;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUserDetailsService implements UserDetailsService {
    UsersEntityRepository usersRepository;

    @Autowired
    public JwtUserDetailsService(UsersEntityRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @SneakyThrows
    @Override
    @Loggable
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> user = usersRepository.findByName(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        return JwtUserFactory.create(user.get());
    }
}
