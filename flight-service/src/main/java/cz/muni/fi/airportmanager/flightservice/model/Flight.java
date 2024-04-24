package cz.muni.fi.airportmanager.flightservice.model;

import java.util.Date;

public class Flight {
    public int id;
    public String name;
    public String airportFrom;
    public String airportTo;
    public Date departureTime;
    public Date arrivalTime;
    public int capacity;
    public FlightStatus status;
}