package by.baturel.spring.controller;


import by.baturel.spring.models.HotelsEntity;
import by.baturel.spring.models.UsersEntity;
import by.baturel.spring.repositories.HotelsEntityRepository;
import by.baturel.spring.logging.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j                            //логирование
@RestController
@RequestMapping
public class MainController {     //отвечает за обработку всех переходов на сайте (каждая функция обрабатывает определенный url

    @Autowired
    private HotelsEntityRepository hotelsEntityRepository;

    @Loggable
    @GetMapping(value = {"/", "/index"}) //обрабатываем стартовую страницу
    public ModelAndView index(Model model, HttpServletRequest request) {
        Iterable<HotelsEntity>  hotelsEntities = hotelsEntityRepository.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("hotelsEntities", hotelsEntities);
        log.info("/index was called");

        Optional<Cookie> cookieOptional = List.of(request.getCookies()).stream().filter(x -> x.getName().equals("username")).findFirst();
        Optional<Cookie> cookieOptionalRole = List.of(request.getCookies()).stream().filter(x -> x.getName().equals("roles")).findFirst();
        Optional<Cookie> cookieOptionalId = List.of(request.getCookies()).stream().filter(x -> x.getName().equals("id")).findFirst();
        if(cookieOptional.isEmpty())
        {
            model.addAttribute("user", null);
        }
        else
        {
            model.addAttribute("user", cookieOptional.get().getValue());
        }
        if(cookieOptionalRole.isEmpty())
        {
            model.addAttribute("role", null);
        }
        else
        {
            model.addAttribute("role", cookieOptionalRole.get().getValue());
        }
        if(cookieOptionalId.isEmpty())
        {
            model.addAttribute("id", null);
        }
        else
        {
            model.addAttribute("id", cookieOptionalId.get().getValue());
        }
        return modelAndView;
    }
    @Loggable
    @PostMapping(value = {"/about/{role}"})
    public ModelAndView about(Model model,@PathVariable(value="role") String role) {
        if(role.equals("null"))
        {
            model.addAttribute("role", null);
        }
        else model.addAttribute("role", role);
        return new ModelAndView("about");
    }




}
