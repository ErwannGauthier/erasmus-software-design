public class SizeBasedPayementStategy implements PayementStrategy {
    private final float BASE_PRICE = 10;

    public float getAmountToPay(ParcelComponent parcel) {
        Size size = parcel.getSize();
        return BASE_PRICE + ((float) (1 + (20 * size.ordinal())) / 100);
    }
}
