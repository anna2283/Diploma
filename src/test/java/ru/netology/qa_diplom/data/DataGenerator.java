package ru.netology.qa_diplom.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerator {
    static Faker faker = new Faker();
    private final LocalDate actualData = LocalDate.now();
    private final DateTimeFormatter formatterYears = DateTimeFormatter.ofPattern("yy");
    private final DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MM");
    private final int getValidCVV = faker.random().nextInt(899) + 100;

    public String shiftYear(int shiftYears) {
        LocalDate newDate = actualData.plusYears(shiftYears);
        return formatterYears.format(newDate);
    }

    public String shiftMonth(int shiftMonth) {
        LocalDate newDate = actualData.plusMonths(shiftMonth);
        return formatterMonth.format(newDate);
    }

    public String shiftCvv() {
        return Integer.toString(getValidCVV);
    }

    public String invalidCvv() {
        return Integer.toString(faker.random().nextInt(1, 99));
    }

    public String shiftOwner() {
        return faker.name().fullName();
    }

}
