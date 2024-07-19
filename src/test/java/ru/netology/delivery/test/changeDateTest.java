package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.Generate;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = Generate.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = Generate.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = Generate.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();

        $("[data-test-id='success-notification']").shouldHave(text("Успешно! Встреча успешно запланирована на " + firstMeetingDate));

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(".button").click();

        $("[data-test-id='replan-notification'] .button").click();

        $("[data-test-id='success-notification']").shouldHave(text("Успешно! Встреча успешно запланирована на " + secondMeetingDate));
    }
}