import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        User sender = new User("John", "Rednes", "Poznan", "sender@email.com", "0123456789");
        User recipient = new User("Bob", "Tneipicer", "Krakow", "recipient@email.com", "0987654321");
        Courrier courrier = new Courrier("Alice", "Reirruoc", "0011223344");

        Locker senderLocker = new Locker("Poznan");
        Module senderSmallModule = new Module();
        Module senderMediumModule = new Module();
        Module senderLargeModule = new Module();
        senderSmallModule.addComponent(new Slot(Size.SMALL));
        senderSmallModule.addComponent(new Slot(Size.SMALL));
        senderSmallModule.addComponent(new Slot(Size.SMALL));
        senderMediumModule.addComponent(new Slot(Size.MEDIUM));
        senderMediumModule.addComponent(new Slot(Size.MEDIUM));
        senderMediumModule.addComponent(new Slot(Size.MEDIUM));
        senderLargeModule.addComponent(new Slot(Size.LARGE));
        senderLargeModule.addComponent(new Slot(Size.LARGE));
        senderLargeModule.addComponent(new Slot(Size.LARGE));
        senderLocker.addModule(senderSmallModule);
        senderLocker.addModule(senderMediumModule);
        senderLocker.addModule(senderLargeModule);
        Locker recipientLocker = new Locker("Krakow");
        Module recipientSmallModule = new Module();
        Module recipientLargeModule = new Module();
        recipientSmallModule.addComponent(new Slot(Size.SMALL));
        recipientLargeModule.addComponent(new Slot(Size.LARGE));
        recipientLocker.addModule(recipientSmallModule);
        recipientLocker.addModule(recipientLargeModule);

        sender.registerParcel(recipient, recipientLocker, Size.MEDIUM);
        List<ParcelComponent> senderParcelsToSend = sender.getToSend();
        UUID parcelIdentifier = senderParcelsToSend.get(0).getIdentifier();
        boolean deposeAll = sender.depose(senderLocker.getUserPanel());
        if (!deposeAll) {
            System.out.println(sender + " didn't depose all parcels.");
        }

        sender.trackParcel(parcelIdentifier);

        boolean pickedUp = courrier.pickUp(senderLocker.getCourrierPanel());
        if (!pickedUp) {
            System.out.println(courrier + " didn't pick up parcels in " + senderLocker);
        }

        boolean deposed = courrier.depose(recipientLocker.getCourrierPanel());
        if (!deposed) {
            System.out.println(courrier + " didn't deposed parcel in " + recipientLocker);
        }
    }
}