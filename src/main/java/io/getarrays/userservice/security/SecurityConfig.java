package io.getarrays.userservice.security;

import static io.getarrays.userservice.model.RoleEnum.ROLE_ADMIN;
import static io.getarrays.userservice.model.RoleEnum.ROLE_USER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import io.getarrays.userservice.filter.CustomAuthenticaionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticaionFilter customAuthenticaionFilter = new CustomAuthenticaionFilter(authenticationManagerBean());
    customAuthenticaionFilter.setFilterProcessesUrl("/api/login");
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests().antMatchers("/api/login/**").permitAll();
    http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority(ROLE_USER.toString());
    http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority(ROLE_ADMIN.toString());
    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(customAuthenticaionFilter);
  }
}
