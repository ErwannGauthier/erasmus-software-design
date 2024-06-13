package objects.parcels;

import events.EventParcel;
import events.TypeEvent;
import objects.lockers.Locker;

import java.util.Date;

public class ParcelNotPickedUp extends Parcel {
    private Locker newReceiverLocker;
    private Date delayExceedDate;
    private boolean extraDay;

    public ParcelNotPickedUp(Parcel parcel, Locker newReceivingLocker) {
        super(parcel);
        this.newReceiverLocker = newReceivingLocker;
        this.delayExceedDate = new Date();
        this.extraDay = false;
    }

    public Locker getPreviousReceiverLocker() {
        return super.getReceiverLocker();
    }

    public Locker getReceiverLocker() {
        return this.newReceiverLocker;
    }

    protected EventParcel getDeliveryEvent() {
        EventParcel deliveryEvent = null;
        for (EventParcel event : super.getEvents()) {
            if (event.getType() == TypeEvent.ParcelIn && event.getLockerRelatedTo() == newReceiverLocker) {
                deliveryEvent = event;
            }
        }

        return deliveryEvent;
    }

    public boolean isArrived() {
        EventParcel lastEvent = getLastEvent();
        if (lastEvent == null) {
            return false;
        }

        return lastEvent.getType() == TypeEvent.ParcelIn && lastEvent.getLockerRelatedTo() == newReceiverLocker;
    }

    private void setExtraDay(boolean extraDay) {
        this.extraDay = extraDay;
    }

    public boolean isExtraDayPayed() {
        return this.extraDay;
    }

    public boolean payExtraDay() {
        if (this.extraDay) {
            return false;
        }

        this.setExtraDay(true);
        return true;
    }

    // Estimated delivery time is always 24 hours after the sender has put the parcel in the locker.
    public Date getEstimatedDeliveryTime() {
        int dayInMs = 24 * 60 * 60 * 1000;
        return new Date(this.delayExceedDate.getTime() + dayInMs);
    }

    // Guaranteed delivery time is always 48 hours after the sender has put the parcel in the locker.
    public Date getGuaranteedDeliveryTime() {
        int dayInMs = 24 * 60 * 60 * 1000;
        return new Date(this.delayExceedDate.getTime() + dayInMs * 2);
    }

    // Returns null if the parcel is not in the receiver locker yet.
    public Date getDeliveryTime() {
        EventParcel deliveryEvent = this.getDeliveryEvent();
        if (deliveryEvent == null) {
            return null;
        }

        Date deliveryDate = deliveryEvent.getDate();
        return new Date(deliveryDate.getTime() - this.delayExceedDate.getTime());
    }

    // Returns null if the parcel is not in the receiver locker yet or if it is still in.
    public Date getPickUpTime() {
        EventParcel deliveryEvent = this.getDeliveryEvent();
        if (deliveryEvent == null) {
            return null;
        }

        EventParcel pickupEvent = getPickUpEvent();
        if (pickupEvent == null) {
            return null;
        }

        Date deliveryDate = deliveryEvent.getDate();
        Date pickupDate = pickupEvent.getDate();
        return new Date(pickupDate.getTime() - deliveryDate.getTime());
    }

    public Date getMaxPickUpTime() {
        EventParcel deliveryEvent = getDeliveryEvent();
        if (deliveryEvent == null) {
            return null;
        }

        int dayInMs = 24 * 60 * 60 * 1000;
        int numberOfDaysMax = 5;
        if (this.extraDay) {
            numberOfDaysMax++;
        }
        Date deliveryDate = deliveryEvent.getDate();
        return new Date(deliveryDate.getTime() + dayInMs * numberOfDaysMax);
    }

    public boolean isPickUpTimeExceeded() {
        Date maxPickupTime = getMaxPickUpTime();
        if (maxPickupTime == null) {
            return false;
        }

        Date now = new Date();
        return (maxPickupTime.getTime() - now.getTime()) < 0;
    }
}
