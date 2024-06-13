import java.time.LocalDateTime;

public class StatePickedUp implements State {
    private ParcelComponent parcel;

    public StatePickedUp(ParcelComponent parcel) {
        this.parcel = parcel;
        this.parcel.setActualPickupTime(LocalDateTime.now());
    }

    public void handleEvent(Event event) {
        System.out.println("Parcel picked up. End of the delivering process.");
    }

    public String getStatus() {
        return "Picked up";
    }
}
