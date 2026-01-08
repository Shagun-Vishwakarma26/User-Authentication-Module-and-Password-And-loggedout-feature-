package com.TaskMange.Security;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenKillService {
	
	private final Set<String> blockListToken= ConcurrentHashMap.newKeySet();
	
	public void blockListToken(String token) {
		blockListToken.add(token);
		
	}
	
	public boolean isBlockListed(String token) {
		return blockListToken.contains(token);
	}

}
