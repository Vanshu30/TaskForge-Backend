package com.Task_Forge.Microservice;

import com.Task_Forge.Microservice.Controller.TaskController;
import com.Task_Forge.Microservice.Entity.User;
import com.Task_Forge.Microservice.Repository.UserRepository;
import com.Task_Forge.Microservice.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MicroserviceApplicationTests {

	@Mock
	private TaskService taskService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		taskController = new TaskController();
		// Inject the mock taskService into taskController if it's not using @Autowired
		ReflectionTestUtils.setField(taskController, "taskService", taskService);
		ReflectionTestUtils.setField(taskController, "userRepository", userRepository);
	}

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private TaskController taskController;

	@Test
	void testGetMonthlyCompletedTasks() {
		UUID userId = UUID.randomUUID();
		User mockUser = new User();
		mockUser.setId(userId);

		Map<String, Integer> taskCounts = new HashMap<>();
		taskCounts.put("January", 5);
		taskCounts.put("February", 8);

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
		when(taskService.getMonthlyCompletedTasksForLoggedInUser(userId)).thenReturn(taskCounts);

		Authentication auth = mock(Authentication.class);
		when(auth.getName()).thenReturn("test@example.com");
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(auth);
		SecurityContextHolder.setContext(securityContext);

		ResponseEntity<Map<String, Integer>> response = taskController.getMonthlyCompletedTasksForLoggedInUser();

		System.out.println("Response: " + response);
		System.out.println("Response Body: " + response.getBody());
		System.out.println("Response Status Code: " + response.getStatusCode());


		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().size());
		assertEquals(5, response.getBody().get("January"));
	}


}
