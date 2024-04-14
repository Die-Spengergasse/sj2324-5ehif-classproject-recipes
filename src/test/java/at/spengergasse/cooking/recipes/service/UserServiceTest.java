package at.spengergasse.cooking.recipes.service;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wiremock.integrations.testcontainers.WireMockContainer;
import org.testcontainers.junit.jupiter.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@Testcontainers
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Container
    public WireMockContainer wireMockServer = new WireMockContainer("wiremock/wiremock:latest")
            .withExposedPorts(8080);

    @BeforeEach
    void setup() throws JsonProcessingException {
        wireMockServer.start();
        List<UserDto> userList = new ArrayList<>();
        userList.add(new UserDto("USREh4PpDmtxvC", "Max123", "Mayer", "Max", "123@gmail.com", "mockPassword", new ArrayList<>()));
        userList.add(new UserDto("USRTp90VKuD6gP", "Adrian123", "Mayer", "Adrian", "adrian123@gmail.com", "mockPassword", new ArrayList<>()));

        WireMock.configureFor("localhost", wireMockServer.getMappedPort(8080));

        WireMock.stubFor(WireMock.get(urlEqualTo("/api/users"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(OBJECT_MAPPER.writeValueAsString(userList))));

        for (UserDto user : userList) {
            String key = user.key();
            WireMock.stubFor(WireMock.get(urlEqualTo("/api/users/" + key))
                    .willReturn(WireMock.aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(OBJECT_MAPPER.writeValueAsString(user))));
        }
    }

    @AfterEach
    void shutdown(){
        wireMockServer.close();
    }


    @Test
    void testGetUser() {
        // todo: openfeign + wiremock testing; https://stackoverflow.com/questions/68453529/how-to-autowire-feignclient-into-test-class
        // todo: cloud contract: https://wiremock.org/docs/solutions/spring-boot/
    }
}