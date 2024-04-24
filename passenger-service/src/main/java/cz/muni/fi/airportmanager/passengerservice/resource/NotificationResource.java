package cz.muni.fi.airportmanager.passengerservice.resource;

import cz.muni.fi.airportmanager.passengerservice.model.Notification;
import cz.muni.fi.airportmanager.passengerservice.model.examples.Examples;
import cz.muni.fi.airportmanager.passengerservice.service.NotificationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/notification")
@Tag(name = "Notification", description = "Notification API")
public class NotificationResource {

    @Inject
    NotificationService notificationService;

    /**
     * Get list of all notifications
     *
     * @return list of all notifications
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get list of all notifications")
    @APIResponse(
            responseCode = "200",
            description = "List of all notifications",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = Notification.class, required = true),
                    examples = @ExampleObject(name = "flight", value = Examples.VALID_NOTIFICATION_LIST)
            )
    )
    public RestResponse<List<Notification>> list() {
        return RestResponse.status(Response.Status.OK, notificationService.listAll());
    }

    /**
     * Delete all notifications
     */
    @DELETE
    @Operation(summary = "Delete all notifications")
    @APIResponse(
            responseCode = "204",
            description = "All notifications deleted"
    )
    public RestResponse<Void> deleteAll() {
        notificationService.deleteAll();
        return RestResponse.status(Response.Status.NO_CONTENT);
    }


}
