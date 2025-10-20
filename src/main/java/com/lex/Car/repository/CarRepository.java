package com.lex.Car.repository;

import com.lex.Car.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer>{

}
