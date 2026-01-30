package ru.yandex.practicum.delivery;

import ru.yandex.practicum.delivery.parcels.*;
import ru.yandex.practicum.delivery.tools.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableParcels = new ArrayList<>();

    private static ParcelBox<StandardParcel> parcelBoxStandard = new ParcelBox<>(20);
    private static ParcelBox<FragileParcel> parcelBoxFragile = new ParcelBox<>(10);
    private static ParcelBox<PerishableParcel> parcelBoxPerishable = new ParcelBox<>(15);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    showParcelBox();
                    break;
                case 5:
                    changeTrackingStatus();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 - Показать содержимое коробки");
        System.out.println("5 - Поменять статус отслеживаемой посылки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.print("Введите тип (1 - обычная, 2 - хрупкая, 3 - скоропортящаяся): ");
        int parcelType = Integer.parseInt(scanner.nextLine());
        if (parcelType < 1 || parcelType > 3) {
            System.out.println("Такого типа посылок нет");
            return;
        }

        System.out.print("Введите описание: ");
        String parcelDescription = scanner.nextLine();

        System.out.print("Введите вес: ");
        int parcelWeight = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите адрес отправки: ");
        String parcelDeliveryAddress = scanner.nextLine();

        System.out.print("Введите день отправки: ");
        int parcelSendDay = Integer.parseInt(scanner.nextLine());

        Parcel parcel;
        switch (parcelType) {
            case 1:
                parcel = new StandardParcel(parcelDescription, parcelWeight, parcelDeliveryAddress, parcelSendDay);
                parcelBoxStandard.addParcel((StandardParcel) parcel);
                break;

            case 2:
                parcel = new FragileParcel(parcelDescription, parcelWeight, parcelDeliveryAddress, parcelSendDay);
                parcelBoxFragile.addParcel((FragileParcel) parcel);
                break;

            case 3:
                System.out.println("Введите срок хранения: ");
                int parcelTimeToLive = Integer.parseInt(scanner.nextLine());

                parcel = new PerishableParcel(
                        parcelDescription, parcelWeight,
                        parcelDeliveryAddress, parcelSendDay,
                        parcelTimeToLive
                );
                parcelBoxPerishable.addParcel((PerishableParcel) parcel);
                break;

            default:
                System.out.println("Такого типа посылок нет");
                return;
        }
        allParcels.add(parcel);

        if (parcel instanceof Trackable) {
            trackableParcels.add((Trackable) parcel);
        }
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Посылок нет, нечего отправлять");
            return;
        }

        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        int totalCost = 0;
        if (!allParcels.isEmpty()) {
            for (Parcel parcel : allParcels) {
                totalCost += parcel.calculateDeliveryCost();
            }
        }

        System.out.println("Общая стоимость доставки: " + totalCost + " руб.");
    }

    private static void showParcelBox() {
        System.out.print("Введите тип коробки (1 - с обычными посылками, "
                + "2 - с  хрупкими посылками, 3 - со скоропортящимися): ");
        int parcelBoxType = Integer.parseInt(scanner.nextLine());

        ParcelBox<? extends Parcel> parcelBox;
        switch (parcelBoxType) {
            case 1:
                parcelBox = parcelBoxStandard;
                break;
            case 2:
                parcelBox = parcelBoxFragile;
                break;
            case 3:
                parcelBox = parcelBoxPerishable;
                break;
            default:
                System.out.println("Такой коробки не существует");
                return;
        }

        parcelBox.getAllParcels();
    }

    private static void showTrackableParcels() {
        System.out.println("Список отслеживаемых посылок:");
        for (int i = 0; i < trackableParcels.size(); i++) {
            Parcel trackableParcel = (Parcel) trackableParcels.get(i);
            System.out.println("\t" + (i + 1) + ". " + trackableParcel.getDescription() + " " + trackableParcel.getDeliveryAddress());
        }

    }

    private static void changeTrackingStatus() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет отслеживаемых посылок");
            return;
        }

        System.out.print("Введите номер отслеживаемой посылки:");
        showTrackableParcels();

        int parcelIndex = Integer.parseInt(scanner.nextLine());
        if (parcelIndex > trackableParcels.size() || parcelIndex < 1) {
            System.out.println("Введён неверный номер посылки");
            return;
        }

        for (int i = 0; i < trackableParcels.size(); i++) {
            if (parcelIndex - 1 == i) {
                Trackable trackableParcel = trackableParcels.get(i);

                System.out.print("Введите новый статус для посылки: ");
                String newLocation = scanner.nextLine();

                trackableParcel.reportStatus(newLocation);
                break;
            }
        }
    }
}