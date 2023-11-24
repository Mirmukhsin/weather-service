package pdp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pdp.domains.AuthUser;
import pdp.service.AuthUserService;
import pdp.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final AuthUserService authUserService;
    private final UserService userService;


    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,Model model) {
        model.addAttribute("errorMessage", error);
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("authUser", new AuthUser());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute AuthUser authUser, BindingResult errors) {
        if (errors.hasErrors()) {
            return "auth/register";
        }
        authUserService.saveUser(authUser);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }

    @GetMapping("/citiesList")
    public String citiesListPage(Model model) {
        model.addAttribute("cities", userService.getAllCities());
        return "citiesList";
    }

    @GetMapping("/subscribeCity/{id}")
    public String subscribeCityPage(@PathVariable Integer id) {
        userService.addUserSubsCities(id);
        return "redirect:/citiesList";
    }

    @GetMapping("/deleteSubsCity/{id}")
    public String deleteSubsCity(@PathVariable Integer id) {
        userService.deleteSubsCity(id);
        return "redirect:/myCities";
    }

    @GetMapping("/myCities")
    public String myCitiesPage(Model model) {
        model.addAttribute("myCities", userService.getUserSubsCities());
        return "user/myCities";
    }

    @GetMapping("/weatherData/{id}")
    public String weatherDataPage(@PathVariable Integer id, Model model, @RequestParam(required = false, defaultValue = "1") Integer kun) {
        model.addAttribute("weatherData", userService.getWeatherData(id, kun));
        return "weatherData";
    }
}
