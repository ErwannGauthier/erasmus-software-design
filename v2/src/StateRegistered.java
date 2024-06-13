public class StateRegistered implements State {
    private ParcelComponent parcel;

    public StateRegistered(ParcelComponent parcel) {
        this.parcel = parcel;
    }

    public void handleEvent(Event event) {
        if (event.getType() == EventType.SENT) {
            parcel.setState(new StateSent(parcel));
        }
    }

    public String getStatus() {
        return "Registered";
    }
}
