package cz.muni.fi.airportmanager.passengerservice.model.examples;

public class Examples {
    public static final String VALID_PASSENGER = """
            {
                            "id": 1
                            "firstName": "John",
                            "lastName": "Doe",
                            "email": "john@gmail.com",
                            "flightId": 1
            }
            """;

    public static final String VALID_PASSENGER_LIST = """
            [
                {
                    "id": 1
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john@gmail.com",
                    "flightId": 1
                },
                {
                    "id": 2
                    "firstName": "Jane",
                    "lastName": "Doe",
                    "email": "jane@gmail.com",
                    "flightId": 1
                }
            ]
            """;

    public static final String VALID_NOTIFICATION = """
            {
                "id": 1,
                "passengerId": 1,
                "flightId": 1,
                "message": "Notification message"
            }
            """;

    public static final String VALID_NOTIFICATION_LIST = """
            [
                {
                    "id": 1,
                    "passengerId": 1,
                    "flightId": 1,
                    "message": "Notification message"
                },
                {
                    "id": 2,
                    "passengerId": 2,
                    "flightId": 1,
                    "message": "Notification message"
                }
            ]
            """;
}