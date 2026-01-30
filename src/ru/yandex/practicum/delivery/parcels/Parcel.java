package ru.yandex.practicum.delivery.parcels;

public abstract class Parcel {
    protected final String description;
    protected final int weight;
    protected final String deliveryAddress;
    protected final int sendDay;
    public static final int BASE_COST_STANDARD = 2;
    public static final int BASE_COST_PERISHABLE = 3;
    public static final int BASE_COST_FRAGILE = 4;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    public void packageItem() {
        System.out.println("Посылка <<" + description + ">> упакована");
    }

    public void deliver() {
        System.out.println("Посылка <<" + description + ">> доставлена по адресу " + deliveryAddress);
    }

    public int calculateDeliveryCost() {
        return weight * getBaseCost();
    }

    public abstract int getBaseCost();

    public int getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}