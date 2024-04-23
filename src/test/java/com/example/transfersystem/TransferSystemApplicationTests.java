package com.example.transfersystem;

import com.example.transfersystem.entity.Users;
import com.example.transfersystem.payload.GetProfileResponse;
import com.example.transfersystem.repository.UserRepository;
import com.example.transfersystem.service.UserService;
import com.example.transfersystem.service.impl.UserServiceImpl;
import com.example.transfersystem.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

@SpringBootTest
class TransferSystemApplicationTests {

	private UserRepository userRepository;
	private UserService userService;


	@Test
	void contextLoads() {
	}

}

class UserServiceTest {

	@Mock
	private SecurityUtils securityUtils;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService = new UserServiceImpl(securityUtils, userRepository);

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetProfile_Success() {
		String sampleEmail = "test@example.com";
		when(securityUtils.getJwtToken()).thenReturn(sampleEmail);

		Users sampleUser = new Users();
		sampleUser.setEmail(sampleEmail);
		sampleUser.setName("Test User");
		sampleUser.setUsername("testuser");

		when(userRepository.findByEmail(sampleEmail)).thenReturn(Optional.of(sampleUser));

		GetProfileResponse profileResponse = userService.getProfile();

		assertNotNull(profileResponse);

		assertEquals(sampleUser.getName(), profileResponse.getName());
		assertEquals(sampleUser.getUsername(), profileResponse.getUsername());
		assertEquals(sampleUser.getEmail(), profileResponse.getEmail());

		verify(securityUtils, times(1)).getJwtToken();

		verify(userRepository, times(1)).findByEmail(sampleEmail);
	}

	@Test
	void testGetProfile_UserNotFound() {
		String sampleEmail = "test@example.com";
		when(securityUtils.getJwtToken()).thenReturn(sampleEmail);

		when(userRepository.findByEmail(sampleEmail)).thenReturn(Optional.empty());

		GetProfileResponse profileResponse = userService.getProfile();

		assertNotNull(profileResponse);

		verify(securityUtils, times(1)).getJwtToken();

		verify(userRepository, times(1)).findByEmail(sampleEmail);
	}
}