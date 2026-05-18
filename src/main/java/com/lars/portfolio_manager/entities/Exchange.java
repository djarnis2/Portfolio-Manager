package com.lars.portfolio_manager.entities;

public enum Exchange {
    CO("CO","Copenhagen"),
    US("US","United States");

    private final String code;
    private final String displayName;

    Exchange(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String code() {
        return code;
    }

    public String displayName() {
        return displayName;
    }

}
