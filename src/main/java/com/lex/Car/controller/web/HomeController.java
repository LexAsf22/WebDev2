package com.lex.Car.controller.web;


import com.lex.Car.DTO.CarDTO;
import com.lex.Car.exception.ResourceNotFoundException;
import com.lex.Car.model.Car;
import com.lex.Car.repository.CarRepository;
import com.lex.Car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final CarService carService;
    CarRepository carRepository;

    // Constructor injection
    public HomeController(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
    }

    // Home page: list all cars
    @GetMapping("/")
    public String index(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);

        // Debug log
        cars.forEach(car -> System.out.println("Car: " + car.getMake()));

        return "index"; // index.html
    }

    // Show form to create new car
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("car", new CarDTO()); // bind DTO
        return "create"; // create.html
    }

    // Process creation
    @PostMapping("/save")
    public String save(@ModelAttribute("car") @Valid CarDTO carDTO,
                       BindingResult result,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("car", carDTO);
            return "create";
        }

        Car newCar = new Car();
        newCar.setMake(carDTO.getMake());
        newCar.setModel(carDTO.getModel());
        newCar.setYear(carDTO.getYear());
        newCar.setColor(carDTO.getColor());
        newCar.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        newCar.setBodyType(carDTO.getBodyType());
        newCar.setEngineType(carDTO.getEngineType());
        newCar.setTransmission(carDTO.getTransmission());

        carRepository.save(newCar);

        return "redirect:/";
    }

    // Show details of a car
    @GetMapping("/show")
    public String show(@RequestParam int id, Model model) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));

        model.addAttribute("car", car);
        return "show"; // show.html
    }

    // Show form to edit existing car
    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));

        // Pre-fill DTO for form
        CarDTO carDTO = new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setMake(car.getMake());
        carDTO.setModel(car.getModel());
        carDTO.setYear(car.getYear());
        carDTO.setColor(car.getColor());
        carDTO.setLicensePlateNumber(car.getLicensePlateNumber());
        carDTO.setBodyType(car.getBodyType());
        carDTO.setEngineType(car.getEngineType());
        carDTO.setTransmission(car.getTransmission());

        model.addAttribute("car", carDTO);
        model.addAttribute("id", id); // keep id for hidden field
        return "edit";
    }

    // Process update
    @PostMapping("/update")
    public String update(@ModelAttribute("car") @Valid CarDTO carDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }

        Car car = carRepository.findById(carDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", carDTO.getId()));

        car.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setColor(carDTO.getColor());
        car.setBodyType(carDTO.getBodyType());
        car.setEngineType(carDTO.getEngineType());
        car.setTransmission(carDTO.getTransmission());

        carRepository.save(car); // ✅ updates instead of creating new
        return "redirect:/";
    }

    // Delete car
    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        carRepository.deleteById(id);
        return "redirect:/";
    }

    // View details (same as show, but can be different page)
    @GetMapping("/view")
    public String view(@RequestParam int id, Model model) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));

        model.addAttribute("car", car);
        return "view"; // view.html
    }

}