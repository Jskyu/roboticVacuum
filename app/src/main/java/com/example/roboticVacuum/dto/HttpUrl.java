package com.example.roboticVacuum.dto;

public enum HttpUrl {
    POST_URL("POST", "http://192.168.0.2/api-post.php"), GET_URL("GET", "httpL//192.168.0.2/api_select.php");
    private String key;
    private String value;

    HttpUrl(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
