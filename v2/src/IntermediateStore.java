import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntermediateStore {
    private final Map<CourrierPanel, List<ParcelComponent>> toDeliver;

    public IntermediateStore() {
        this.toDeliver = new HashMap<>();
    }

    public void addParcel(CourrierPanel courrierPanel, ParcelComponent parcel) {
        if (!toDeliver.containsKey(courrierPanel)) {
            toDeliver.put(courrierPanel, new ArrayList<>());
        }

        toDeliver.get(courrierPanel).add(parcel);
    }

    public void removeParcel(CourrierPanel courrierPanel, ParcelComponent parcel) {
        if (toDeliver.containsKey(courrierPanel)) {
            toDeliver.get(courrierPanel).remove(parcel);
        }
    }

    public boolean haveToDeliver(CourrierPanel panel) {
        return toDeliver.containsKey(panel) && !toDeliver.get(panel).isEmpty();
    }

    public List<ParcelComponent> getParcels(CourrierPanel courrierPanel) {
        if (!haveToDeliver(courrierPanel)) return new ArrayList<>();
        return toDeliver.get(courrierPanel);
    }

    public Map<CourrierPanel, List<ParcelComponent>> getToDeliver() {
        return toDeliver;
    }
}
