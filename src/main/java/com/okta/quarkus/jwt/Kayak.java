package com.okta.quarkus.jwt;

import lombok.Data;

import java.util.Objects;

@Data
public class Kayak {

    private String make;

    private String model;

    private Integer length;

    public Kayak() {
    }

    public Kayak(String make, String model, Integer length) {
        this.make = make;
        this.model = model;
        this.length = length;
    }
    
}
