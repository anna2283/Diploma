package ru.netology.qa_diplom.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.qa_diplom.data.DataHelper;
import ru.netology.qa_diplom.data.SQLHelper;
import ru.netology.qa_diplom.page.StartPage;

import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }


    @DisplayName("Карта – Удачная покупка.")
    @Test
    public void shouldConfirmPaymentApprovedCard() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var approvedCardInformation = DataHelper.getValidCard();
        payCard.enterCardData(approvedCardInformation);
        payCard.verifySuccessNotificationCard();

        var statusPayment = SQLHelper.getStatusPayment();
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Карта - Успешная покупка с текущей датой..")
    @Test
    public void shouldConfirmPaymentCurrentMonthAndYear() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var validCardInformation = DataHelper.getCurrentMonthAndYear();
        payCard.enterCardData(validCardInformation);
        payCard.verifySuccessNotificationCard();

        var statusPayment = SQLHelper.getStatusPayment();
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Карта – отклоненная карта")
    @Test
    public void shouldNotPayDeclinedCard() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var declinedCard = DataHelper.getDeclinedCard();
        payCard.enterCardData(declinedCard);
        payCard.verifyErrorWarningCard();

        var statusPayment = SQLHelper.getStatusPayment();
        Assertions.assertEquals("DECLINED", statusPayment);
    }

    @DisplayName("Карта - поле номер карты пустое")
    @Test
    public void shouldNotPayEmptyCard() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldCardEmpty = DataHelper.getCardNumberEmpty();
        payCard.enterCardData(fieldCardEmpty);
        payCard.verifyRequiredFieldCard();
    }

    @DisplayName("Карта - поле месяца пусто")
    @Test
    public void shouldNotPayEmptyMonth() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldMonthEmpty = DataHelper.getMonthEmpty();
        payCard.enterCardData(fieldMonthEmpty);
        payCard.verifyRequiredFieldCard();
    }

    @DisplayName("Карта - поле года пустое")
    @Test
    public void shouldNotPayEmptyYear() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldYearEmpty = DataHelper.getYearEmpty();
        payCard.enterCardData(fieldYearEmpty);
        payCard.verifyRequiredFieldCard();
    }

    @DisplayName("Карта — держатель карты пустое поле")
    @Test
    public void shouldNotPayEmptyHolder() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldHolderEmpty = DataHelper.getHolderEmpty();
        payCard.enterCardData(fieldHolderEmpty);
        payCard.verifyRequiredFieldCard();
    }

    @DisplayName("Карта – поле CVV пустое.")
    @Test
    public void shouldNotPayEmptyCvv() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldCvvEmpty = DataHelper.getCVVEmpty();
        payCard.enterCardData(fieldCvvEmpty);
        payCard.verifyRequiredFieldCard();
    }

    @DisplayName("Карта - Год истек")
    @Test
    public void shouldNotPayExpiredYear() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getExpiredYear();
        payCard.enterCardData(invalidCard);
        payCard.expiredCard();
    }

    @DisplayName("Карта - Истек месяц")
    @Test
    public void shouldNotPayExpiredMonth() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getExpiredMonth();
        payCard.enterCardData(invalidCard);
        payCard.expiredCard();
    }

    @DisplayName("Карта – неверный месяц.")
    @Test
    public void shouldNotPayWrongMonth() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getInvalidMonth();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectDateCard();
    }

    @DisplayName("Карта — неверный год.")
    @Test
    public void shouldNotPayWrongYear() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getWrongYear();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectDateCard();
    }

    @DisplayName("Карта – буквенночисловое имя держателя.")
    @Test
    public void shouldNotPayNumericHolder() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getInvalidName();
        payCard.enterCardData(invalidCard);
        payCard.verifyErrorWarningFormatDataCard();
    }

    @DisplayName("Карта – неверный CVV.")
    @Test
    public void shouldNotPayInvalidCVV() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getInvalidCVV();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Карта – нулевой номер карты")
    @Test
    public void shouldNotPayZeroNumber() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getZeroCard();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Карта - Нулевой месяц")
    @Test
    public void shouldNotPayZeroMonth() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getZeroMonth();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectDateCard();
    }

    @DisplayName("Карта – нулевой CVV.")
    @Test
    public void shouldNotPayZeroCVV() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getZeroCVV();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Карта – короткий CVV.")
    @Test
    public void shouldNotPayIncorrectFormatCVC() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getInvalidFormatCVV();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Карта – Месяц одной цифрой.")
    @Test
    public void shouldNotPayIncorrectFormatMonth() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getInvalidFormatMonth();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Карта – Короткое или длинное имя держателя.")
    @Test
    public void shouldNotCreditMinMaxNameHolder() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCard();
        var invalidCard = DataHelper.getInvalidNameFormat();
        buyCredit.enterCardData(invalidCard);
        buyCredit.verifyFieldDataOwnerCard();
    }

}
