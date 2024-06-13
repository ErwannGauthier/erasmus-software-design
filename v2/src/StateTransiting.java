public class StateTransiting implements State {
    private ParcelComponent parcel;

    public StateTransiting(ParcelComponent parcel) {
        this.parcel = parcel;
    }

    public void handleEvent(Event event) {
        if (event.getType() == EventType.DELIVERED) {
            parcel.setState(new StateDelivered(parcel));
        } else if (event.getType() == EventType.LOST) {
            parcel.setState(new StateLost(parcel));
        }
    }

    public String getStatus() {
        return "Transiting";
    }
}
