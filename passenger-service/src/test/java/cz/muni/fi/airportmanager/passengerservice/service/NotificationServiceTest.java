package cz.muni.fi.airportmanager.passengerservice.service;

import cz.muni.fi.airportmanager.passengerservice.model.Notification;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class NotificationServiceTest {
    private NotificationService notificationService;

    private Notification notification;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService();
        notification = new Notification();
        notification.id = 1;
        notification.message = "Flight Cancelled";
        notification.passengerId = 100;
        notification.email = "john.doe@example.com";
    }

    @Test
    void testListAllNotifications() {
        notificationService.createNotification(notification);
        List<Notification> notifications = notificationService.listAll();
        assertNotNull(notifications);
        assertFalse(notifications.isEmpty());
        assertEquals(1, notifications.size());
        assertEquals(notification, notifications.get(0));
    }

    @Test
    void testCreateNotification_Success() {
        Notification created = notificationService.createNotification(notification);
        assertNotNull(created);
        assertEquals(notification, created);
    }

    @Test
    void testDeleteAllNotifications() {
        notificationService.createNotification(notification);
        notificationService.deleteAll();
        List<Notification> notifications = notificationService.listAll();
        assertTrue(notifications.isEmpty());
    }
}
