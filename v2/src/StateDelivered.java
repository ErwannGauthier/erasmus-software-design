import java.time.LocalDateTime;

public class StateDelivered implements State {
    private ParcelComponent parcel;

    public StateDelivered(ParcelComponent parcel) {
        this.parcel = parcel;
        parcel.setActualDeliveryTime(LocalDateTime.now());
        parcel.getRecipient().newParcelToPickUp(parcel.getIdentifier(), parcel.getRecipientLocker().getUserPanel());
    }

    public void handleEvent(Event event) {
        if (event.getType() == EventType.PICKED_UP) {
            parcel.setState(new StatePickedUp(parcel));
        } else if (event.getType() == EventType.LOST) {
            parcel.setState(new StateLost(parcel));
        }
    }

    public String getStatus() {
        return "Delivered";
    }
}
