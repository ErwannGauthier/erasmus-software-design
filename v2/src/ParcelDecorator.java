import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public abstract class ParcelDecorator implements ParcelComponent {
    ParcelComponent wrappedComponent;

    public ParcelDecorator(ParcelComponent wrappedComponent) {
        this.wrappedComponent = wrappedComponent;
    }

    public void addEvent(Event event) {
        wrappedComponent.addEvent(event);
    }

    public void subscribe(Subscriber subscriber) {
        wrappedComponent.subscribe(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        wrappedComponent.unsubscribe(subscriber);
    }

    public void notifySubscribers(Event event) {
        wrappedComponent.notifySubscribers(event);
    }

    public UUID getIdentifier() {
        return wrappedComponent.getIdentifier();
    }

    public User getSender() {
        return wrappedComponent.getSender();
    }

    public User getRecipient() {
        return wrappedComponent.getRecipient();
    }

    public Locker getSenderLocker() {
        return wrappedComponent.getSenderLocker();
    }

    public void setSenderLocker(Locker senderLocker) {
        wrappedComponent.setSenderLocker(senderLocker);
    }

    public Locker getRecipientLocker() {
        return wrappedComponent.getRecipientLocker();
    }

    public Size getSize() {
        return wrappedComponent.getSize();
    }

    public List<Event> getTransitRecords() {
        return wrappedComponent.getTransitRecords();
    }

    public LocalDateTime getRegisteredAt() {
        return wrappedComponent.getRegisteredAt();
    }

    public LocalDateTime getEstimateDeliveryTime() {
        return wrappedComponent.getEstimateDeliveryTime();
    }

    public void setEstimateDeliveryTime(LocalDateTime estimateDeliveryTime) {
        wrappedComponent.setEstimateDeliveryTime(estimateDeliveryTime);
    }

    public LocalDateTime getActualDeliveryTime() {
        return wrappedComponent.getActualDeliveryTime();
    }

    public void setActualDeliveryTime(LocalDateTime actualDeliveryTime) {
        wrappedComponent.setActualDeliveryTime(actualDeliveryTime);
    }

    public LocalDateTime getGuaranteedDeliveryTime() {
        return wrappedComponent.getGuaranteedDeliveryTime();
    }

    public LocalDateTime getActualPickupTime() {
        return wrappedComponent.getActualPickupTime();
    }

    public void setActualPickupTime(LocalDateTime actualPickupTime) {
        wrappedComponent.setActualPickupTime(actualPickupTime);
    }

    public State getState() {
        return wrappedComponent.getState();
    }

    public void setState(State state) {
        wrappedComponent.setState(state);
    }

    public String getStatus() {
        return wrappedComponent.getStatus();
    }

    public String toString() {
        return wrappedComponent.toString();
    }
}
