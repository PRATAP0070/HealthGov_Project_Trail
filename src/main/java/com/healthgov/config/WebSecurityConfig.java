package com.healthgov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtValidationFilter jwtValidationFilter;

	public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			JwtValidationFilter jwtValidationFilter) {
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtValidationFilter = jwtValidationFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("inside security");
		http.csrf(csrf -> csrf.disable());
		http.cors(cors -> {
		}); // ✅ enable CORS

		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/healthGov/register","/healthGov/login").permitAll()
				.requestMatchers("/healthGov/forgotPassword").permitAll()
				.requestMatchers("/audit_log/getLogById/**").hasAuthority("ADMIN")
				.requestMatchers("/infrastructures/**","/resources/**").hasAuthority("MANAGER")
				.requestMatchers("/api/enrollments/**").hasAuthority("CITIZEN")
				.requestMatchers("/api/programs/**").hasAuthority("MANAGER")
				.requestMatchers("/customer/**").hasAuthority("CITIZEN")
				.requestMatchers("/health-profiles").hasAuthority("CITIZEN")
				.requestMatchers("/documents/**").hasAuthority("CITIZEN")
				.requestMatchers("/audits/**").hasAuthority("AUDITOR")
				.requestMatchers("/compliance-records/**").hasAuthority("COMPLIANCE")
				.requestMatchers("/research/**").hasAuthority("RESEARCHER")
				.requestMatchers("/manager/**").hasAuthority("MANAGER")
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // PREVENT 401
				.anyRequest().authenticated());

		http.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint));

		http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// http.addFilterBefore(jwtRequestFilter,
		// UsernamePasswordAuthenticationFilter.class);
		//http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		//http.addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);

		http.addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
