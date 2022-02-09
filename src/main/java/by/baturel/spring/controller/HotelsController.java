package by.baturel.spring.controller;

import by.baturel.spring.DTO.FeedBackForm;
import by.baturel.spring.models.FeedbacksEntity;
import by.baturel.spring.models.HotelsEntity;
import by.baturel.spring.models.UsersEntity;
import by.baturel.spring.repositories.FeedbacksEntityRepository;
import by.baturel.spring.repositories.HotelsEntityRepository;
import by.baturel.spring.logging.Loggable;
import by.baturel.spring.repositories.UsersEntityRepository;
import by.baturel.spring.services.impl.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.beans.FeatureDescriptor;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;
import java.sql.Date;

@Slf4j
@RestController
@RequestMapping
public class HotelsController {

    private HotelsEntityRepository hotelsEntityRepository;
    private FeedbacksEntityRepository feedbacksEntityRepository;
    private UsersService usersService;
    private FeedBackForm feedBackForm;

    @Autowired
    public HotelsController(HotelsEntityRepository hotelsEntityRepository, FeedbacksEntityRepository feedbacksEntityRepository, UsersService usersService) {
        this.hotelsEntityRepository = hotelsEntityRepository;
        this.feedbacksEntityRepository = feedbacksEntityRepository;
        this.usersService = usersService;
    }

    @Loggable
    @GetMapping(value = {"/add"})
    public ModelAndView hotelsAdd(Model model) { return new ModelAndView("hotelsAdd"); }

    BigDecimal rating = new BigDecimal(4);
    @PostMapping(value = {"/add"})
    public ModelAndView hotelsPostAdd(@RequestParam String description, @RequestParam int rooms, @RequestParam String name, Model model) {
        HotelsEntity hotelsEntity = new HotelsEntity(rooms,rating,description, name);
    hotelsEntityRepository.saveAndFlush(hotelsEntity);
    return new ModelAndView("redirect:/index"); }

    @GetMapping("/hotelsDetails/{elid}/{rolee}/{usernamee}/{userid}")
    public ModelAndView hotelsDetails(@PathVariable("elid") long id, @PathVariable("rolee") String role, @PathVariable("usernamee") String user, @PathVariable("userid") Long userid, Model model) {
       if(!hotelsEntityRepository.existsById(id))
       {
           return new ModelAndView("redirect:/index");
       }
        Optional<HotelsEntity> hotel = hotelsEntityRepository.findById(id);
        ArrayList<HotelsEntity> res = new ArrayList<>();
        hotel.ifPresent(res::add);
        Collection<FeedbacksEntity> feedbacks = hotelsEntityRepository.findById(id).get().getFeedbacksById();
        ArrayList<FeedbacksEntity> feed = new ArrayList<>(feedbacks);
        ArrayList<FeedBackForm> feedbacksForm = new ArrayList<>();

        for(FeedbacksEntity feedbacks1 : feedbacks) {
            FeedBackForm feedBackForm = new FeedBackForm();
            feedBackForm.setComment(feedbacks1.getComment());
            feedBackForm.setUsername(feedbacks1.getUsersByUserId().getUserLogin());
            feedBackForm.setFeedbackDate(feedbacks1.getFeedbackDate());
            feedbacksForm.add(feedBackForm);
        }


        model.addAttribute("post", res);
        model.addAttribute("feed", feedbacksForm);
        model.addAttribute("role", role);
        model.addAttribute("username", user);
        model.addAttribute("userid", userid);
        return new ModelAndView("hotelsDetails"); }

    @GetMapping(value = {"/hotel/{id}/edit"})
    public ModelAndView hotelEdit(@PathVariable(value="id") long id,   Model model) {
        if(!hotelsEntityRepository.existsById(id))
        {
            return new ModelAndView("redirect:/index");
        }
        Optional<HotelsEntity> hotel = hotelsEntityRepository.findById(id);
        ArrayList<HotelsEntity> res = new ArrayList<>();
        hotel.ifPresent(res::add);
        model.addAttribute("post", res);
        return new ModelAndView("hotelEdit"); }

    @PostMapping(value = {"/hotel/{id}/edit"})
    public ModelAndView hotelPostEdit(@PathVariable(value="id") long id, @RequestParam String description, @RequestParam int rooms, @RequestParam String name, Model model) { HotelsEntity hotelsEntity = new HotelsEntity(rooms,rating,description, name);
        HotelsEntity hotel = hotelsEntityRepository.findById(id).orElseThrow();
        hotel.setName(name);
        hotel.setDescription(description);
        hotel.setRooms(rooms);
        hotelsEntityRepository.save(hotel);
        return new ModelAndView("redirect:/index"); }

    @PostMapping(value = {"/hotel/{id}/delete"})
    public ModelAndView hotelPostDelete(@PathVariable(value="id") long id, Model model) {
        HotelsEntity hotel = hotelsEntityRepository.findById(id).orElseThrow();
       hotelsEntityRepository.delete(hotel);
        return new ModelAndView("redirect:/index"); }

    @PostMapping(value = {"/search/{role}"})
    @Loggable
    public ModelAndView Search(@PathVariable(value="role") String role, @RequestParam(required = false) String search, Model model) throws Exception {
        int i = Integer.parseInt(search);
        Optional<HotelsEntity> hotel = hotelsEntityRepository.findByName(i);
        ArrayList<HotelsEntity> res = new ArrayList<>();
        hotel.ifPresent(res::add);
        if(!res.isEmpty())
        {
            model.addAttribute("search", res);
        }
        else  model.addAttribute("search", null);
        model.addAttribute("role",role);
        return new ModelAndView("indexSearch");
    }

    @GetMapping("/feedback/{elid}/{usernamee}/{userid}")
    public ModelAndView leaveFeedback(@PathVariable("elid") long id, @PathVariable("usernamee") String user, @PathVariable("userid") Long userid, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("username", user);
        model.addAttribute("userid", userid);
        return new ModelAndView("feedback"); }

    @PostMapping("/feedback/{elid}/{username}/{userid}")
    public ModelAndView leaveFeedbackPost(@RequestParam(required = false) String feedback, @PathVariable(value="username") String username, @PathVariable(value="elid") int id, @PathVariable(value="userid") Long userid,  Model model) {


        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        FeedbacksEntity Feedback = new FeedbacksEntity(Math.toIntExact(userid), Math.toIntExact(id), date, feedback);
        feedbacksEntityRepository.saveAndFlush(Feedback);
        return new ModelAndView("redirect:/index"); }
}
