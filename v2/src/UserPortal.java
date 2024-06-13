import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserPortal implements UserPortalMediator, Subscriber {
    private static UserPortal instance;
    private List<ParcelComponent> parcels;
    private PayementStrategy payementStrategy;

    private UserPortal() {
        this.parcels = new ArrayList<>();
        this.payementStrategy = new SizeBasedPayementStategy();
    }

    public static UserPortal getInstance() {
        if (instance == null) {
            UserPortal.instance = new UserPortal();
        }

        return instance;
    }

    public ParcelComponent registerParcel(User sender, User recipient, Locker recipientLocker, Size size) {
        ParcelComponent parcel = new Parcel(sender, recipient, recipientLocker, size);
        parcel.addEvent(new Event(EventType.REGISTERED, parcel, null));
        this.parcels.add(parcel);
        return parcel;
    }

    public void makePayement(ParcelComponent parcel) {
        float amount = this.payementStrategy.getAmountToPay(parcel);
        this.print("You paid " + amount + "PLN to register " + parcel);
    }

    public void trackParcel(UUID parcelIdentifier) {
        ParcelComponent parcel = this.getParcel(parcelIdentifier);
        if (parcel == null) {
            this.print("Parcel not found");
            return;
        }

        List<Event> events = parcel.getTransitRecords();
        this.print("Tracking " + parcel);
        for (Event event : events) {
            System.out.println(event.toString());
        }
    }

    public void update(ParcelComponent parcel, Event event) {
        if (event.getType() == EventType.DELIVERED) {
            parcel.getRecipient().message("SMS", "UserPortal", parcel + " is available for pick-up in the" + parcel.getRecipientLocker() + ".");
        } else if (event.getType() == EventType.LOST) {
            parcel.getSender().message("SMS", "UserPortal", parcel + " is lost, you can start compensation process.");
            parcel.getRecipient().message("SMS", "UserPortal", parcel + " is lost, you can start compensation process.");
        }

        parcel.getSender().message("Mail", "UserPortal", parcel + " has a new status: " + parcel.getStatus() + ".");
        parcel.getRecipient().message("Mail", "UserPortal", parcel + " has a new status: " + parcel.getStatus() + ".");
    }

    private ParcelComponent getParcel(UUID parcelIdentifier) {
        for (ParcelComponent parcel : this.parcels) {
            if (parcelIdentifier.equals(parcel.getIdentifier())) {
                return parcel;
            }
        }

        return null;
    }

    private void print(String message) {
        System.out.println("----- User Portal -----");
        System.out.println(message);
        System.out.println("----------");
    }

    public PayementStrategy getPayementStrategy() {
        return payementStrategy;
    }

    public void setPayementStrategy(PayementStrategy payementStrategy) {
        this.payementStrategy = payementStrategy;
    }
}
