package ru.yandex.practicum;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.practicum.delivery.parcels.*;
import ru.yandex.practicum.delivery.tools.ParcelBox;

public class DeliveryCostTest {
    public static ParcelBox<StandardParcel> parcelBoxStandard = new ParcelBox<>(20);
    public static ParcelBox<FragileParcel> parcelBoxFragile = new ParcelBox<>(15);
    public static ParcelBox<PerishableParcel> parcelBoxPerishable = new ParcelBox<>(25);
    private static final String ERROR_PARCEL_BOX_OVERWEIGHT = "Посылка неверно добавлена в коробку, "
                                                            + "у посылки превышен вес для коробки";

    private static StandardParcel standardParcel;
    private static FragileParcel fragileParcel;
    private static PerishableParcel perishableParcel;

    @BeforeAll
    static void beforeAll() {
        standardParcel = new StandardParcel("Стандартная посылка", 3,
                "Москва. ул. Моросейка д.5", 10);
        fragileParcel = new FragileParcel("Хрупкая посылка", 15,
                "Санкт-Петербург, Невский проспект д.16", 1);
        perishableParcel = new PerishableParcel("Скоропортящаяся посылка", 5,
                "Челябинск, ул. Ленина д.101", 25, 5);
    }

    @Test
    public void shouldReturnCorrectCostOfStandardParcel() {
        assertEquals(6, standardParcel.calculateDeliveryCost(),
                "Неверная сумма расчёта доставки у стандартной посылки"
        );
    }

    @Test
    public void shouldReturnCorrectCostOfFragileParcel() {
        assertEquals(60, fragileParcel.calculateDeliveryCost(),
                "Неверная сумма расчёта доставки у хрупкой посылки"
        );
    }

    @Test
    public void shouldReturnCorrectCostOfPerishableParcel() {
        assertEquals(15, perishableParcel.calculateDeliveryCost(),
                "Неверная сумма расчёта доставки у скоропортящейся посылки"
        );
    }

    @Test
    public void shouldBeFalseWhenPerishableParcelAndSendDayAndTimeToLiveEqualsCurrentDay() {
        assertFalse(perishableParcel.isExpired(30));
    }

    @Test
    public void shouldBeTrueWhenPerishableParcelAndSendDayAndTimeToLiveMoreThenCurrentDay() {
        PerishableParcel anotherPerishableParcel = new PerishableParcel(
                "Верблюжье молоко из Египта", 5,
                "Стамбул", 4, 3
        );

        assertTrue(anotherPerishableParcel.isExpired(6));
    }

    @Test
    public void shouldBeFalseWhenPerishableParcelAndSendDayAndTimeToLiveLessThenCurrentDay() {
        PerishableParcel anotherPerishableParcel = new PerishableParcel(
                "Йогурты - 5 шт.", 2,
                "Новый Уренгой", 16, 10
        );

        assertFalse(anotherPerishableParcel.isExpired(27));
    }

    @Test
    public void shouldPutParcelInBoxWhenBoxIsNotOverWeight() {
        parcelBoxPerishable.addParcel(perishableParcel);
        assertEquals(1, parcelBoxPerishable.getParcelsCount());

        PerishableParcel anotherPerishableParcel = new PerishableParcel(
                "Королевские креветки", 5,
                "Новосибирск", 1, 7
        );
        parcelBoxPerishable.addParcel(anotherPerishableParcel);

        assertEquals(2, parcelBoxPerishable.getParcelsCount());
    }

    @Test
    public void shouldPutParcelInBoxWhenBoxWeightBecomeMaxWeight() {
        parcelBoxStandard.addParcel(standardParcel);
        assertEquals(1, parcelBoxStandard.getParcelsCount());

        StandardParcel anotherStandardParcel = new StandardParcel(
                "Запчасти к хонде", 17,
                "Калуга", 2
        );

        parcelBoxStandard.addParcel(anotherStandardParcel);

        assertEquals(2, parcelBoxStandard.getParcelsCount());
    }

    @Test
    public void shouldNotPutParcelInBoxWhenBoxIsOverweight() {
        parcelBoxFragile.addParcel(fragileParcel);
        assertEquals(1, parcelBoxFragile.getParcelsCount());

        FragileParcel anotherFragileParcel = new FragileParcel(
                "Древние вазы династии Цинь", 20,
                "Китай, Пекин", 5
        );

        parcelBoxFragile.addParcel(anotherFragileParcel);
        assertEquals(1, parcelBoxFragile.getParcelsCount(), ERROR_PARCEL_BOX_OVERWEIGHT);
    }

    @Test
    public void shouldBeEmptyParcelBoxWhenOneParcelWithOverweightPutInBox() {
        ParcelBox<StandardParcel> anotherParcelBoxStandard = new ParcelBox<>(2);
        anotherParcelBoxStandard.addParcel(standardParcel);

        assertEquals(0, anotherParcelBoxStandard.getParcelsCount(), ERROR_PARCEL_BOX_OVERWEIGHT);
    }
}