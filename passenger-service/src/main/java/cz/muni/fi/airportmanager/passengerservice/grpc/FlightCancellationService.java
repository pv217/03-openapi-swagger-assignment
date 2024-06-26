package cz.muni.fi.airportmanager.passengerservice.grpc;

import cz.muni.fi.airportmanager.passengerservice.model.Notification;
import cz.muni.fi.airportmanager.passengerservice.service.NotificationService;
import cz.muni.fi.airportmanager.passengerservice.service.PassengerService;
import cz.muni.fi.airportmanager.proto.FlightCancellationRequest;
import cz.muni.fi.airportmanager.proto.FlightCancellationResponse;
import cz.muni.fi.airportmanager.proto.FlightCancellationResponseStatus;
import cz.muni.fi.airportmanager.proto.MutinyFlightCancellationGrpc;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;


@GrpcService
public class FlightCancellationService extends MutinyFlightCancellationGrpc.FlightCancellationImplBase {

    @Inject
    NotificationService notificationService;

    @Inject
    PassengerService passengerService;

    /**
     * Cancel flight and add notification for all passengers
     *
     * @param request request with flight id and reason for cancellation
     * @return response with status of cancellation
     */
    @Override
    public Uni<FlightCancellationResponse> cancelFlight(FlightCancellationRequest request) {
//        TODO inject notification service and passenger service to create notifications for all passengers with given flight id
//        TODO check how Passenger and Notification classes look like
    }
}
