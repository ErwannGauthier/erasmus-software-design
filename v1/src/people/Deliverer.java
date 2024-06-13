package people;

import objects.lockers.ExternalStorage;
import objects.lockers.Locker;
import objects.parcels.Parcel;
import objects.parcels.ParcelNotPickedUp;

import java.util.ArrayList;
import java.util.List;

public class Deliverer extends User {
    private List<Parcel> storage;

    public Deliverer(String name, String surname, String email, String password) {
        super(name, surname, email, password);
        this.storage = new ArrayList<>();
    }

    private void addParcelToStorage(Parcel parcel) {
        this.storage.add(parcel);
    }

    private void removeParcelFromStorage(Parcel parcel) {
        this.storage.remove(parcel);
    }

    public void pickUpParcelToDelivery(Locker locker) {
        List<Parcel> parcels = locker.getUserPanel().getParcelsToDeliver(this);
        for (Parcel parcel : parcels) {
            addParcelToStorage(parcel);
        }
    }

    public void deliverParcelsToLocker(Locker locker) {
        List<Parcel> parcelToRemove = new ArrayList<>();

        for (Parcel parcel : storage) {
            if (parcel.isDestinationLocker(locker) && locker.getUserPanel().addParcelToLocker(parcel, this)) {
                parcelToRemove.add(parcel);
            } else if (parcel.isDestinationLocker(locker)) {
                System.out.println("\t" + this.getName() + " " + this.getSurname() + " can't put the parcel n°" + parcel.getId() + " in the " + parcel.getReceiverLocker().getAddress() + " locker for the moment.");
            }
        }

        for (Parcel parcel : parcelToRemove) {
            this.removeParcelFromStorage(parcel);
        }
    }

    private List<Parcel> getStorageParcelsTimeExceeded() {
        List<Parcel> parcels = new ArrayList<>();
        for (Parcel parcel : storage) {
            if (parcel.isPickUpTimeExceeded()) {
                parcels.add(parcel);
            }
        }

        for (Parcel parcel : parcels) {
            storage.remove(parcel);
        }

        return parcels;
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

    private void disinformReceiverLocker(Parcel parcel) {
        if (parcel.getReceiverLocker().isParcelIncoming(parcel)) {
            parcel.getReceiverLocker().removeFromIncomingParcel(parcel);
        }
    }

    public void pickUpParcelsTimeExceeded(Locker locker, ExternalStorage externalStorage) {
        List<Parcel> parcelsExceeded = locker.getUserPanel().getParcelsPickUpTimeExceeded(this);
        parcelsExceeded.addAll(this.getStorageParcelsTimeExceeded());

        for (Parcel parcel : parcelsExceeded) {
            ParcelNotPickedUp parcelNotPickedUp = new ParcelNotPickedUp(parcel, externalStorage);

            disinformReceiver(parcel);
            disinformReceiverLocker(parcel);
            informReceiver(parcelNotPickedUp);
            informReceiverLocker(parcelNotPickedUp);

            addParcelToStorage(parcel);
        }
    }
}
