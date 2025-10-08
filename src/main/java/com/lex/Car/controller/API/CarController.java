package com.lex.Car.controller.API;

import com.lex.Car.DTO.CarDTO;
import com.lex.Car.model.Car;
import com.lex.Car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // GET all cars
    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    // POST a new car
    @PostMapping("/cars")
    public Car newCar(@Valid @RequestBody CarDTO car) {
        return carService.save(car);
    }

    // PUT - update existing car by ID
    @PutMapping("/cars/{id}")
    public Car updateCar(@PathVariable Long id, @Valid @RequestBody CarDTO car) {
        if (carService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found.");
        }
        return carService.updateCar(id, car);
    }

    // DELETE - remove car by ID
    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable Long id) {
        if (carService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found.");
        }
        carService.deleteCar(id);
    }


}
