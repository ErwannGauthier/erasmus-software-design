package events;

import objects.Parcel;
import objects.lockers.Locker;
import people.Person;

public class EventParcel extends Event {
    private Parcel parcel;
    private Locker lockerRelatedTo;
    private Person eventMaker;

    public EventParcel(TypeEvent type, Parcel parcel, Locker lockerRelatedTo, Person eventMaker) {
        super(type);
        this.parcel = parcel;
        this.lockerRelatedTo = lockerRelatedTo;
        this.eventMaker = eventMaker;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public Locker getLockerRelatedTo() {
        return lockerRelatedTo;
    }

    public Person getEventMaker() {
        return eventMaker;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("objects.Parcel: ").append(parcel).append("\n");
        builder.append("\tAction: ").append(super.getType()).append("\n");
        builder.append("\tOn objects.lockers.Locker: ").append(lockerRelatedTo).append("\n");
        builder.append("\tBy: ").append(eventMaker).append("\n");
        return builder.toString();
    }
}
