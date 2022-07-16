package com.tinie.Services.responses;

import lombok.Data;

@Data
public class BusinessDetailsResponse {
    private int id;
    private String name;
    private float latitude;
    private float longitude;
    private SubCategoryResponse subCategory;
}
