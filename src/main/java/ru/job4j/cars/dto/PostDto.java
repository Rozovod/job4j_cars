package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {
    private String carName;
    private String carModel;
    private int productionYear;
    private int bodyId;
    private int engineId;
    private int transmissionId;
    private boolean carNew;
    private int mileage;
    private int price;
    private String phone;
    private String description;
}
