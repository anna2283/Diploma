package ru.netology.qa_diplom.data;

import lombok.Value;

import static ru.netology.qa_diplom.data.DataGenerator.faker;

public class DataHelper {
    static CardNumber cardNumber = new CardNumber();
    static DataGenerator dataGenerator = new DataGenerator();


    public static CardInformation getValidCard() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getCurrentMonthAndYear() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(0),
                dataGenerator.shiftMonth(0),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getDeclinedCard() {
        return new CardInformation(
                cardNumber.getDeclinedCardNumber(),
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getCardNumberEmpty() {
        return new CardInformation(
                " ",
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getYearEmpty() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                " ",
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getMonthEmpty() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                " ",
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getHolderEmpty() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                " ",
                dataGenerator.shiftCvv());
    }

    public static CardInformation getCVVEmpty() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                "");
    }

    public static CardInformation getExpiredYear() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(-1),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getExpiredMonth() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(0),
                dataGenerator.shiftMonth(-1),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getWrongYear() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(11),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getInvalidMonth() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                Integer.toString(faker.random().nextInt(13, 20)),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getInvalidName() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                "Петро123(*?(?(?",
                dataGenerator.shiftCvv());
    }

    public static CardInformation getInvalidCVV() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                dataGenerator.invalidCvv());
    }

    public static CardInformation getZeroCard() {
        return new CardInformation(
                "0000000000000000",
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getZeroMonth() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                "00",
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getZeroCVV() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                "000");
    }

    public static CardInformation getInvalidFormatCVV() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                dataGenerator.shiftOwner(),
                "77");
    }

    public static CardInformation getInvalidFormatMonth() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                "7",
                dataGenerator.shiftOwner(),
                dataGenerator.shiftCvv());
    }

    public static CardInformation getInvalidNameFormat() {
        return new CardInformation(
                cardNumber.getApprovedCardNumber(),
                dataGenerator.shiftYear(1),
                dataGenerator.shiftMonth(1),
                "G",
                dataGenerator.shiftCvv());
    }

    @Value
    public static class CardInformation {
        String cardNumber;
        String year;
        String month;
        String owner;
        String CVV;
    }

}
