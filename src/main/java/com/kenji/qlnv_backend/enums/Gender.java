package com.kenji.qlnv_backend.enums;

public enum Gender {
    Male("Male"),
    Female("Female");
    private final String description;

    Gender(String value){
        this.description = value;
    }

    public String getDescription() {
        return description;
    }
}
