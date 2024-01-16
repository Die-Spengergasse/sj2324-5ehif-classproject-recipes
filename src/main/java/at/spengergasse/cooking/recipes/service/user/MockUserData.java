package at.spengergasse.cooking.recipes.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockUserData {

    private static final int WIREMOCK_PORT = 8089;

    public static List<UserDto> startMockUser() {
        WireMockServer wireMockServer = new WireMockServer(WIREMOCK_PORT);
        wireMockServer.start();
        List<UserDto> userList = new ArrayList<>();
        UserDto mockUser = new UserDto(
                "ABCDE",
                "Max123",
                "Mayer",
                "Max",
                "123@gmail.com",
                "mockPassword",
                new ArrayList<>()
        );
        userList.add(mockUser);
        String jsonBody = convertListToJson(userList);

        WireMock.configureFor("localhost", WIREMOCK_PORT);

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/users"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonBody)));
        return userList;
    }

    private static String convertListToJson(List<UserDto> userList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(userList);
        } catch (IOException e) {
            throw new RuntimeException("Error converting list to JSON", e);
        }
    }
}
