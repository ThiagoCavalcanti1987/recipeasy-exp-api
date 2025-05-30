package com.example.recipeasy.model;

public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
