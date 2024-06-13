import java.util.List;

public class SizeBasedSlotAllocationStrategy implements SlotAllocationStrategy {
    public boolean isSlotAvailable(Locker locker, ParcelComponent parcel) {
        Size parcelSize = parcel.getSize();
        List<Slot> availableSlots = locker.getAvailableSlots();

        for (Slot slot : availableSlots) {
            if (parcelSize.ordinal() <= slot.getSize().ordinal()) {
                return true;
            }
        }

        return false;
    }

    public Slot allocateSlot(Locker locker, ParcelComponent parcel) {
        Size parcelSize = parcel.getSize();
        List<Slot> availableSlots = locker.getAvailableSlots();

        for (Slot slot : availableSlots) {
            if (parcelSize.ordinal() <= slot.getSize().ordinal()) {
                return slot;
            }
        }

        return null;

        /*
        List<Size> acceptedSizes = getAcceptedSizes(parcelSize);

        for (Size size : acceptedSizes) {
            for (Slot slot : availableSlots) {
                if (size == slot.getSize()) {
                    return slot;
                }
            }
        }
        */
    }

    /*
    private List<Size> getAcceptedSizes(Size size) {
        List<Size> acceptedSizes = new ArrayList<>();
        switch (size) {
            case SMALL:
                acceptedSizes.add(Size.SMALL);
                acceptedSizes.add(Size.MEDIUM);
                acceptedSizes.add(Size.LARGE);
            case MEDIUM:
                acceptedSizes.add(Size.MEDIUM);
                acceptedSizes.add(Size.LARGE);
            case LARGE:
                acceptedSizes.add(Size.LARGE);
            default:
                break;
        }

        return acceptedSizes;
    }
    */
}
