package com.lex.Car.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CarDTO {

    private Long id;

    @NotBlank(message = "Make cannot be empty")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @Min(1886)
    @Max(2025)
    private int year;

    @NotBlank(message = "License Plate cannot be empty")
    private String licensePlateNumber;

    @NotBlank(message = "Pick a color")
    private String color;

    private String bodyType;
    private String engineType;
    private String transmission;

    public CarDTO() {}

    public CarDTO(long id, String make, String model, int year, String licensePlateNumber,
                  String color, String bodyType, String engineType, String transmission) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlateNumber = licensePlateNumber;
        this.color = color;
        this.bodyType = bodyType;
        this.engineType = engineType;
        this.transmission = transmission;
    }

    public Long getId() {
        return id;
    }
    public void setId(long id) { this.id = id; }

    public String getMake() {
        return make;
    }
    public void setMake(String make) { this.make = make; }

    public String getModel() {
        return model;
    }
    public void setModel(String model) { this.model = model; }

    public int getYear() {
        return year;
    }
    public void setYear(int year) { this.year = year; }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }
    public void setLicensePlateNumber(String licensePlateNumber) { this.licensePlateNumber = licensePlateNumber; }

    public String getColor() {
        return color;
    }
    public void setColor(String color) { this.color = color; }

    public String getBodyType() {
        return bodyType;
    }
    public void setBodyType(String bodyType) { this.bodyType = bodyType; }

    public String getEngineType() {
        return engineType;
    }
    public void setEngineType(String engineType) { this.engineType = engineType; }

    public String getTransmission() {
        return transmission;
    }
    public void setTransmission(String transmission) { this.transmission = transmission; }
}
