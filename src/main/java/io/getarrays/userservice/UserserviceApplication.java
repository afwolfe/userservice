package io.getarrays.userservice;

import io.getarrays.userservice.model.Role;
import io.getarrays.userservice.model.RoleEnum;
import io.getarrays.userservice.model.User;
import io.getarrays.userservice.service.UserService;
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, RoleEnum.ROLE_USER));
			userService.saveRole(new Role(null, RoleEnum.ROLE_MANAGER));
			userService.saveRole(new Role(null, RoleEnum.ROLE_ADMIN));
			userService.saveRole(new Role(null, RoleEnum.ROLE_SUPER_ADMIN));

			userService.saveUser(new User(null, "John Travolta", "john", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Will Smith", "will", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Jim Carrey", "jim", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Arnold Schwarzenegger", "arnold", "1234", new ArrayList<>()));

			userService.addRoleToUser("john", RoleEnum.ROLE_USER);
			userService.addRoleToUser("will", RoleEnum.ROLE_MANAGER);
			userService.addRoleToUser("jim", RoleEnum.ROLE_ADMIN);
			userService.addRoleToUser("arnold", RoleEnum.ROLE_SUPER_ADMIN);
			userService.addRoleToUser("arnold", RoleEnum.ROLE_MANAGER);
			userService.addRoleToUser("arnold", RoleEnum.ROLE_USER);


		};
	}
}
