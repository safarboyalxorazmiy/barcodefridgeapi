package uz.smilechat;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import uz.premier.auth.AuthenticationRequest;
import uz.premier.auth.AuthenticationResponse;
import uz.premier.auth.AuthenticationService;

@SpringBootConfiguration
@SpringBootTest
@RequiredArgsConstructor
class SecurityApplicationTests {
class SecurityApplicationTests {
	private final AuthenticationService service;


	@Test
	void contextLoads() {}

	@Test
	void test () {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setUsername("anon");
		authenticationRequest.setPassword("1234");
		AuthenticationResponse authenticate = service.authenticate(authenticationRequest);

		System.out.println(authenticate.getAccessToken());
	}

}
