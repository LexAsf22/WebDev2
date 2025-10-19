package com.lex.Car.service;

import com.lex.Car.DTO.CarDTO;
import com.lex.Car.exception.ResourceNotFoundException;
import com.lex.Car.model.Car;
import com.lex.Car.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class CarService {
    final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(int id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car save(CarDTO carDTO) {
        Car car = new Car();
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        car.setColor(carDTO.getColor());
        car.setBodyType(carDTO.getBodyType());
        car.setEngineType(carDTO.getEngineType());
        car.setTransmission(carDTO.getTransmission());

        return carRepository.save(car);
    }

    public Car updateCar(int id, CarDTO carDTO){
        Car car = carRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Car", id));
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        car.setColor(carDTO.getColor());
        car.setBodyType(carDTO.getBodyType());
        car.setEngineType(carDTO.getEngineType());
        car.setTransmission(carDTO.getTransmission());
        return carRepository.save(car);
    }

    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }
}