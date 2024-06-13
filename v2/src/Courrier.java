import java.util.*;

public class Courrier extends Person {
    private final Map<CourrierPanel, List<UUID>> toPickUp;
    private IntermediateStore intermediateStore;

    public Courrier(String name, String surname, String phone) {
        super(name, surname, phone);
        toPickUp = new HashMap<>();
        intermediateStore = new IntermediateStore();
    }

    public void newParcelToPickUp(UUID parcelIdentifier, CourrierPanel courrierPanel) {
        if (!this.toPickUp.containsKey(courrierPanel)) {
            this.toPickUp.put(courrierPanel, new ArrayList<>());
        }

        this.toPickUp.get(courrierPanel).add(parcelIdentifier);
    }

    public boolean depose(CourrierPanel courrierPanel) {
        if (!this.intermediateStore.haveToDeliver(courrierPanel)) return false;

        List<ParcelComponent> toDepose = this.intermediateStore.getParcels(courrierPanel);
        for (ParcelComponent parcel : toDepose) {
            boolean isDelivered = courrierPanel.depose(parcel);
            if (isDelivered) {
                this.intermediateStore.removeParcel(courrierPanel, parcel);
            }
        }

        return this.intermediateStore.getParcels(courrierPanel).isEmpty();
    }

    public boolean pickUp(CourrierPanel courrierPanel) {
        if (!this.toPickUp.containsKey(courrierPanel)) return false;

        for (UUID parcelIdentifier : new ArrayList<>(this.toPickUp.get(courrierPanel))) {
            ParcelComponent parcel = courrierPanel.pickUp(parcelIdentifier);
            if (parcel != null) {
                this.toPickUp.get(courrierPanel).remove(parcelIdentifier);
                CourrierPanel recipientCourrierPanel = parcel.getRecipientLocker().getCourrierPanel();
                this.intermediateStore.addParcel(recipientCourrierPanel, parcel);
            }
        }

        return true;
    }

    public void message(String from, String content) {
        System.out.println(this + " received a SMS message from " + from + ": " + content);
    }

    public String toString() {
        return "Courrier " + super.toString();
    }
}
