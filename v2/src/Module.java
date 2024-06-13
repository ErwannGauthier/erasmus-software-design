import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Module implements Component {
    private final List<Component> components;

    public Module() {
        this.components = new ArrayList<>();
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }

    public void removeComponent(Component component) {
        this.components.remove(component);
    }

    public boolean isEmpty() {
        for (Component component : components) {
            if (!component.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public List<Slot> getAvailableSlots() {
        List<Slot> availableSlots = new ArrayList<>();
        for (Component component : this.components) {
            availableSlots.addAll(component.getAvailableSlots());
        }

        return availableSlots;
    }

    public ParcelComponent pickUp(UUID identifier) {
        for (Component component : components) {
            ParcelComponent parcel = component.pickUp(identifier);
            if (parcel != null) {
                return parcel;
            }
        }

        return null;
    }

    public List<Component> getComponents() {
        return components;
    }
}
