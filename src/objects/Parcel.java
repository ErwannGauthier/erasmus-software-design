package objects;

import events.Event;
import events.EventParcel;
import events.TypeEvent;
import objects.lockers.Locker;
import people.Person;
import people.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Parcel {
    // Based on https://images.app.goo.gl/bpk7Q9MKnKRktpATA
    public static double MIN_LENGTH = 35.3;
    public static double MAX_LENGTH = 61.0;
    public static double MIN_WIDTH = 25.0;
    public static double MAX_WIDTH = 46.0;
    public static double MIN_HEIGHT = 2.5;
    public static double MAX_HEIGHT = 46.0;

    private final UUID id;
    private final double length;
    private final double width;
    private final double height;
    private final User sender;
    private final User receiver;
    private final Locker senderLocker;
    private final Locker receiverLocker;
    private List<EventParcel> events;

    public Parcel(double length, double width, double height, User sender, User receiver, Locker senderLocker, Locker receiverLocker) {
        assert (length >= Parcel.MIN_LENGTH && length <= Parcel.MAX_LENGTH);
        assert (width >= Parcel.MIN_WIDTH && width <= Parcel.MAX_WIDTH);
        assert (height >= Parcel.MIN_HEIGHT && height <= Parcel.MAX_HEIGHT);
        this.id = UUID.randomUUID();
        this.length = length;
        this.width = width;
        this.height = height;
        this.sender = sender;
        this.receiver = receiver;
        this.senderLocker = senderLocker;
        this.receiverLocker = receiverLocker;
        this.events = new ArrayList<>();
    }

    public static Parcel randomParcel(User sender, User receiver, Locker senderLocker, Locker receiverLocker) {
        double randomLength = Parcel.MIN_LENGTH + Math.random() * (Parcel.MAX_LENGTH - Parcel.MIN_LENGTH);
        double randomWidth = Parcel.MIN_WIDTH + Math.random() * (Parcel.MAX_WIDTH - Parcel.MIN_WIDTH);
        double randomHeight = Parcel.MIN_HEIGHT + Math.random() * (Parcel.MAX_HEIGHT - Parcel.MIN_HEIGHT);
        return new Parcel(randomLength, randomWidth, randomHeight, sender, receiver, senderLocker, receiverLocker);
    }

    public static double getMinVolume() {
        return MIN_LENGTH * MIN_WIDTH * MIN_HEIGHT;
    }

    public static double getMaxVolume() {
        return MAX_LENGTH * MAX_WIDTH * MAX_HEIGHT;
    }

    public UUID getId() {
        return id;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public Locker getSenderLocker() {
        return senderLocker;
    }

    public Locker getReceiverLocker() {
        return receiverLocker;
    }

    public boolean isArrived() {
        if (events.isEmpty()) {
            return false;
        }

        EventParcel lastEvent = events.get(events.size() - 1);
        return lastEvent.getType() == TypeEvent.ParcelIn && lastEvent.getLockerRelatedTo() == receiverLocker;
    }

    private void addEvent(TypeEvent type, Locker locker, Person person) {
        EventParcel event = new EventParcel(type, this, locker, person);
        events.add(event);
    }

    public void parcelInEvent(Locker locker, Person person) {
        this.addEvent(TypeEvent.ParcelIn, locker, person);
    }

    public void parcelOutEvent(Locker locker, Person person) {
        this.addEvent(TypeEvent.ParcelOut, locker, person);
    }

    public boolean isDestinationLocker(Locker locker) {
        return receiverLocker == locker;
    }

    private EventParcel getFirstEvent() {
        if (events.isEmpty()) {
            return null;
        }

        return events.get(0);
    }

    private EventParcel getDeliveryEvent() {
        EventParcel deliveryEvent = null;
        for (EventParcel event : events) {
            if (event.getType() == TypeEvent.ParcelIn && event.getLockerRelatedTo() == receiverLocker) {
                deliveryEvent = event;
            }
        }

        return deliveryEvent;
    }

    private EventParcel getPickUpEvent() {
        if (events.isEmpty()) {
            return null;
        }

        EventParcel lastEvent = events.get(events.size() - 1);
        if (lastEvent.getType() != TypeEvent.ParcelOut && lastEvent.getEventMaker() != receiver) {
            return null;
        }

        return lastEvent;
    }

    // Estimated delivery time is always 24 hours after the sender has put the parcel in the locker.
    public Date getEstimatedDeliveryTime() {
        Event firstEvent = getFirstEvent();
        if (firstEvent == null) {
            return null;
        }

        int dayInMs = 24 * 60 * 60 * 1000;
        Date sendDate = firstEvent.getDate();
        return new Date(sendDate.getTime() + dayInMs);
    }

    // Guaranteed delivery time is always 48 hours after the sender has put the parcel in the locker.
    public Date getGuaranteedDeliveryTime() {
        Event firstEvent = getFirstEvent();
        if (firstEvent == null) {
            return null;
        }

        int dayInMs = 24 * 60 * 60 * 1000;
        Date sendDate = firstEvent.getDate();
        return new Date(sendDate.getTime() + dayInMs * 2);
    }

    // Returns null if the parcel is not in the receiver locker yet.
    public Date getDeliveryTime() {
        Event firstEvent = getFirstEvent();
        if (firstEvent == null) {
            return null;
        }

        EventParcel deliveryEvent = getDeliveryEvent();
        if (deliveryEvent == null) {
            return null;
        }

        Date sendDate = firstEvent.getDate();
        Date deliveryDate = deliveryEvent.getDate();

        return new Date(deliveryDate.getTime() - sendDate.getTime());
    }

    // Returns null if the parcel is not in the receiver locker yet or if it is still in.
    public Date getPickUpTime() {
        if (events.isEmpty()) {
            return null;
        }

        EventParcel deliveryEvent = getDeliveryEvent();
        if (deliveryEvent == null) {
            return null;
        }

        EventParcel pickupEvent = getPickUpEvent();
        if (pickupEvent == null) {
            return null;
        }

        Date deliveryDate = deliveryEvent.getDate();
        Date pickupDate = pickupEvent.getDate();
        return new Date(pickupDate.getTime() - deliveryDate.getTime());
    }

    public Date getMaxPickUpTime() {
        EventParcel deliveryEvent = getDeliveryEvent();
        if (deliveryEvent == null) {
            return null;
        }

        EventParcel pickupEvent = getPickUpEvent();
        if (pickupEvent != null) {
            return null;
        }

        int dayInMs = 24 * 60 * 60 * 1000;
        Date deliveryDate = deliveryEvent.getDate();
        return new Date(deliveryDate.getTime() + dayInMs * 2);
    }

    public boolean isPickUpTimeExceeded() {
        Date maxPickupTime = getMaxPickUpTime();
        if (maxPickupTime == null) {
            return false;
        }

        int dayInMs = 24 * 60 * 60 * 1000;
        Date now = new Date();
        return (now.getTime() - maxPickupTime.getTime()) > dayInMs * 2;
    }

    public boolean willPickUpTimeBeExceeded(Date date) {
        Date maxPickupTime = getMaxPickUpTime();
        if (maxPickupTime == null) {
            return false;
        }

        return (date.getTime() - maxPickupTime.getTime()) >= 0;
    }
}
