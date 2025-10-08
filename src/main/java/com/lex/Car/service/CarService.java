package com.lex.Car.service;

import com.lex.Car.DTO.CarDTO;
import com.lex.Car.exception.ResourceNotFoundException;
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

// ===== Original Working Methods =====

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car with ID " + id + " not found."));
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

    public Car updateCar(Long id, CarDTO carDTO) {
        Car car = findById(id);
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

    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car with ID " + id + " not found.");
        }
        carRepository.deleteById(id);
    }

    public List<Car> searchCars(String keyword) {
        return carRepository
                .findByMakeContainingIgnoreCaseOrModelContainingIgnoreCaseOrLicensePlateNumberContainingIgnoreCase(
                        keyword, keyword, keyword
                );
    }

// ===== Added for Compatibility with Teacher's Code =====

    public List<Car> getAllCars() {
        return findAll();
    }

    public Car getCarById(Long id) {
        return findById(id);
    }

    public void update(Long id, CarDTO carDTO) {
        updateCar(id, carDTO);
    }

    public void delete(Long id) {
        deleteCar(id);
    }

}
