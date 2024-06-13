public class RedirectedParcel extends ParcelDecorator {
    ExternalStorage externalStorage;

    public RedirectedParcel(ParcelComponent parcel, ExternalStorage externalStorage) {
        super(parcel);
        this.externalStorage = externalStorage;
    }
}
