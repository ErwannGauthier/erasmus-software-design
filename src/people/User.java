package people;

import objects.Parcel;
import objects.lockers.Locker;

import java.util.ArrayList;
import java.util.List;

public class User extends Person {
    private List<Parcel> toSend;
    private List<Parcel> incomingParcel;
    private List<Parcel> received;

    public User(String name, String surname, String email, String password) {
        super(name, surname, email, password);
        this.toSend = new ArrayList<>();
        this.incomingParcel = new ArrayList<>();
        this.received = new ArrayList<>();
    }

    public void addParcelToSend(Parcel parcel) {
        this.toSend.add(parcel);
    }

    private void removeParcelToSend(Parcel parcel) {
        this.toSend.remove(parcel);
    }

    public void addToIncomingParcel(Parcel parcel) {
        this.incomingParcel.add(parcel);
    }

    private void removeFromIncomingParcel(Parcel parcel) {
        this.incomingParcel.remove(parcel);
    }

    public void addToReceived(Parcel parcel) {
        assert (this.incomingParcel.contains(parcel));
        this.removeFromIncomingParcel(parcel);
        this.received.add(parcel);
    }

    public void createParcel(double length, double width, double height, User receiver, Locker senderLocker, Locker receiverLocker) {
        Parcel parcel = new Parcel(length, width, height, this, receiver, senderLocker, receiverLocker);
        this.addParcelToSend(parcel);
    }

    public void sendParcels() {
        List<Parcel> parcelToRemove = new ArrayList<>();

        for (Parcel parcel : this.toSend) {
            if (parcel.getSenderLocker().getUserPanel().addParcelToLocker(parcel, this)) {
                parcelToRemove.add(parcel);
            } else {
                System.out.println(parcel.getSender().getName() + " " + parcel.getSender().getSurname() + " can't put the parcel n°" + parcel.getId() + " in the " + parcel.getSenderLocker().getAddress() + " locker for the moment.");
            }
        }

        for (Parcel parcel : parcelToRemove) {
            this.removeParcelToSend(parcel);
        }
    }

    public void pickUpParcels() {
        List<Parcel> parcelToReceive = new ArrayList<>();

        for (Parcel parcel : this.incomingParcel) {
            if (parcel.isArrived() && parcel.getReceiverLocker().getUserPanel().pickUpParcelFromLocker(parcel, this)) {
                parcelToReceive.add(parcel);
            }
        }

        for (Parcel parcel : parcelToReceive) {
            this.addToReceived(parcel);
        }
    }
}
