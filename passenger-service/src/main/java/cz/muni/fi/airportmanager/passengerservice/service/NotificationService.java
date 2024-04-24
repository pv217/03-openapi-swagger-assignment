package cz.muni.fi.airportmanager.passengerservice.service;

import cz.muni.fi.airportmanager.passengerservice.model.Notification;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class NotificationService {

    /**
     * This is a temporary storage for notifications
     */
    private final List<Notification> notifications = new ArrayList<>();

    /**
     * Get list of all notifications
     *
     * @return list of all notifications
     */
    public List<Notification> listAll() {
        return notifications;
    }

    /**
     * Create a new notification
     *
     * @param notification notification to create
     * @return created notification
     */
    public Notification createNotification(Notification notification) {
        notification.id =
                notifications.stream().mapToInt(n -> n.id).max().orElse(0) + 1;
        notifications.add(notification);
        return notification;
    }

    /**
     * Delete all notifications
     */
    public void deleteAll() {
        notifications.clear();
    }

}
