package pdp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pdp.domains.AuthUser;
import pdp.domains.City;
import pdp.domains.Weather;
import pdp.service.AdminService;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/usersList")
    public String usersListPage(Model model) {
        model.addAttribute("authUsers", adminService.getAllUsers());
        return "admin/usersList";
    }

    @GetMapping("/userDetails/{id}")
    public String userDetailsPage(@PathVariable Integer id, Model model) {
        model.addAttribute("detail", adminService.getUserDetails(id));
        return "admin/userDetails";
    }

    @GetMapping("/editUser/{id}")
    public String editUserPage(@PathVariable Integer id, Model model) {
        model.addAttribute("authUser", adminService.getUser(id));
        return "admin/editUser";
    }

    @PostMapping("/editUser")
    public String editUser(@Valid @ModelAttribute AuthUser authUser, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("userid", authUser.getId());
            return "admin/editUser";
        }
        adminService.editUser(authUser);
        return "redirect:/usersList";
    }

    @GetMapping("/editCity/{id}")
    public String editCityPage(@PathVariable Integer id, Model model) {
        model.addAttribute("city", adminService.getCity(id));
        return "admin/editCity";
    }

    @PostMapping("/editCity")
    public String editCity(@Valid @ModelAttribute City city, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("cityId", city.getId());
            return "admin/editCity";
        }
        adminService.editCity(city);
        return "redirect:/citiesList";
    }

    @GetMapping("/addCity")
    public String addCityPage(Model model) {
        model.addAttribute("city", new City());
        return "admin/addCity";
    }

    @PostMapping("/addCity")
    public String addCity(@Valid @ModelAttribute City city, BindingResult errors) {
        if (errors.hasErrors()) {
            return "admin/addCity";
        }
        adminService.addCity(city);
        return "redirect:/citiesList";
    }

    @GetMapping("/adminWeatherData/{id}")
    public String weatherDataPage(@PathVariable Integer id, Model model) {
        model.addAttribute("weatherData", adminService.getWeatherData(id));
        model.addAttribute("cityId", id);
        return "weatherData";
    }

    @GetMapping("/addWeatherData/{id}")
    public String addWeatherDataPage(@PathVariable Integer id, Model model) {
        model.addAttribute("city", adminService.getCity(id));
        model.addAttribute("weather", new Weather());
        return "admin/addWeatherData";
    }

    @PostMapping("/addWeatherData")
    public String addWeatherData(@Valid @ModelAttribute Weather weather, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("cityId", weather.getCityId());
            return "admin/addWeatherData";
        }
        adminService.addWeatherData(weather);
        return "redirect:/adminWeatherData/" + weather.getCityId();
    }

    @GetMapping("/editWeatherData/{id}")
    public String editWeatherDataPage(@PathVariable Integer id, Model model) {
        model.addAttribute("weather", adminService.getWeatherDataById(id));
        return "admin/editWeatherData";
    }

    @PostMapping("/editWeatherData")
    public String editWeatherData(@Valid @ModelAttribute Weather weather, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("dataId", weather.getId());
            return "admin/editWeatherData";
        }
        adminService.editWeatherData(weather);
        return "redirect:/adminWeatherData/" + weather.getCityId();
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUserPage(@PathVariable Integer id, Model model) {
        System.out.println("a: " + adminService.getUser(id));
        model.addAttribute("authUser", adminService.getUser(id));
        return "admin/deleteUser";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam Integer id) {
        adminService.deleteUser(id);
        return "redirect:/usersList";
    }

    @GetMapping("/deleteCity/{id}")
    public String deleteCityPage(@PathVariable Integer id, Model model) {
        model.addAttribute("city", adminService.getCity(id));
        return "admin/deleteCity";
    }

    @PostMapping("/deleteCity")
    public String deleteCity(@RequestParam Integer id) {
        adminService.deleteCity(id);
        return "redirect:/citiesList";
    }

    @GetMapping("/deleteWeatherData/{id}/{cityId}")
    public String deleteWeatherData(@PathVariable Integer id, @PathVariable Integer cityId) {
        adminService.deleteWeatherData(id);
        return "redirect:/adminWeatherData/" + cityId;
    }
}
