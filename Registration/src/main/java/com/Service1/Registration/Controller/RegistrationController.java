package com.Service1.Registration.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Service1.Registration.RateLimiter.RateLimiterService;
import com.Service1.Registration.Security.JwtUtil;
import com.Service1.Registration.Service.ClientStatusService;
import com.Service1.Registration.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/users")
public class RegistrationController {
    
	@Autowired
	ClientStatusService clientStatusService;
	
	@Autowired
	RateLimiterService rateLimiterService;
	
	@Autowired
	JwtUtil jwtService;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	ObjectMapper objectMapper;

	private final String TOPIC = "registration";

    
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestHeader("client_Id") String clientId,
			@RequestBody User user) {
		
		//rate limit 
		if (!rateLimiterService.isAllowed(clientId)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
        }
         
		// getting status here
		String status=clientStatusService.getClientStatus(clientId);
		
		
		//if inactive or null iam sending appropriate message 
		if(status==null || status.equals("inactive")) {
			return ResponseEntity.status(403).body("Client not allowed to register");
		}
		
		//setting the id here
		user.setId(clientId);
		
		//sending to topic 
		  try {
	            String userJson = objectMapper.writeValueAsString(user);
	            kafkaTemplate.send(TOPIC, userJson);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Failed to send to Kafka: " + e.getMessage());
	        }
		  
		  //getting JWT token here for other api authentication if we process 
		String token = jwtService.generateToken(user.getName());

		   // returning token along with success message
		return ResponseEntity.ok(
		    Map.of(
		        "message", "Registration completed for client_Id " + clientId,
		        "token", token
		    )
		);
		
	}
	
	
	@GetMapping("/test")
	public ResponseEntity<String> test(@RequestHeader("Authorization") String authHeader,
	                                   @RequestHeader("client_Id") String clientId) {
	    if (!rateLimiterService.isAllowed(clientId)) {
	        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
	    }

	    
	    String token = authHeader.substring(7);

	   
	    if (!jwtService.isTokenValid(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	    }

	    return ResponseEntity.ok("JWT is valid and you're permitted for now");
	}

	
	
	
}
