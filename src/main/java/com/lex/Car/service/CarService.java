package com.lex.Car.service;

import com.lex.Car.DTO.CarDTO;
import com.lex.Car.model.Car;
import com.lex.Car.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAll(){
        return carRepository.findAll();
    }

    public Car findById(int id){
        return carRepository.findById(id).orElse(null);
    }

    public Car save(CarDTO car) {
        Car newCar = new Car();
        newCar.setMake(car.getMake());
        newCar.setModel(car.getModel());
        newCar.setYear(car.getYear());
        newCar.setLicensePlateNumber(car.getLicensePlateNumber());
        newCar.setColor(car.getColor());
        newCar.setBodyType(car.getBodyType());
        newCar.setEngineType(car.getEngineType());
        newCar.setTransmission(car.getTransmission());
        return carRepository.save(newCar);
    }

    public Car updateCar(Car car, CarDTO carDTO){
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

    public void deleteCar(int id){
        carRepository.deleteById(id);
    }
}