package ru.yandex.practicum.delivery.tools;

import ru.yandex.practicum.delivery.parcels.Parcel;

import java.util.ArrayList;

public class ParcelBox<T extends Parcel> {
    private final int maxWeight;
    private int weight;
    private final ArrayList<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
        weight = 0;
    }

    public void addParcel(T parcel) {
        if (weight >= maxWeight) {
            System.out.println("Коробка заполнена, "
                    + "невозможно переместить в неё посылку <<" + parcel.getDescription() + ">>");
            return;
        }

        parcels.add(parcel);
        weight += parcel.getWeight();
    }

    public int getParcelsCount() {
        return parcels.size();
    }

    public void getAllParcels() {
        if (parcels.isEmpty()) {
            System.out.println("Данная коробка пуста");
            return;
        }

        System.out.println("В данной коробке лежат посылки (" + getParcelsCount() + "):");
        for (T parcel : parcels) {
            System.out.println("\t" + parcel.getDescription());
        }
    }
}