package at.spengergasse.cooking.recipes.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wiremock.integrations.testcontainers.WireMockContainer;
import org.testcontainers.junit.jupiter.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Testcontainers
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Container
    WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock")
            .withMapping("/api/users", UserServiceTest.class, "user.json");


    @Test
    void helloWorld() throws Exception {
        String url = wiremockServer.getUrl("/api/users");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    @Test
    void testGetUser() {
        // todo: openfeign + wiremock testing; https://stackoverflow.com/questions/68453529/how-to-autowire-feignclient-into-test-class
        // todo: cloud contract: https://wiremock.org/docs/solutions/spring-boot/
    }
}