package com.ns.model;

public enum Status {
    RUNNING("Running"), NOT_RESPONDING("Not Responding"), UNKNOWN("Unknown");

    public static Status valueOfIgnoreCase(String name) {

        for (Status status : values()) {
            if (status.getName().equalsIgnoreCase(name)) {
                return status;
            }
        }

        throw new IllegalArgumentException(String.format("There is no status with name '%s'", name));
    }

    private Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;
}
