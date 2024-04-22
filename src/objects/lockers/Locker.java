package objects.lockers;

import events.EventLocker;
import events.TypeEvent;
import objects.Parcel;
import people.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Locker {
    private final UUID id;
    private String address;
    private final UserPanel userPanel;
    private List<Slot> slots;
    private List<EventLocker> events;
    private List<Parcel> incomingParcel;

    public Locker(String address) {
        this.id = UUID.randomUUID();
        this.address = address;
        this.userPanel = new UserPanel(this);
        this.slots = new ArrayList<>();
        this.events = new ArrayList<>();
        this.incomingParcel = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public UserPanel getUserPanel() {
        return userPanel;
    }

    public void addSlot(Slot slot) {
        this.slots.add(slot);
    }

    public boolean isSlotAvailable(Parcel parcel) {
        for (Slot slot : slots) {
            if (slot.isFree() && slot.isBiggerThanParcel(parcel)) {
                return true;
            }
        }

        return false;
    }

    public Slot findBestSlot(Parcel parcel) {
        assert (isSlotAvailable(parcel));

        Slot bestSlot = null;
        double minVolume = Parcel.getMaxVolume() + 1;
        for (Slot slot : slots) {
            if (slot.isFree() && slot.isBiggerThanParcel(parcel) && slot.getVolume() < minVolume) {
                bestSlot = slot;
                minVolume = slot.getVolume();
            }
        }

        return bestSlot;
    }

    public void addParcelToSlot(Parcel parcel, Person person) {
        assert (isSlotAvailable(parcel));

        Slot slot = findBestSlot(parcel);
        slot.addParcel(parcel, person);

        parcelInEvent(parcel, person);
        removeFromIncomingParcel(parcel);
    }

    private void addEvent(TypeEvent type, Parcel parcel, Person person) {
        EventLocker event = new EventLocker(type, this, parcel, person);
        events.add(event);
    }

    public void parcelInEvent(Parcel parcel, Person person) {
        System.out.println(person.getName() + " " + person.getSurname() + " put the parcel n°" + parcel.getId() + " in the " + getAddress() + " locker.");
        this.addEvent(TypeEvent.ParcelIn, parcel, person);
    }

    public void parcelOutEvent(Parcel parcel, Person person) {
        System.out.println(person.getName() + " " + person.getSurname() + " pick up the parcel n°" + parcel.getId() + " from the " + getAddress() + " locker.");
        this.addEvent(TypeEvent.ParcelOut, parcel, person);
    }

    public boolean isParcelInLocker(Parcel parcel) {
        for (Slot slot : slots) {
            if (slot.isParcelInSlot(parcel)) {
                return true;
            }
        }

        return false;
    }

    public Slot getSlotContainingParcel(Parcel parcel) {
        assert (isParcelInLocker(parcel));

        for (Slot slot : slots) {
            if (slot.isParcelInSlot(parcel)) {
                return slot;
            }
        }

        return null;
    }

    public void pickUpParcel(Parcel parcel, Person person) {
        assert (isParcelInLocker(parcel));

        Slot slot = this.getSlotContainingParcel(parcel);
        slot.getParcel();

        parcelOutEvent(parcel, person);
    }

    public List<Parcel> getParcelToDeliver() {
        ArrayList<Parcel> parcels = new ArrayList<>();
        for (Slot slot : slots) {
            if (!slot.isFree() && !slot.isParcelArrived()) {
                parcels.add(slot.getParcel());
            }
        }

        return parcels;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public boolean isEmpty() {
        for (Slot slot : slots) {
            if (!slot.isFree()) {
                return false;
            }
        }

        return true;
    }

    public void addToIncomingParcel(Parcel parcel) {
        this.incomingParcel.add(parcel);
    }

    private void removeFromIncomingParcel(Parcel parcel) {
        this.incomingParcel.remove(parcel);
    }

    public boolean changeAddress(String address) {
        if (!incomingParcel.isEmpty() || !this.isEmpty()) {
            return false;
        }

        this.setAddress(address);
        return true;
    }

    public void cleanHistory() {
        Date now = new Date();
        long dayInHour = 24;
        List<EventLocker> eventsToRemove = new ArrayList<>();
        for (EventLocker event : events) {
            if ((now.getTime() - event.getDate().getTime()) / (60 * 60 * 1000) > dayInHour * 7) {
                eventsToRemove.add(event);
            }
        }

        for (EventLocker event : eventsToRemove) {
            events.remove(event);
        }
    }

    private void cleanIncomingParcel() {
        List<Parcel> parcelToRemove = new ArrayList<>();
        for (Parcel parcel : incomingParcel) {
            if (parcel.isPickUpTimeExceeded()) {
                parcelToRemove.add(parcel);
            }
        }

        for (Parcel parcel : parcelToRemove) {
            incomingParcel.remove(parcel);
        }
    }

    public boolean willBeAvailable(Date date) {
        this.cleanIncomingParcel();

        int numberOfFreeSlots = 0;
        for (Slot slot : slots) {
            if (slot.isFree()) {
                numberOfFreeSlots++;
            } else if (!slot.willParcelBeInSlot(date)) {
                // Estimates the number of parcels to be picked up by date
                numberOfFreeSlots++;
            }
        }

        for (Parcel parcel : incomingParcel) {
            if (parcel.getEstimatedDeliveryTime() != null && date.getTime() - parcel.getEstimatedDeliveryTime().getTime() > 0) {
                // Estimates the number of parcels due to arrive by date
                numberOfFreeSlots--;
            }
        }

        return numberOfFreeSlots > 0;
    }
}
