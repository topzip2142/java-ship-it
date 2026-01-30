package ru.yandex.practicum.delivery.parcels;

import ru.yandex.practicum.delivery.tools.Trackable;

public class FragileParcel extends Parcel implements Trackable {
    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println("Посылка <<" + description + ">> обёрнута в защитную плёнку");
        super.packageItem();
    }

    @Override
    public int getBaseCost() {
        return Parcel.BASE_COST_FRAGILE;
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.println("Хрупкая посылка <<" + description + ">> изменила местоположение на " + newLocation);
    }
}
