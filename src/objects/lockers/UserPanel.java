package objects.lockers;

import objects.Parcel;
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
        parcel.getReceiver().addToIncomingParcel(parcel);
    }

    private void informReceiverLocker(Parcel parcel) {
        parcel.getReceiverLocker().addToIncomingParcel(parcel);
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
}
