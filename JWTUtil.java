package com.TaskMange.Security;

import java.security.Key;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
	
	private final key key;
	private final long expireToken = 1000L*60*60*24;
	
	public JWTUtil() {
		String secret = System.getenv("JWT_SECRET");
		if(secret ==null || secret.isEmpty()) {
			secret = "Replace This with Very Secret Key";
			
		}
		key =key.hmacShaKeyFor(secret.getBytes());
	}
	
	public String generateToken(UserAuth user) {
		Set<Permission> permission = RolePermissionConfig.getRoleBasedPermissions().get(user.getRole());
		
		return Jwts.builder()
				.setSubject(user.getUserOffialEmail())
				.claim("role", user.getRole().name())
				.claim("permission", permission.stream().map(Enum::name).collect(Collectors.toList()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+expireToken))
				.signWith(SignatureAlgorithm.HS256,key ).compact();
	}
	
	public key getKey() {
		return key;
	}

	public long getExpireToken() {
		return expireToken;
	}

	public boolean validToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
	
	public String getUserEmail(String token) {
		return getClaims(token).getSubject();
	}
	
	public String extractToken(String header) {
		if(header !=null &&header.startsWith("Bearer")) {
			return header.substring(7);
		}
		
		return null;
		
	}

}



