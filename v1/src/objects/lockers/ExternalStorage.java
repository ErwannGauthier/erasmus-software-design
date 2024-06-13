package objects.lockers;

import objects.parcels.Parcel;
import people.Person;

import java.util.Date;

public class ExternalStorage extends Locker {

    public ExternalStorage(String address) {
        super(address);
    }

    public void addParcelToSlot(Parcel parcel, Person person) {
        if (!super.isSlotAvailable(parcel)) {
            super.addSlot(new Slot(Parcel.MAX_LENGTH, Parcel.MAX_WIDTH, Parcel.MAX_HEIGHT, this));
        }

        Slot slot = super.findBestSlot(parcel);
        slot.addParcel(parcel, person);

        parcelInEvent(parcel, person);
    }

    public boolean willBeAvailable(Date date) {
        return true;
    }
}
