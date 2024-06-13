import java.util.*;

public class User extends Person {
    private final List<ParcelComponent> toSend;
    private final Map<UserPanel, List<UUID>> toPickUp;
    private final List<ParcelComponent> pickedUp;
    private String address;
    private String email;

    public User(String name, String surname, String address, String email, String phone) {
        super(name, surname, phone);
        this.address = address;
        this.email = email;
        this.toSend = new ArrayList<>();
        this.toPickUp = new HashMap<>();
        this.pickedUp = new ArrayList<>();
    }

    public void registerParcel(User recipient, Locker recipientLocker, Size size) {
        UserPortal userPortal = UserPortal.getInstance();
        ParcelComponent parcel = userPortal.registerParcel(this, recipient, recipientLocker, size);
        userPortal.makePayement(parcel);
        toSend.add(parcel);
    }

    public void trackParcel(UUID parcelIdentifier) {
        UserPortal userPortal = UserPortal.getInstance();
        userPortal.trackParcel(parcelIdentifier);
    }

    public void newParcelToPickUp(UUID parcelIdentifier, UserPanel userPanel) {
        if (!this.toPickUp.containsKey(userPanel)) {
            this.toPickUp.put(userPanel, new ArrayList<>());
        }

        this.toPickUp.get(userPanel).add(parcelIdentifier);
    }

    public boolean depose(UserPanel userPanel) {
        List<ParcelComponent> toDepose = new ArrayList<>(this.toSend);

        for (ParcelComponent parcel : toDepose) {
            boolean isSent = userPanel.depose(parcel);
            if (isSent) {
                this.toSend.remove(parcel);
            }
        }

        return this.toSend.isEmpty();
    }

    public boolean pickUp(UserPanel userPanel) {
        if (!this.toPickUp.containsKey(userPanel)) return false;

        for (UUID parcelIdentifier : new ArrayList<>(this.toPickUp.get(userPanel))) {
            ParcelComponent parcel = userPanel.pickUp(parcelIdentifier);
            if (parcel != null) {
                this.toPickUp.get(userPanel).remove(parcelIdentifier);
                this.pickedUp.add(parcel);
            }
        }

        return true;
    }

    public void message(String type, String from, String content) {
        System.out.println(this + " received a " + type + " message from " + from + ": " + content);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ParcelComponent> getToSend() {
        return toSend;
    }

    public Map<UserPanel, List<UUID>> getToPickUp() {
        return toPickUp;
    }

    public List<ParcelComponent> getPickedUp() {
        return pickedUp;
    }

    public String toString() {
        return "User " + super.toString();
    }
}
