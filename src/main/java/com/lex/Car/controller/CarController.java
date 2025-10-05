package com.lex.Car.controller;

import com.lex.Car.model.Car;
import com.lex.Car.repository.CarRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private final CarRepository repository;

    public CarController(CarRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Car> getAllCar(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id " + id));
    }

    @PostMapping
    public Car createCar(@RequestBody Car car){
        return repository.save(car);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id" + id));
        car.setMake(carDetails.getMake());
        car.setModel(carDetails.getModel());
        car.setYear(carDetails.getYear());
        car.setLicensePlateNumber(carDetails.getLicensePlateNumber());
        car.setColor(carDetails.getColor());
        car.setBodyType(carDetails.getBodyType());
        car.setEngineType(carDetails.getEngineType());
        car.setTransmission(carDetails.getTransmission());

        return repository.save(car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
