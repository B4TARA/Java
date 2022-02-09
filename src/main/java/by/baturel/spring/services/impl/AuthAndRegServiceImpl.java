package by.baturel.spring.services.impl;

import by.baturel.spring.DTO.RegistrationForm;
import by.baturel.spring.models.UsersEntity;
import by.baturel.spring.repositories.UsersEntityRepository;
import by.baturel.spring.services.AuthAndRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthAndRegServiceImpl implements AuthAndRegService {

    UsersEntityRepository userRepository;

    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthAndRegServiceImpl(UsersEntityRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void register(String username, String email, String password) throws Exception {
        List<UsersEntity> users = userRepository.findAll();
        if (users.stream().filter(x -> x.geteMail().equals(email)).count() != 0) {
            throw new Exception("User has already registered email");
        }
        if (users.stream().filter(x -> x.getUserLogin().equals(username)).count() != 0) {
            throw new Exception("User has already registered username");
        }
        if(userRepository.countRows() == 0)
            userRepository.add(true,username, passwordEncoder.encode(password),
                    email);
        else
            userRepository.add(false,username, passwordEncoder.encode(password),
                   email);

        Optional<UsersEntity> userInserted = userRepository.findByName(username);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Optional<UsersEntity> getById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UsersEntity> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void editUser(UsersEntity user) {
        userRepository.update(Math.toIntExact(user.getId()),user.isAdmin(),user.getUserLogin(), user.getUserPassword(),user.geteMail());
    }

    @Override
    public List<UsersEntity> getAll() {
        return userRepository.findAll();
    }

}
