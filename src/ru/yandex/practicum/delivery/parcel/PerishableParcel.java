package ru.yandex.practicum.delivery.parcel;

public class PerishableParcel extends Parcel {
    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void deliver() {

    }

    @Override
    public double calculateDeliveryCost() {
        return 0;
    }
}
