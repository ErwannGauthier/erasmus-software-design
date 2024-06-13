import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Parcel implements Publisher, ParcelComponent {
    private final int GUARANTEED_DELIVERY_TIME_DAYS = 7;

    private final UUID identifier;
    private final User sender;
    private final User recipient;
    private final Locker recipientLocker;
    private final Size size;
    private final List<Event> transitRecords;
    private final LocalDateTime registeredAt;
    private final LocalDateTime guaranteedDeliveryTime;
    private Locker senderLocker;
    private LocalDateTime estimateDeliveryTime;
    private LocalDateTime actualDeliveryTime;
    private LocalDateTime actualPickupTime;
    private State state;
    private List<Subscriber> subscribers;

    public Parcel(User sender, User recipient, Locker recipientLocker, Size size) {
        this.identifier = UUID.randomUUID();
        this.sender = sender;
        this.recipient = recipient;
        this.recipientLocker = recipientLocker;
        this.size = size;
        this.transitRecords = new ArrayList<>();
        this.registeredAt = LocalDateTime.now();
        this.guaranteedDeliveryTime = registeredAt.plusDays(GUARANTEED_DELIVERY_TIME_DAYS);
        this.state = new StateRegistered(this);
        this.subscribers = new ArrayList<>();
    }

    public void addEvent(Event event) {
        transitRecords.add(event);
        this.state.handleEvent(event);
        this.notifySubscribers(event);
    }

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(Event event) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(this, event);
        }
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public Locker getSenderLocker() {
        return senderLocker;
    }

    public void setSenderLocker(Locker senderLocker) {
        this.senderLocker = senderLocker;
    }

    public Locker getRecipientLocker() {
        return recipientLocker;
    }

    public Size getSize() {
        return size;
    }

    public List<Event> getTransitRecords() {
        return Collections.unmodifiableList(transitRecords);
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDateTime getEstimateDeliveryTime() {
        return estimateDeliveryTime;
    }

    public void setEstimateDeliveryTime(LocalDateTime estimateDeliveryTime) {
        this.estimateDeliveryTime = estimateDeliveryTime;
    }

    public LocalDateTime getActualDeliveryTime() {
        return actualDeliveryTime;
    }

    public void setActualDeliveryTime(LocalDateTime actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime;
    }

    public LocalDateTime getGuaranteedDeliveryTime() {
        return guaranteedDeliveryTime;
    }

    public LocalDateTime getActualPickupTime() {
        return actualPickupTime;
    }

    public void setActualPickupTime(LocalDateTime actualPickupTime) {
        this.actualPickupTime = actualPickupTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getStatus() {
        return state.getStatus();
    }

    public String toString() {
        return "Parcel (" + identifier.toString() + ") form " + getSender() + " to" + getRecipient();
    }
}
