package cz.muni.fi.airportmanager.passengerservice.resource;

import cz.muni.fi.airportmanager.passengerservice.model.Passenger;
import cz.muni.fi.airportmanager.passengerservice.model.examples.Examples;
import cz.muni.fi.airportmanager.passengerservice.service.PassengerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
 * This class is a REST resource that will be hosted on /passenger
 */
@Path("/passenger")
@Tag(name = "Passenger", description = "Passenger CRUD API")
public class PassengerResource {

    @Inject
    PassengerService passengerService;

    /**
     * Get list of all passengers
     *
     * @return list of all passengers
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get list of all passengers")
    @APIResponse(
            responseCode = "200",
            description = "List of all passengers",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = Passenger.class, required = true),
                    examples = @ExampleObject(name = "flight", value = Examples.VALID_PASSENGER_LIST)
            )
    )
    public RestResponse<List<Passenger>> list() {
        return RestResponse.status(Response.Status.OK, passengerService.listAll());
    }

    /**
     * Create a new passenger
     *
     * @param passenger passenger to create.
     * @return created passenger
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new passenger")
    @APIResponse(
            responseCode = "201",
            description = "Created passenger",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = Passenger.class, required = true),
                    examples = @ExampleObject(name = "flight", value = Examples.VALID_PASSENGER)
            )
    )
    @APIResponse(
            responseCode = "409",
            description = "Passenger with given id already exists"
    )
    public RestResponse<Passenger> create(
            @Schema(implementation = Passenger.class, required = true)
            Passenger passenger) {
        try {
            var newPassenger = passengerService.createPassenger(passenger);
            return RestResponse.status(Response.Status.CREATED, newPassenger);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.CONFLICT);
        }
    }

    /**
     * Get passenger by id
     *
     * @param id id of passenger
     * @return passenger with given id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get passenger by id")
    @APIResponse(
            responseCode = "200",
            description = "Passenger with given id",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = Passenger.class, required = true),
                    examples = @ExampleObject(name = "flight", value = Examples.VALID_PASSENGER)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Passenger with given id does not exist"
    )
    public RestResponse<Passenger> get(@Parameter(name = "id", required = true, description = "Passenger id") @PathParam("id") int id) {
        try {
            var passenger = passengerService.getPassenger(id);
            return RestResponse.status(Response.Status.OK, passenger);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Update passenger
     *
     * @param id        id of passenger
     * @param passenger passenger to update
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update passenger")
    @APIResponse(
            responseCode = "200",
            description = "Updated passenger",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = Passenger.class, required = true),
                    examples = @ExampleObject(name = "flight", value = Examples.VALID_PASSENGER)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Passenger with given id does not exist"
    )
    public RestResponse<Passenger> update(@Parameter(name = "id", required = true, description = "Passenger id") @PathParam("id") int id,
                                          @Schema(implementation = Passenger.class, required = true)
                                          Passenger passenger) {
        if (passenger.id != id) {
            return RestResponse.status(Response.Status.BAD_REQUEST);
        }
        try {
            var updatedPassenger = passengerService.updatePassenger(passenger);
            return RestResponse.status(Response.Status.OK, updatedPassenger);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Delete passenger
     *
     * @param id id of passenger
     */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete passenger")
    public RestResponse<Passenger> delete(@Parameter(name = "id", required = true) @PathParam("id") int id) {
        try {
            passengerService.deletePassenger(id);
            return RestResponse.status(Response.Status.OK);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Helper method to delete all passengers
     */
    @DELETE
    @Operation(summary = "Delete all passengers")
    public RestResponse<Passenger> deleteAll() {
        passengerService.deleteAllPassengers();
        return RestResponse.status(Response.Status.OK);
    }
}
