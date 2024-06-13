import java.util.List;
import java.util.UUID;

public interface Component {
    boolean isEmpty();

    List<Slot> getAvailableSlots();

    ParcelComponent pickUp(UUID parcelIdentifier);
}