import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ExternalStorage {
    private final UUID identifier;
    private List<ParcelComponent> storedParcels;

    public ExternalStorage() {
        this.identifier = UUID.randomUUID();
        this.storedParcels = new ArrayList<ParcelComponent>();
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public List<ParcelComponent> getStoredParcels() {
        return Collections.unmodifiableList(storedParcels);
    }

    public void setStoredParcels(List<ParcelComponent> storedParcels) {
        this.storedParcels = storedParcels;
    }
}
