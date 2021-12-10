package it.unisa.enums;

public enum Type {
    INTEGER("integer"), REAL("real"), STRING("string"),
    BOOL("bool"), VAR("var");

    private String toString;

    Type(String name) {
        this.toString = name;
    }

    @Override
    public String toString() {
        return toString;
    }

}