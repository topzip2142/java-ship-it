package ru.yandex.practicum.delivery.parcels;

public class PerishableParcel extends Parcel {
    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    public boolean isExpired(int currentDay) {
        return (sendDay + timeToLive) > currentDay;
    }

    @Override
    public int getBaseCost() {
        return Parcel.BASE_COST_PERISHABLE;
    }
}