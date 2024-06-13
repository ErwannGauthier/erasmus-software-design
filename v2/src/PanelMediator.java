import java.util.UUID;

public interface PanelMediator {
    boolean depose(ParcelComponent parcel);

    ParcelComponent pickUp(UUID parcelIdentifier);
}
