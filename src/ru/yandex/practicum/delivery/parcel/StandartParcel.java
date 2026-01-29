package ru.yandex.practicum.delivery.parcel;

public class StandartParcel extends Parcel{


    public StandartParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }


    @Override
    public double calculateDeliveryCost() {
        return 0;
    }
}
