package com.lex.Car.repository;

import com.lex.Car.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByMakeContainingIgnoreCaseOrModelContainingIgnoreCaseOrLicensePlateNumberContainingIgnoreCaseOrColorContainingIgnoreCaseOrBodyTypeContainingIgnoreCaseOrEngineTypeContainingIgnoreCaseOrTransmissionContainingIgnoreCase(
            String make,String model, String licensePlateNumber, String color, String bodyType, String engineType, String transmission
    );
}
