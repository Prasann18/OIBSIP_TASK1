package com.app.onlineregistrationsystem.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.onlineregistrationsystem.dto.UserDto;
import com.app.onlineregistrationsystem.entity.Reservation;
import com.app.onlineregistrationsystem.entity.User;
import com.app.onlineregistrationsystem.service.ReservationService;
import com.app.onlineregistrationsystem.service.UserService;

import jakarta.validation.Valid;

@Controller
public class ApplicationController {

    private UserService userService;
    
    private ReservationService reservationService;

    private String username;
    
    private Long userId;
    
    private String pnrId;

    public ApplicationController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("userRedirect")
    public String userRedirect() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String currentUserName = auth.getName();
    	User user = userService.findByEmail(currentUserName);
    	userId = user.getId();
    	username = user.getName();
    	String role = userService.findRoleByEmail(currentUserName);
    	if (role.equals("ROLE_ADMIN")) {
    		return "redirect:/users";
    	} else {
    		return "redirect:/home";
    	}
    }
    
    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @PostMapping("/reservation/save")
    public String saveReservation(@Valid @ModelAttribute("reservation") Reservation reservation,
                               BindingResult result,
                               Model model){
        if (result.hasErrors()) {
            model.addAttribute("reservation", reservation);
            return "reservation";
        }
        reservation.setUser_id(userId);
        if (reservation.getDeparture_from().equals(reservation.getDeparture_to())) {
        	return "redirect:/reservation?error";
        }
        reservationService.saveReservationDeatails(reservation);
        return "redirect:/reservation?success";
    }

    @PostMapping("/reservation/cancel")
    public String cancelReservation(@Valid @ModelAttribute("reservation") Reservation reservation,
                               BindingResult result,
                               Model model){
        if (result.hasErrors()) {
            model.addAttribute("reservation", reservation);
            return "cancelReservation";
        }
        reservation.setUser_id(userId);
        pnrId = reservation.getPnr();
        boolean status = reservationService.cancelReservation(reservation.getPnr(), false);
        if (status) {
        	List<Reservation> reservations = reservationService.findAllReservationsByPnrId(pnrId);
            model.addAttribute("reservations", reservations);
            model.addAttribute("success", true);
        	return "cancelReservation";
        } else {
        	return "redirect:/cancelReservation?error";
        }
        
    }

    @PostMapping("/reservation/cancelProceed")
    public String proceedCancel(Model model){
    	boolean status = reservationService.cancelReservation(pnrId, true);
        if (status) {
        	return "redirect:/cancelReservation?successCancel";
        } else {
        	return "redirect:/cancelReservation?error";
        }
    }

    @PostMapping("/reservation/view")
    public String viewReservation(Model model){
    	return "redirect:/showReservation";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/showReservation")
    public String showReservation(Model model){
        List<Reservation> reservations = reservationService.findAllReservationsById(userId);
        model.addAttribute("reservations", reservations);
        return "showReservation";
    }

    @GetMapping("/home")
    public String showHome(Model model){
    	model.addAttribute("username", username);
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation(Model model){
    	Reservation reservation = new Reservation();
        model.addAttribute("reservation", reservation);
    	model.addAttribute("username", username);
        return "reservation";
    }

    @GetMapping("/cancelReservation")
    public String cancelReservation(Model model){
    	model.addAttribute("username", username);
        return "cancelReservation";
    }
}
