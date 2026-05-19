package com.lars.portfolio_manager.entities;

public enum Exchange {
    CO("CO", "Copenhagen", "DDK"),
    US("US", "United States", "USD");

    private final String code;
    private final String displayName;
    private final String currency;

    Exchange(String code, String displayName, String currency) {
        this.code = code;
        this.displayName = displayName;
        this.currency = currency;
    }

    public String code() {
        return code;
    }

    public String displayName() {
        return displayName;
    }

    public String getCurrency() {
        return currency;
    }

    public static Exchange fromCode(String code) {
        for (Exchange exchange : values()) {
            if (exchange.code.equalsIgnoreCase(code)) {
                return exchange;
            }
        }
        throw new IllegalArgumentException("Unknown exchange: " + code);
    }
}
