package com.lex.Car.service;

import com.lex.Car.DTO.CarDTO;
import com.lex.Car.model.Car;
import com.lex.Car.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void save(CarDTO carDTO) {
        Car car = new Car();
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setColor(carDTO.getColor());
        car.setBodyType(carDTO.getBodyType());
        car.setEngineType(carDTO.getEngineType());
        car.setLicensePlate(carDTO.getLicensePlate());

        carRepository.save(car);
    }

    public void update(int id, CarDTO carDTO) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setColor(carDTO.getColor());
        car.setBodyType(carDTO.getBodyType());
        car.setEngineType(carDTO.getEngineType());
        car.setLicensePlate(carDTO.getLicensePlate());

        carRepository.save(car);
    }

    public void delete(int id) {
        carRepository.deleteById(id);
    }
}
