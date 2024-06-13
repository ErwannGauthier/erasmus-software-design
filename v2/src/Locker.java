import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Locker implements Component {
    private final UUID identifier;
    private final List<Module> modules;
    private final List<Event> history;
    private final List<UUID> inComingParcel;
    private String address;
    private UserPanel userPanel;
    private CourrierPanel courrierPanel;
    private SlotAllocationStrategy slotAllocationStrategy;

    public Locker(String address) {
        this.identifier = UUID.randomUUID();
        this.address = address;
        this.modules = new ArrayList<>();
        this.history = new ArrayList<>();
        this.inComingParcel = new ArrayList<>();
        this.userPanel = new UserPanel(this);
        this.courrierPanel = new CourrierPanel(this);
        this.slotAllocationStrategy = new SizeBasedSlotAllocationStrategy();
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public void removeModule(Module module) {
        this.modules.remove(module);
    }

    public void addEvent(Event event) {
        this.history.add(event);
    }

    public void addIncomingParcel(UUID parcelIdentifier) {
        this.inComingParcel.add(parcelIdentifier);
    }

    public void removeIncomingParcel(UUID parcelIdentifier) {
        this.inComingParcel.remove(parcelIdentifier);
    }

    public boolean isEmpty() {
        for (Module module : modules) {
            if (!module.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public List<Slot> getAvailableSlots() {
        ArrayList<Slot> availableSlots = new ArrayList<>();
        for (Module module : modules) {
            availableSlots.addAll(module.getAvailableSlots());
        }

        return availableSlots;
    }

    public boolean isSlotAvailable(ParcelComponent parcel) {
        return slotAllocationStrategy.isSlotAvailable(this, parcel);
    }

    public Slot allocateSlot(ParcelComponent parcel) {
        return slotAllocationStrategy.allocateSlot(this, parcel);
    }

    public boolean depose(ParcelComponent parcel) {
        Slot slot = this.allocateSlot(parcel);
        if (slot == null) {
            System.out.println("No suitable slot available for the parcel in " + this);
            return false;
        }

        slot.setParcel(parcel);
        return true;
    }

    public ParcelComponent pickUp(UUID parcelIdentifier) {
        for (Module module : modules) {
            ParcelComponent parcel = module.pickUp(parcelIdentifier);
            if (parcel != null) {
                return parcel;
            }
        }

        return null;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public String getAddress() {
        return address;
    }

    public boolean setAddress(String address) {
        if (!this.isEmpty() || !this.inComingParcel.isEmpty()) return false;

        this.address = address;
        return true;
    }

    public List<Module> getModules() {
        return Collections.unmodifiableList(modules);
    }

    public List<Event> getHistory() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Event> events = new ArrayList<>();
        for (Event event : this.history) {
            if (event.getTimestamp().isAfter(sevenDaysAgo)) {
                events.add(event);
            }
        }

        return Collections.unmodifiableList(events);
    }

    public List<UUID> getInComingParcel() {
        return inComingParcel;
    }

    public UserPanel getUserPanel() {
        return userPanel;
    }

    public void setUserPanel(UserPanel userPanel) {
        this.userPanel = userPanel;
    }

    public CourrierPanel getCourrierPanel() {
        return courrierPanel;
    }

    public void setCourrierPanel(CourrierPanel courrierPanel) {
        this.courrierPanel = courrierPanel;
    }

    public SlotAllocationStrategy getSlotAllocationStrategy() {
        return slotAllocationStrategy;
    }

    public void setSlotAllocationStrategy(SlotAllocationStrategy slotAllocationStrategy) {
        this.slotAllocationStrategy = slotAllocationStrategy;
    }

    public String toString() {
        return "Locker (" + identifier.toString() + ") from " + this.address;
    }
}
