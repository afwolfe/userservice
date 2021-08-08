package io.getarrays.userservice.service;

import io.getarrays.userservice.model.Role;
import io.getarrays.userservice.model.RoleEnum;
import io.getarrays.userservice.model.User;
import java.util.List;

public interface UserService {

  User saveUser(User user);

  Role saveRole(Role role);

  void addRoleToUser(String username, RoleEnum role);

  User getUser(String username);

  List<User> getAllUsers();

}
