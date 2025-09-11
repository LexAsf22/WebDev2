package com.lex.Car.DTO;

import jakarta.validation.constraints.*;

public class CarDTO {

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1886, message = "Year must be after 1885")
    @Max(value = 2100, message = "Year is not valid")
    private Integer year;

    @NotBlank(message = "Color is required")
    private String color;

    @NotBlank(message = "Body type is required")
    private String bodyType;

    @NotBlank(message = "Engine type is required")
    private String engineType;

    @NotBlank(message = "License plate is required")
    @Size(min = 2, max = 15, message = "License plate must be between 2 and 15 characters")
    private String licensePlate;

    // Getters and Setters
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
