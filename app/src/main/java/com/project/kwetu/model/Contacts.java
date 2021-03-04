package com.project.kwetu.model;

public class Contacts {
    String fire, ambulance, police;

    public Contacts() {
    }

    public Contacts(String fire, String ambulance, String police) {
        this.fire = fire;
        this.ambulance = ambulance;
        this.police = police;
    }

    public String getFire() {
        return fire;
    }

    public void setFire(String fire) {
        this.fire = fire;
    }

    public String getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(String ambulance) {
        this.ambulance = ambulance;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }
}
