package com.lex.Car.controller;

import com.lex.Car.DTO.CarDTO;
import com.lex.Car.model.Car;
import com.lex.Car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final CarService carService;

    public HomeController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String index(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Car> cars;

        if (search != null && !search.trim().isEmpty()) {
            cars = carService.searchCars(search);
        } else {
            cars = carService.getAllCars();
        }

        model.addAttribute("cars", cars);
        model.addAttribute("search", search);
        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("carDTO", new CarDTO());
        model.addAttribute("bodyTypes", new String[]{"Sedan", "SUV", "Hatchback", "Pickup", "Coupe", "Convertible"});
        model.addAttribute("engineTypes", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
        model.addAttribute("transmissions", new String[]{"Automatic", "Manual"});
        return "new";
    }

    @PostMapping("/save")
    public String saveCar(@Valid @ModelAttribute("carDTO") CarDTO carDTO,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bodyTypes", new String[]{"Sedan", "SUV", "Hatchback", "Pickup", "Coupe", "Convertible"});
            model.addAttribute("engineTypes", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
            model.addAttribute("transmissions", new String[]{"Automatic", "Manual"});
            return "new";
        }

        carService.save(carDTO);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id);

        CarDTO carDTO = new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setMake(car.getMake());
        carDTO.setModel(car.getModel());
        carDTO.setYear(car.getYear());
        carDTO.setLicensePlateNumber(car.getLicensePlateNumber());
        carDTO.setColor(car.getColor());
        carDTO.setBodyType(car.getBodyType());
        carDTO.setEngineType(car.getEngineType());
        carDTO.setTransmission(car.getTransmission());

        model.addAttribute("carDTO", carDTO);
        model.addAttribute("carId", id);
        model.addAttribute("bodyTypes", new String[]{"Sedan", "SUV", "Hatchback", "Pickup", "Coupe", "Convertible"});
        model.addAttribute("engineTypes", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
        model.addAttribute("transmissions", new String[]{"Automatic", "Manual"});

        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateCar(@PathVariable Long id,
                            @Valid @ModelAttribute("carDTO") CarDTO carDTO,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("carId", id);
            model.addAttribute("bodyTypes", new String[]{"Sedan", "SUV", "Hatchback", "Pickup", "Coupe", "Convertible"});
            model.addAttribute("engineTypes", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
            model.addAttribute("transmissions", new String[]{"Automatic", "Manual"});
            return "edit";
        }

        carService.update(id, carDTO);
        return "redirect:/";
    }

}
