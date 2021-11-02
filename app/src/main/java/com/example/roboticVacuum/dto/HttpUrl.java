package com.example.roboticVacuum.dto;

public enum HttpUrl {
    INSERT_URL("http://192.168.0.2/api-insert.php"), SELECT_URL("http://192.168.0.2/api-select.php");
    private final String value;

    HttpUrl(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}