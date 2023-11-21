package com.pingpong.jlab.pingpong.global.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private UserRepository userRepository;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Optional<User> admin = userRepository.findById(authentication.getName());
		User user = admin.get();

		logger.info("login successed ! ! !");
		}
	}
