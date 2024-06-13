public interface SlotAllocationStrategy {
    boolean isSlotAvailable(Locker locker, ParcelComponent parcel);

    Slot allocateSlot(Locker locker, ParcelComponent parcel);
}
