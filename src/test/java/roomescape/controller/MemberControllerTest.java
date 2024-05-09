package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("DB에 저장된 이메일과 비밀번호로 로그인 시도하면 토큰으로 쿠키에 저장할 수 있다.")
    @Test
    void readReservations() {
        Map<String, String> params = Map.of(
                "email", "aaa@naver.com",
                "password", "1111"
        );

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/login")
                .then().log().all().extract();

        String keepAlive = response.header("Keep-Alive");
        String accessToken = response.header("Set-Cookie").split(";")[0];

        assertAll(
                () -> assertThat(keepAlive).isEqualTo("timeout=60"),
                () -> assertThat(accessToken.startsWith("token=")).isTrue()
        );
    }

    @DisplayName("DB에 저장되지 않은 이메일과 비밀번호로 로그인 시도하면 로그인이 안된다.")
    @Test
    void readReservations_whenNotMember() {
        Map<String, String> params = Map.of(
                "email", "abc@email.com",
                "password", "1111"
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/login")
                .then().log().all()
                .statusCode(203);
    }
}
