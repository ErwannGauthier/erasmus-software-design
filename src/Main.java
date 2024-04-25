import objects.lockers.ExternalStorage;
import objects.lockers.Locker;
import objects.lockers.Slot;
import objects.parcels.Parcel;
import people.Deliverer;
import people.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Locker> generateLockers() {
        List<Locker> lockers = new ArrayList<>();
        Locker warszawa = new Locker("Warszawa");
        Locker krakow = new Locker("Kraków");
        Locker lodz = new Locker("Łódź");
        Locker wroclaw = new Locker("Wrocław");
        Locker poznan = new Locker("Poznań");
        lockers.add(warszawa);
        lockers.add(krakow);
        lockers.add(lodz);
        lockers.add(wroclaw);
        lockers.add(poznan);
        return lockers;
    }

    public static List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User("user", "1", "user1@email.com", "1");
        User user2 = new User("user", "2", "user2@email.com", "2");
        User user3 = new User("user", "3", "user3@email.com", "3");
        User user4 = new User("user", "4", "user4@email.com", "4");
        User user5 = new User("user", "5", "user5@email.com", "5");
        User user6 = new User("user", "6", "user6@email.com", "6");
        User user7 = new User("user", "7", "user7@email.com", "7");
        User user8 = new User("user", "8", "user8@email.com", "8");
        User user9 = new User("user", "9", "user9@email.com", "9");
        User user10 = new User("user", "10", "user10@email.com", "10");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        users.add(user9);
        users.add(user10);
        return users;
    }

    public static void addRandomSlotToLocker(Locker locker, int numberOfSlots) {
        for (int i = 0; i < numberOfSlots; i++) {
            locker.addSlot(Slot.randomSlot(locker));
        }
    }

    public static void userRandomlySendParcel(List<User> users, List<Locker> lockers, User sender, int numberOfParcel) {
        int senderIndex = users.indexOf(sender);
        for (int i = 0; i < numberOfParcel; i++) {
            int randomIndexUser = (int) Math.floor(Math.random() * users.size());
            while (randomIndexUser == senderIndex) {
                randomIndexUser = (int) Math.floor(Math.random() * users.size());
            }

            int randomSenderLocker = (int) Math.floor(Math.random() * lockers.size());
            int randomReceiverLocker = (int) Math.floor(Math.random() * lockers.size());
            while (randomReceiverLocker == randomSenderLocker) {
                randomReceiverLocker = (int) Math.floor(Math.random() * lockers.size());
            }

            User receiver = users.get(randomIndexUser);
            Locker senderLocker = lockers.get(randomSenderLocker);
            Locker receiverLocker = lockers.get(randomReceiverLocker);
            Parcel randomParcel = Parcel.randomParcel(sender, receiver, senderLocker, receiverLocker);
            sender.addParcelToSend(randomParcel);
        }

        sender.sendParcels();
    }

    public static void main(String[] args) {
        List<User> users = generateUsers();
        Deliverer deliverer = new Deliverer("Fast", "Deliverer", "DelivererFast@email.com", "deliverer");
        List<Locker> lockers = generateLockers();
        for (Locker locker : lockers) {
            addRandomSlotToLocker(locker, 10);
        }
        ExternalStorage externalStorage = new ExternalStorage("External Storage Address");

        int days = 0;

        Scanner scanner = new Scanner(System.in);
        String input = "";

        // Loop until the user presses 'q'
        while (!input.equalsIgnoreCase("q")) {
            days++;
            System.out.println("Day " + days + " start :");

            System.out.println("\nSenders send parcel...");
            for (User user : users) {
                userRandomlySendParcel(users, lockers, user, 3);
            }

            System.out.println("\nDeliverer start his work...");
            for (Locker locker : lockers) {
                System.out.println("\nDeliverer arrive to " + locker.getAddress() + " locker.");
                System.out.println("Deliver start to pick up parcels...");
                deliverer.pickUpParcelToDelivery(locker);
                System.out.println("Deliver start to pick up parcels with exceeded pick up time...");
                deliverer.pickUpParcelsTimeExceeded(locker, externalStorage);
                System.out.println("Deliver start to put parcels...");
                deliverer.deliverParcelsToLocker(locker);
            }

            System.out.println("\nReceivers pick up parcel...");
            for (User user : users) {
                user.pickUpParcels();
            }

            System.out.println("\nDeliverer goes to external storage...");
            deliverer.deliverParcelsToLocker(externalStorage);

            System.out.println("\nPress 'q' to quit or another key to continue.\n");
            input = scanner.nextLine();
        }

        System.out.println("You pressed 'q'. Exiting the loop.");

        scanner.close();
    }
}