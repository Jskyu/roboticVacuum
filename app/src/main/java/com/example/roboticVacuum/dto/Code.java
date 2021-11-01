package com.example.roboticVacuum.dto;

public enum Code {
    UP("1"), DOWN("2"), LEFT("3"), RIGHT("4"), CENTER("5"),
    CIRCLE("6"), RANDOM("7"), WAVE("8"), WALL_FOLLOWING("9");

    private String number;

    Code(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
