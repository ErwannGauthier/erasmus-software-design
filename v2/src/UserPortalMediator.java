import java.util.UUID;

public interface UserPortalMediator {
    ParcelComponent registerParcel(User sender, User recipient, Locker recipientLocker, Size size);

    void makePayement(ParcelComponent parcel);

    void trackParcel(UUID parcelIdentifier);
}
