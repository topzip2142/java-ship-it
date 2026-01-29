package ru.yandex.practicum.delivery.parcel;

public class FragileParcel extends Parcel{
    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println("Посылка <<" + description + ">> обёрнута в защитную плёнку");
        super.packageItem();
    }

    @Override
    public double calculateDeliveryCost() {
        return 0;
    }
}
