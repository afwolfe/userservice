package io.getarrays.userservice.service;

import io.getarrays.userservice.model.Role;
import io.getarrays.userservice.model.RoleEnum;
import io.getarrays.userservice.model.User;
import io.getarrays.userservice.repo.RoleRepo;
import io.getarrays.userservice.repo.UserRepo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javassist.Loader.Simple;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepo userRepo;
  private final RoleRepo roleRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username);
    if (user == null) {
      log.error("User not found in database");
      throw new UsernameNotFoundException("User not found in the database");
    }
    else {
      log.info("User found in database: {}", username);
    }

    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (Role role : user.getRoles()) {
      String roleStr = role.getRoleEnum().toString();
      authorities.add(new SimpleGrantedAuthority(roleStr));
    }

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
  }

  @Override
  public User saveUser(User user) {
    log.info("Saving new user {} to the database", user.getName());
    return userRepo.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    log.info("Saving new role {} to the database", role.getRoleEnum());
    return roleRepo.save(role);

  }

  @Override
  public void addRoleToUser(String username, RoleEnum roleEnum) {
    log.info("Adding role {} to user {}", roleEnum, username);
    User user = userRepo.findByUsername(username);
    Role role = roleRepo.findByRoleEnum(roleEnum);
    user.getRoles().add(role);
  }

  @Override
  public User getUser(String username) {
    log.info("Fetching user {}", username);
    return userRepo.findByUsername(username);
  }

  @Override
  public List<User> getAllUsers() {
    log.info("Fetching all users");
    return userRepo.findAll();
  }
}
