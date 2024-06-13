import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Slot implements Component {
    private final UUID identifier;
    private final Size size;
    private ParcelComponent parcel;

    public Slot(Size size) {
        this.identifier = UUID.randomUUID();
        this.size = size;
    }

    public boolean isAvailable() {
        return this.parcel == null;
    }

    public boolean isEmpty() {
        return isAvailable();
    }

    public List<Slot> getAvailableSlots() {
        List<Slot> availableSlots = new ArrayList<>();
        if (this.isAvailable()) {
            availableSlots.add(this);
        }

        return availableSlots;
    }

    public ParcelComponent pickUp(UUID identifier) {
        if (!this.isAvailable() && this.parcel.getIdentifier() == identifier) {
            ParcelComponent parcel = this.parcel;
            this.setParcel(null);
            return parcel;
        }

        return null;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public Size getSize() {
        return size;
    }

    public ParcelComponent getParcel() {
        return parcel;
    }

    public void setParcel(ParcelComponent parcel) {
        this.parcel = parcel;
    }
}
