package com.Service1.Registration.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClientStatusService {

	private static final String REDIS_KEY="client_status";
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	public String getClientStatus(String clientId) {
		return (String) redisTemplate.opsForHash().get(REDIS_KEY, clientId);
		
	}

}
