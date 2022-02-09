package by.baturel.spring.controller;

import by.baturel.spring.DTO.RegistrationForm;
import by.baturel.spring.logging.Loggable;
import by.baturel.spring.models.HotelsEntity;
import by.baturel.spring.models.UsersEntity;
import by.baturel.spring.repositories.HotelsEntityRepository;
import by.baturel.spring.repositories.UsersEntityRepository;
import by.baturel.spring.services.impl.AuthAndRegServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
public class SignUpController {
    @Autowired
    private UsersEntityRepository usersEntityRepository;

    private final AuthAndRegServiceImpl usersService;

    @Autowired
    public SignUpController(AuthAndRegServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PostMapping(value = {"/addUser"})
    @Loggable
    public ModelAndView userPostAdd(@RequestParam(required = false) String username, @RequestParam(required = false) String email,  @RequestParam(required = false) String password, Model model) throws Exception {
        usersService.register(username, email, password);
        URI login = new URI("http://localhost:8080/signIn");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(login);
        return new ModelAndView("redirect:/index");
    }

    @GetMapping(value = {"/registration"})
    public ModelAndView registrationMain(Model model) {
        return new ModelAndView("registration");
    }


}
