package cz.muni.fi.airportmanager.flightservice.resources;

import cz.muni.fi.airportmanager.flightservice.model.example.Examples;
import cz.muni.fi.airportmanager.flightservice.service.FlightService;
import cz.muni.fi.airportmanager.flightservice.model.Flight;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * This class is a REST resource that will be hosted on /flight
 */
@Path("/flight")
// TODO add openapi tag
public class FlightResource {

    @Inject
    FlightService flightService;


    /**
     * Get list of all flights
     *
     * @return list of all flights
     */
    @GET
    @Produces(APPLICATION_JSON)
    //    TODO add openapi docs
    public RestResponse<List<Flight>> list() {
        return RestResponse.status(Response.Status.OK, flightService.listAll());
    }

    /**
     * Create a new flight
     *
     * @param flight flight to create.
     * @return created flight
     */
    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    //    TODO add openapi docs
    public RestResponse<Flight> create(
            @Schema(implementation = Flight.class, required = true)
            Flight flight) {
        try {
            var newFlight = flightService.createFlight(flight);
            return RestResponse.status(Response.Status.CREATED, newFlight);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.CONFLICT);
        }
    }


    /**
     * Get flight by id
     *
     * @param id id of flight
     * @return flight with given id
     */
    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    //    TODO add openapi docs
    public RestResponse<Flight> get(@PathParam("id") int id) {
        try {
            var flight = flightService.getFlight(id);
            return RestResponse.status(Response.Status.OK, flight);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }


    /**
     * Update flight
     *
     * @param id     id of flight
     * @param flight flight to update
     */
    @PUT
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    //    TODO add openapi docs
    public RestResponse<Flight> update( @PathParam("id") int id,
                                       Flight flight) {
        if (flight.id != id) {
            return RestResponse.status(Response.Status.BAD_REQUEST);
        }
        try {
            var updatedFlight = flightService.updateFlight(flight);
            return RestResponse.status(Response.Status.OK, updatedFlight);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Delete flight
     *
     * @param id id of flight
     */
    @DELETE
    @Path("/{id}")
    //    TODO add openapi docs
    public RestResponse<Flight> delete(@PathParam("id") int id) {
        try {
            flightService.deleteFlight(id);
            return RestResponse.status(Response.Status.OK);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Helper method for to delete all flights
     */
    @DELETE
    @Operation(summary = "Delete all flights")
    @APIResponse(
            responseCode = "200",
            description = "All flights deleted"
    )
    public RestResponse<Flight> deleteAll() {
        flightService.deleteAllFlights();
        return RestResponse.status(Response.Status.OK);
    }

    /**
     * Cancel flight
     */
    @PUT
    @Path("/{id}/cancel")
    //    TODO add openapi docs
    public RestResponse<Flight> cancel(@PathParam("id") int id) {
        try {
            flightService.cancelFlight(id);
            return RestResponse.status(Response.Status.OK);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

}

