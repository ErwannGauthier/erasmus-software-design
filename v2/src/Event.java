import java.time.LocalDateTime;
import java.util.UUID;

public class Event {
    private final UUID identifier;
    private final LocalDateTime timestamp;
    private final EventType type;
    private final ParcelComponent parcel;
    private final Locker locker;

    public Event(EventType type, ParcelComponent parcel, Locker locker) {
        this.identifier = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.parcel = parcel;
        this.locker = locker;
    }

    public String getLocation() {
        if (this.type == EventType.REGISTERED || this.type == EventType.LOST) {
            return "There is no location for this type of event.";
        } else if (this.locker == null) {
            return "No location given for this event.";
        }

        return this.locker.getAddress();
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public EventType getType() {
        return type;
    }

    public ParcelComponent getParcel() {
        return parcel;
    }

    public Locker getLocker() {
        return locker;
    }

    public String toString() {
        return "Event (" + identifier.toString() + ") " + type.name() + " at " + timestamp.toString() + " in " + this.getLocation();
    }
}
