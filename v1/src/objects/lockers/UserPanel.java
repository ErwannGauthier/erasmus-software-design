package objects.lockers;

import objects.parcels.Parcel;
import people.Deliverer;
import people.Person;

import java.util.ArrayList;
import java.util.List;

public class UserPanel {
    private final Locker locker;

    public UserPanel(Locker locker) {
        this.locker = locker;
    }

    private void informReceiver(Parcel parcel) {
        if (!parcel.getReceiver().isParcelIncoming(parcel)) {
            parcel.getReceiver().addToIncomingParcel(parcel);
        }
    }

    private void disinformReceiver(Parcel parcel) {
        if (parcel.getReceiver().isParcelIncoming(parcel)) {
            parcel.getReceiver().removeFromIncomingParcel(parcel);
        }
    }

    private void informReceiverLocker(Parcel parcel) {
        if (!parcel.getReceiverLocker().isParcelIncoming(parcel)) {
            parcel.getReceiverLocker().addToIncomingParcel(parcel);
        }
    }

    public boolean addParcelToLocker(Parcel parcel, Person person) {
        if (!locker.isSlotAvailable(parcel)) {
            return false;
        }

        locker.addParcelToSlot(parcel, person);
        if (locker == parcel.getSenderLocker()) {
            informReceiver(parcel);
            informReceiverLocker(parcel);
        }
        return true;
    }

    public boolean pickUpParcelFromLocker(Parcel parcel, Person person) {
        if (!locker.isParcelInLocker(parcel)) {
            return false;
        }

        locker.pickUpParcel(parcel, person);
        return true;
    }

    public List<Parcel> getParcelsToDeliver(Deliverer deliverer) {
        List<Parcel> parcels = new ArrayList<>();
        List<Parcel> parcelsUp = locker.getParcelToDeliver();
        for (Parcel parcel : parcelsUp) {
            if (pickUpParcelFromLocker(parcel, deliverer)) {
                parcels.add(parcel);
            }
        }

        return parcels;
    }

    public List<Parcel> getParcelsPickUpTimeExceeded(Deliverer deliverer) {
        List<Parcel> parcels = new ArrayList<>();
        List<Parcel> parcelsExceeded = locker.getParcelsPickUpTimeExceeded();
        for (Parcel parcel : parcelsExceeded) {
            if (pickUpParcelFromLocker(parcel, deliverer)) {
                disinformReceiver(parcel);
                parcels.add(parcel);
            }
        }

        return parcels;
    }
}
