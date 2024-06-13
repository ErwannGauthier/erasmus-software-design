import java.util.UUID;

public class CourrierPanel extends Panel implements CourrierPanelMediator {
    public CourrierPanel(Locker locker) {
        super(locker);
    }

    public ParcelComponent pickUp(UUID parcelIdentifier) {
        ParcelComponent parcel = super.getLocker().pickUp(parcelIdentifier);
        if (parcel == null) {
            return null;
        }

        parcel.addEvent(new Event(EventType.TRANSITING, parcel, parcel.getSenderLocker()));
        super.getLocker().addEvent(new Event(EventType.TRANSITING, parcel, super.getLocker()));
        return parcel;
    }

    public boolean depose(ParcelComponent parcel) {
        if (!super.getLocker().isSlotAvailable(parcel)) return false;
        boolean isDeposed = super.getLocker().depose(parcel);
        if (!isDeposed) return false;

        parcel.addEvent(new Event(EventType.DELIVERED, parcel, parcel.getRecipientLocker()));
        super.getLocker().addEvent(new Event(EventType.DELIVERED, parcel, super.getLocker()));
        super.getLocker().removeIncomingParcel(parcel.getIdentifier());
        return true;
    }
}
