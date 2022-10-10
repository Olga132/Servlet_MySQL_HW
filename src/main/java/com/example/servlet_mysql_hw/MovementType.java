package com.example.servlet_mysql_hw;

public enum MovementType {
    ПРИХОД("ПРИХОД"), РАСХОД("РАСХОД");

    private final String name;

    MovementType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
