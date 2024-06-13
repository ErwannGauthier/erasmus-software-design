public class StateLost implements State {
    private ParcelComponent parcel;

    public StateLost(ParcelComponent parcel) {
        this.parcel = parcel;
    }

    public void handleEvent(Event event) {
        System.out.println("Parcel lost, you can start compensation process.");
    }

    public String getStatus() {
        return "Lost";
    }
}
