package tests;

import models.GetUserDataModel;
import models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.TestSpec.requestSpec;
import static specs.TestSpec.responseSpec;

@Tag("api")
public class ApiTest extends TestBase {

    @DisplayName("Проверка получения данных пользователя")
    @Test
    void getUserDataTest() {
        int userId = 2;
        String userName = "fuchsia rose";
        int userYear = 2001;
        String userColor = "#C74375";
        String userPantoneValue = "17-2031";
        String supportUrl = "https://reqres.in/#support-heading";
        String supportText = "To keep ReqRes free, contributions towards server costs are appreciated!";

        GetUserDataModel response = step("Отправляем запрос на получение данных пользователя", () -> {
            return given(requestSpec)
                    .when()
                    .get("/user/2")
                    .then()
                    .spec(responseSpec)
                    .statusCode(200)
                    .extract().as(GetUserDataModel.class);
        });

        step("Проверяем ответ на запрос", () -> {
            assertThat(response.getData().getId()).isEqualTo(userId);
            assertThat(response.getData().getName()).isEqualTo(userName);
            assertThat(response.getData().getYear()).isEqualTo(userYear);
            assertThat(response.getData().getColor()).isEqualTo(userColor);
            assertThat(response.getData().getPantoneValue()).isEqualTo(userPantoneValue);
            assertThat(response.getSupport().getUrl()).isEqualTo(supportUrl);
            assertThat(response.getSupport().getText()).isEqualTo(supportText);
        });
    }

    @DisplayName("Проверка отсутствия данных пользователя")
    @Test
    void getMissingUserDataTest() {
        GetUserDataModel response = step("Отправляем запрос на получение данных отсутствующего пользователя", () -> {
            return given(requestSpec)
                    .when()
                    .get("/user/50")
                    .then()
                    .spec(responseSpec)
                    .statusCode(404)
                    .extract().as(GetUserDataModel.class);
        });
        step("Проверяем, что ответ на запрос пустой", () -> {
            assertThat(response.getData()).isNull();
            assertThat(response.getSupport()).isNull();
        });
    }

    @DisplayName("Проверка создания пользователя")
    @Test
    void createUserTest() {
        String userName = "Mary";
        String userJob = "secretary";

        UserModel model = new UserModel();
        model.setName(userName);
        model.setJob(userJob);
        UserModel response = step("Отправляем запрос на создание пользователя", () -> {
            return given(requestSpec)
                    .body(model)
                    .when()
                    .post("/users")
                    .then()
                    .spec(responseSpec)
                    .statusCode(201)
                    .extract().as(UserModel.class);
        });
        step("Проверяем ответ на запрос", () -> {
            assertThat(response.getName()).isEqualTo(userName);
            assertThat(response.getJob()).isEqualTo(userJob);
            assertThat(response.getId()).isNotNull();
            assertThat(response.getCreatedAt()).isNotNull();
        });
    }

    @DisplayName("Проверка редактирования данных пользователя")
    @Test
    void updateUserDataTest() {
        String userName = "Mary Jay";
        String userJob = "leader";

        UserModel model = new UserModel();
        model.setName(userName);
        model.setJob(userJob);
        UserModel response = step("Отправляем запрос на редактирование пользователя", () -> {
            return given(requestSpec)
                    .body(model)
                    .when()
                    .put("/users/640")
                    .then()
                    .spec(responseSpec)
                    .statusCode(200)
                    .extract().as(UserModel.class);
        });
        step("Проверяем ответ на запрос", () -> {
            assertThat(response.getName()).isEqualTo(userName);
            assertThat(response.getJob()).isEqualTo(userJob);
            assertThat(response.getUpdatedAt()).isNotNull();
        });
    }

    @DisplayName("Проверка удаления пользователя")
    @Test
    void deleteUserTest() {
        step("Отправляем запрос на удаление пользователя", () -> {
            given(requestSpec)
                    .when()
                    .delete("/users/640")
                    .then()
                    .spec(responseSpec)
                    .statusCode(204);
        });
    }
}
