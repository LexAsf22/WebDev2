package com.lex.Car.controller.API;

import com.lex.Car.DTO.CarDTO;
import com.lex.Car.exception.ResourceNotFoundException;
import com.lex.Car.model.Car;
import com.lex.Car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<Car> getAllCars(){
        return carService.findAll();
    }

    @PostMapping("/cars")
    public Car newCar(@Valid @RequestBody CarDTO car){
        return carService.save(car);
    }

    @PutMapping("/cars/{id}")
    public Car updateCar(@PathVariable Long id, @Valid @RequestBody CarDTO car) {
        if (carService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found.");
        }
        return carService.updateCar(id, car);
    }


    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable Long id){
        if(carService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID "+ id + " not found.");
        }
        carService.deleteCar(id);
    }

}
