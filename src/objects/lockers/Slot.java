package objects.lockers;

import objects.Parcel;
import people.Person;

import java.util.Date;
import java.util.UUID;

public class Slot {
    private final UUID id;
    private final double length;
    private final double width;
    private final double height;
    private Locker locker;
    private Parcel parcel;

    public Slot(double length, double width, double height, Locker locker) {
        assert (length >= Parcel.MIN_LENGTH && length <= Parcel.MAX_LENGTH);
        assert (width >= Parcel.MIN_WIDTH && width <= Parcel.MAX_WIDTH);
        assert (height >= Parcel.MIN_HEIGHT && height <= Parcel.MAX_HEIGHT);
        this.id = UUID.randomUUID();
        this.length = length;
        this.width = width;
        this.height = height;
        this.locker = locker;
        this.parcel = null;
    }

    public static Slot randomSlot(Locker locker) {
        double randomLength = Parcel.MIN_LENGTH + Math.random() * (Parcel.MAX_LENGTH - Parcel.MIN_LENGTH);
        double randomWidth = Parcel.MIN_WIDTH + Math.random() * (Parcel.MAX_WIDTH - Parcel.MIN_WIDTH);
        double randomHeight = Parcel.MIN_HEIGHT + Math.random() * (Parcel.MAX_HEIGHT - Parcel.MIN_HEIGHT);
        return new Slot(randomLength, randomWidth, randomHeight, locker);
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

    public double getVolume() {
        return length * width * height;
    }

    public boolean isFree() {
        return parcel == null;
    }

    public boolean isBiggerThanParcel(Parcel parcel) {
        return length >= parcel.getLength() && width >= parcel.getWidth() && height >= parcel.getHeight();
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void addParcel(Parcel parcel, Person person) {
        assert (isFree());
        this.parcel = parcel;
        parcel.parcelInEvent(locker, person);
    }

    public boolean isParcelInSlot(Parcel parcel) {
        if (isFree()) {
            return false;
        }

        return this.parcel == parcel;
    }

    public Parcel pickUpParcel(Person person) {
        assert (!isFree());
        Parcel parcel = this.parcel;
        this.parcel = null;
        parcel.parcelOutEvent(locker, person);
        return parcel;
    }

    public boolean isParcelArrived() {
        assert (!isFree());
        return parcel.isArrived();
    }

    public boolean willParcelBeInSlot(Date date) {
        if (isFree()) {
            return false;
        }

        return parcel.willPickUpTimeBeExceeded(date);
    }
}
