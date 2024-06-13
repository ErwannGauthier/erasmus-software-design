import java.util.UUID;

public class UserPanel extends Panel implements UserPanelMediator {
    public UserPanel(Locker locker) {
        super(locker);
    }

    public boolean depose(ParcelComponent parcel) {
        if (!super.getLocker().isSlotAvailable(parcel)) return false;
        boolean isDeposed = super.getLocker().depose(parcel);
        if (!isDeposed) return false;

        parcel.setSenderLocker(super.getLocker());
        parcel.addEvent(new Event(EventType.SENT, parcel, parcel.getSenderLocker()));
        super.getLocker().addEvent(new Event(EventType.SENT, parcel, super.getLocker()));
        parcel.getRecipientLocker().addIncomingParcel(parcel.getIdentifier());
        return true;
    }

    public ParcelComponent pickUp(UUID parcelIdentifier) {
        ParcelComponent parcel = super.getLocker().pickUp(parcelIdentifier);
        if (parcel == null) {
            return null;
        }

        parcel.addEvent(new Event(EventType.PICKED_UP, parcel, parcel.getRecipientLocker()));
        super.getLocker().addEvent(new Event(EventType.PICKED_UP, parcel, super.getLocker()));
        return parcel;
    }
}
