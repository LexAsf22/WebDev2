package com.lex.Car.controller;

import com.lex.Car.DTO.CarDTO;
import com.lex.Car.exception.ResourceNotFoundException;
import com.lex.Car.model.Car;
import com.lex.Car.repository.CarRepository;
import com.lex.Car.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    private final CarRepository carRepository;
    private final CarService carService;

    public HomeController(CarRepository carRepository, CarService carService) {
        this.carRepository = carRepository;
        this.carService = carService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("carDTO", new CarDTO());
        return "create";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("carDTO") CarDTO carDTO,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            return "create"; // back to create form showing errors
        }
        carService.save(carDTO); // Save the car using the service
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable("id") int id, Model model) {
        try {
            Car car = carRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Car", (long) id));

            // Map entity to DTO for form binding
            CarDTO carDTO = new CarDTO();
            carDTO.setMake(car.getMake());
            carDTO.setModel(car.getModel());
            carDTO.setYear(car.getYear());
            carDTO.setColor(car.getColor());
            carDTO.setBodyType(car.getBodyType());
            carDTO.setEngineType(car.getEngineType());
            carDTO.setLicensePlate(car.getLicensePlate());

            model.addAttribute("carDTO", carDTO);
            model.addAttribute("carId", id);
            return "edit";

        } catch (ResourceNotFoundException ex) {
            // Pass the error message to the model to display on the edit page
            model.addAttribute("errorMessage", "ID=" + id + " is not found");
            model.addAttribute("carDTO", new CarDTO()); // empty DTO for form
            model.addAttribute("carId", id);
            return "edit";
        }
    }

    @PostMapping("/update/{id}")
    public String updateCar(@PathVariable("id") int id,
                            @Valid @ModelAttribute("carDTO") CarDTO carDTO,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("carId", id);
            return "edit"; // redisplay form with errors
        }
        carService.update(id, carDTO);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") int id) {
        carService.delete(id);
        return "redirect:/";
    }
}