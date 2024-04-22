package people;

import objects.Parcel;
import objects.lockers.Locker;

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
                System.out.println(this.getName() + " " + this.getSurname() + " can't put the parcel n°" + parcel.getId() + " in the " + parcel.getReceiverLocker().getAddress() + " locker for the moment.");
            }
        }

        for (Parcel parcel : parcelToRemove) {
            this.removeParcelFromStorage(parcel);
        }
    }
}
