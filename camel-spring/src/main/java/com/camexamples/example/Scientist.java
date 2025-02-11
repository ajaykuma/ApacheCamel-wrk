package com.camexamples.example;

public class Scientist {

    private String scId;
    private String scName;

    public String getscId() {
        return scId;
    }

    public void setscId(String scId) {
        this.scId = scId;
    }
    public String getscName() {
        return scName;
    }

    public void setscName(String scName) {
        this.scName = scName;
    }

    @Override
    public String toString() {
        return "Scientist [scId=" + scId + ", scName=" + scName + "]";
    }

}
