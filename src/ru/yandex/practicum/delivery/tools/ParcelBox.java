package ru.yandex.practicum.delivery.tools;

import ru.yandex.practicum.delivery.parcels.Parcel;

import java.util.ArrayList;

public class ParcelBox<T extends Parcel> {
    private final int maxWeight;
    private final ArrayList<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addParcel(T parcel) {
        if (parcel.getWeight() > maxWeight) {
            System.out.println("Превышен максимальный вес посылки для коробки, "
                    + "невозможно поместить в неё посылку <<" + parcel.getDescription() + ">>");
            return;
        }

        parcels.add(parcel);
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