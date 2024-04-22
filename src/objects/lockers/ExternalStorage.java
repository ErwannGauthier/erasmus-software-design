package objects.lockers;

import objects.Parcel;
import people.Person;

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
}
