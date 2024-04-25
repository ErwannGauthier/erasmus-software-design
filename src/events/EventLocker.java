package events;

import objects.lockers.Locker;
import objects.parcels.Parcel;
import people.Person;

public class EventLocker extends Event {
    private Locker locker;
    private Parcel parcelRelatedTo;
    private Person eventMaker;

    public EventLocker(TypeEvent type, Locker locker, Parcel parcelRelatedTo, Person eventMaker) {
        super(type);
        this.locker = locker;
        this.parcelRelatedTo = parcelRelatedTo;
        this.eventMaker = eventMaker;
    }

    public Locker getLocker() {
        return locker;
    }

    public Parcel getParcelRelatedTo() {
        return parcelRelatedTo;
    }

    public Person getEventMaker() {
        return eventMaker;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("objects.lockers.Locker: ").append(locker).append("\n");
        builder.append("\tAction: ").append(super.getType()).append("\n");
        builder.append("\tOn objects.parcels.Parcel: ").append(parcelRelatedTo).append("\n");
        builder.append("\tBy: ").append(eventMaker).append("\n");
        return builder.toString();
    }
}
