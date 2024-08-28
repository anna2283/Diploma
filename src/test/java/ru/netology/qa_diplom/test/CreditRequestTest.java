package ru.netology.qa_diplom.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.qa_diplom.data.DataHelper;
import ru.netology.qa_diplom.data.SQLHelper;
import ru.netology.qa_diplom.page.StartPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditRequestTest {
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

    @DisplayName("Кредит – Успешная покупка.")
    @Test
    public void shouldConfirmCreditApprovedCard() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var approvedCardInformation = DataHelper.getValidCard();
        buyCredit.enterCreditCardData(approvedCardInformation);
        buyCredit.verifySuccessNotificationCreditCard();

        var statusPayment = SQLHelper.getStatusCredit();
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Кредит - Успешная покупка с текущей датой.")
    @Test
    public void shouldConfirmCreditWithCurrentMonthAndYear() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var validCardInformation = DataHelper.getCurrentMonthAndYear();
        buyCredit.enterCreditCardData(validCardInformation);
        buyCredit.verifySuccessNotificationCreditCard();

        var statusPayment = SQLHelper.getStatusCredit();
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Кредит – отклоненная карта")
    @Test
    public void shouldNotCreditDeclinedCard() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var declinedCard = DataHelper.getDeclinedCard();
        buyCredit.enterCreditCardData(declinedCard);
        buyCredit.verifyErrorWarningCreditCard();

        var statusPayment = SQLHelper.getStatusCredit();
        Assertions.assertEquals("DECLINED", statusPayment);
    }

    @DisplayName("Кредит - поле номер карты пустое")
    @Test
    public void shouldNotCreditEmptyCard() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldCardEmpty = DataHelper.getCardNumberEmpty();
        buyCredit.enterCreditCardData(fieldCardEmpty);
        buyCredit.verifyRequiredFieldCreditCard();
    }

    @DisplayName("Кредит – поле месяца пусто")
    @Test
    public void shouldNotCreditEmptyMonth() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldMonthEmpty = DataHelper.getMonthEmpty();
        buyCredit.enterCreditCardData(fieldMonthEmpty);
        buyCredit.verifyRequiredFieldCreditCard();
    }

    @DisplayName("Кредит — поле года пусто.")
    @Test
    public void shouldNotCreditEmptyYear() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldYearEmpty = DataHelper.getYearEmpty();
        buyCredit.enterCreditCardData(fieldYearEmpty);
        buyCredit.verifyRequiredFieldCreditCard();
    }

    @DisplayName("Кредит – держатель поля пуст")
    @Test
    public void shouldNotCreditEmptyHolder() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldHolderEmpty = DataHelper.getHolderEmpty();
        buyCredit.enterCreditCardData(fieldHolderEmpty);
        buyCredit.verifyRequiredFieldCreditCard();
    }

    @DisplayName("Кредит - поле CVV пусто")
    @Test
    public void shouldNotCreditEmptyCvv() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldCvvEmpty = DataHelper.getCVVEmpty();
        buyCredit.enterCreditCardData(fieldCvvEmpty);
        buyCredit.verifyRequiredFieldCreditCard();
    }

    @DisplayName("Кредит - Год истек")
    @Test
    public void shouldNotCreditExpiredYear() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getExpiredYear();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.expiredCreditCard();
    }

    @DisplayName("Кредит - Истек месяц")
    @Test
    public void shouldNotCreditExpiredMonth() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getExpiredMonth();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.expiredCreditCard();
    }

    @DisplayName("Кредит – неверный месяц.")
    @Test
    public void shouldNotCreditWrongMonth() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getInvalidMonth();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectDateCreditCard();
    }

    @DisplayName("Кредит – неверный год.")
    @Test
    public void shouldNotCreditWrongYear() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getWrongYear();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectDateCreditCard();
    }

    @DisplayName("Кредит – буквенночисловое имя держателя.")
    @Test
    public void shouldNotCreditNumericHolder() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getInvalidName();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyErrorWarningFormatDataCreditCard();
    }

    @DisplayName("Кредит — неверный CVV.")
    @Test
    public void shouldNotCreditInvalidCVV() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getInvalidCVV();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Кредит – нулевой номер карты.")
    @Test
    public void shouldNotCreditZeroNumber() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getZeroCard();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Кредит-нулевой месяц")
    @Test
    public void shouldNotCreditZeroMonth() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getZeroMonth();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectDateCreditCard();
    }

    @DisplayName("Кредит – нулевой CVV")
    @Test
    public void shouldNotCreditZeroCVV() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getZeroCVV();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Кредит – короткий CVV.")
    @Test
    public void shouldNotPayIncorrectFormatCVC() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getInvalidFormatCVV();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Кредит – Месяц одной цифрой.")
    @Test
    public void shouldNotPayIncorrectFormatMonth() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getInvalidFormatMonth();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Кредит – Короткое или длинное имя держателя.")
    @Test
    public void shouldNotCreditMinMaxNameHolder() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getInvalidNameFormat();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyFieldDataOwnerCreditCard();
    }

}
