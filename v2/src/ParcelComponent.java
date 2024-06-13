import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ParcelComponent {
    public void addEvent(Event event);

    public void subscribe(Subscriber subscriber);

    public void unsubscribe(Subscriber subscriber);

    public void notifySubscribers(Event event);

    public UUID getIdentifier();

    public User getSender();

    public User getRecipient();

    public Locker getSenderLocker();

    public void setSenderLocker(Locker senderLocker);

    public Locker getRecipientLocker();

    public Size getSize();

    public List<Event> getTransitRecords();

    public LocalDateTime getRegisteredAt();

    public LocalDateTime getEstimateDeliveryTime();

    public void setEstimateDeliveryTime(LocalDateTime estimateDeliveryTime);

    public LocalDateTime getActualDeliveryTime();

    public void setActualDeliveryTime(LocalDateTime actualDeliveryTime);

    public LocalDateTime getGuaranteedDeliveryTime();

    public LocalDateTime getActualPickupTime();

    public void setActualPickupTime(LocalDateTime actualPickupTime);

    public State getState();

    public void setState(State state);

    public String getStatus();

    public String toString();
}
