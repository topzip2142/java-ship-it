package ru.yandex.practicum.delivery;

import ru.yandex.practicum.delivery.parcel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();

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
        System.out.println("0 — Завершить");
    }

    // реализуйте методы ниже

    private static void addParcel() {
        System.out.print("Какую посылку хотите отправить? 1 - обычную, 2 - хрупкую, 3 - скоропортящуюся: ");
        int parcelType = Integer.parseInt(scanner.nextLine());

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
                break;

            case 2:
                parcel = new FragileParcel(parcelDescription, parcelWeight, parcelDeliveryAddress, parcelSendDay);
                break;

            case 3:
                System.out.println("Введите срок хранения: ");
                int parcelTimeToLive = Integer.parseInt(scanner.nextLine());

                parcel = new PerishableParcel(
                        parcelDescription, parcelWeight,
                        parcelDeliveryAddress, parcelSendDay,
                        parcelTimeToLive
                );
                break;

            default:
                System.out.println("Такого типа посылок нет");
                return;
        }
        allParcels.add(parcel);
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Посылок нет, нечего отправлять");
            return;
        }

        for (Parcel parcel : allParcels) {
            if (parcel instanceof StandardParcel) {
                parcelBoxStandard.addParcel((StandardParcel) parcel);
            } else if (parcel instanceof FragileParcel) {
                parcelBoxFragile.addParcel((FragileParcel) parcel);
            } else if (parcel instanceof PerishableParcel) {
                parcelBoxPerishable.addParcel((PerishableParcel) parcel);
            }

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

        System.out.println("Общая стоимость всех доставок: " + totalCost + " руб.");
    }

    private static void showParcelBox() {
        System.out.print("Какую коробку показать? 1 - с обычными посылками, 2 - с  хрупкими посылками, 3 - со скоропортящимися");
        int parcelBoxType = Integer.parseInt(scanner.nextLine());


        switch (parcelBoxType) {
            case 1:
                parcelBoxStandard.getAllParcels();
                break;
            case 2:
                parcelBoxFragile.getAllParcels();
                break;
            case 3:
                parcelBoxPerishable.getAllParcels();
                break;
            default:
                System.out.println("Такой коробки не существует");
        }
    }

}

