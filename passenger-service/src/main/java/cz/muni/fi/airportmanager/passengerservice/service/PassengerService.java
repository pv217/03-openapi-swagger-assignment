package cz.muni.fi.airportmanager.passengerservice.service;

import cz.muni.fi.airportmanager.passengerservice.model.Passenger;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped // This bean will be created once per application and live as long as the application lives
public class PassengerService {
    /**
     * This is a temporary storage for passengers
     */
    private final Map<Integer, Passenger> passengers = new HashMap<>();

    /**
     * Get list of all passengers
     *
     * @return list of all passengers
     */
    public List<Passenger> listAll() {
        return passengers.values().stream().toList();
    }

    /**
     * Get passenger by id
     *
     * @param id passenger id
     * @return passenger with given id
     * @throws IllegalArgumentException if passenger with given id does not exist
     */
    public Passenger getPassenger(int id) {
        if (passengers.get(id) == null) {
            throw new IllegalArgumentException("Passenger with id " + id + " does not exist");
        }
        return passengers.get(id);
    }

    /**
     * Get passengers for given flight id
     *
     * @param flightId flight id
     * @return list of passengers for given flight id
     */
    public List<Passenger> getPassengersForFlight(int flightId) {
        return passengers.values().stream().filter(p -> p.flightId == flightId).toList();
    }

    /**
     * Create a new passenger
     *
     * @param passenger passenger to create.
     * @return created passenger
     * @throws IllegalArgumentException if passenger with given id already exists
     */
    public Passenger createPassenger(Passenger passenger) {
        if (passengers.get(passenger.id) != null) {
            throw new IllegalArgumentException("Passenger with id " + passenger.id + " already exists");
        }
        passengers.put(passenger.id, passenger);
        return passenger;
    }

    /**
     * Update passenger
     *
     * @param passenger passenger to update
     * @return updated passenger
     * @throws IllegalArgumentException if passenger with given id does not exist
     */
    public Passenger updatePassenger(Passenger passenger) {
        if (passengers.get(passenger.id) == null) {
            throw new IllegalArgumentException("Passenger with id " + passenger.id + " does not exist");
        }
        passengers.put(passenger.id, passenger);
        return passenger;
    }

    /**
     * Delete passenger
     *
     * @param id passenger id
     * @throws IllegalArgumentException if passenger with given id does not exist
     */
    public void deletePassenger(int id) {
        if (passengers.get(id) == null) {
            throw new IllegalArgumentException("Passenger with id " + id + " does not exist");
        }
        passengers.remove(id);
    }

    /**
     * Delete all passengers
     */
    public void deleteAllPassengers() {
        passengers.clear();
    }
}
