public class StateSent implements State {
    private ParcelComponent parcel;
    private Courrier courrier;

    public StateSent(ParcelComponent parcel) {
        this.parcel = parcel;
        //courrier.newParcelToPickUp(parcel.getIdentifier(), parcel.getSenderLocker().getCourrierPanel());
    }

    public void handleEvent(Event event) {
        if (event.getType() == EventType.TRANSITING) {
            parcel.setState(new StateTransiting(parcel));
        } else if (event.getType() == EventType.LOST) {
            parcel.setState(new StateLost(parcel));
        }
    }

    public String getStatus() {
        return "Sent";
    }
}
