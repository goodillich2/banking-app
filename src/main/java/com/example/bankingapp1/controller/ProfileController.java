package com.example.bankingapp1.controller;



import com.example.bankingapp1.entity.user.User;
import com.example.bankingapp1.repository.UserRepository;
import com.example.bankingapp1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping()
    public String getProfile(Model model) throws Exception {

        //получаем email юзера
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(userEmail);

        //получаем обьект юзеря по имени ()
        User user = userService.findByEmail(userEmail);

        model.addAttribute("user", user);
        return "profile";
    }


    @GetMapping("/update")
    public String updateProfile(Model model) throws Exception {

        //получаем email юзера
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        //получаем обьект юзеря по email
        User user = userService.findByEmail(userEmail);

        model.addAttribute("user", user);
        return "updateProfiile";
    }


    @PostMapping("/update")
    public String updateProfile(@RequestParam(name = "firstName") String firstName,
                                @RequestParam(name = "lastName") String lastName,
                                @RequestParam(name = "email") String email
                                ) throws Exception {

        //получаем email юзера
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        //получаем обьект юзеря по email
        User user = userService.findByEmail(userEmail);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.flush();


        return "redirect:/profile";
    }
}
