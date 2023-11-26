package com.arkx.Backend.ecomwatches.enumeration;

public enum OrderStatus {
    OPENED("Opened"),
    PAID("Paid"),
    SHIPPED("Shipped"),
    CANCELED("Canceled"),
    CLOSED("CLOSED");

    OrderStatus(String status) {
        this.value=status;
    }
    private final String value;

    public String getValue() {
        return value;
    }
}
