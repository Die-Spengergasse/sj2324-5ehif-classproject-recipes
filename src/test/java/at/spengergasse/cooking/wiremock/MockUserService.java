package at.spengergasse.cooking.wiremock;

import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockUserService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int WIREMOCK_PORT = 8089;
    public static List<UserDto> startMockUser() throws JsonProcessingException {
        WireMockServer wireMockServer = new WireMockServer(WIREMOCK_PORT);
        wireMockServer.start();
        List<UserDto> userList = new ArrayList<>();

        UserDto mockUser = new UserDto(
                "USREh4PpDmtxvC",
                "Max123",
                "Mayer",
                "Max",
                "123@gmail.com",
                "mockPassword",
                new ArrayList<>()
        );
        UserDto mockUser1 = new UserDto(
                "USRTp90VKuD6gP",
                "Adrian123",
                "Mayer",
                "Adrian",
                "adrian123@gmail.com",
                "mockPassword",
                new ArrayList<>()
        );
        userList.add(mockUser);
        userList.add(mockUser1);


        WireMock.configureFor("localhost", WIREMOCK_PORT);

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/users"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(MockUserService.OBJECT_MAPPER.writeValueAsString(userList))));

        for (UserDto user : userList) {
            String key = user.key();
            WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/users/" + key))
                    .willReturn(WireMock.aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(MockUserService.OBJECT_MAPPER.writeValueAsString(user))));
        }

        return userList;
    }

    public static void main(String[] args) throws JsonProcessingException {
        List<UserDto> users = startMockUser();
    }
}
