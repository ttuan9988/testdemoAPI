package com.ttuan.demoAPI.jwt;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ttuan.demoAPI.model.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	private static final long EXPIRE_DURATION = 24*60*60*1000; //24H
	
	@Value("${app.jwt.secret}")
	private String secretkey;
	
	public String createAccessToken(user user) {
		return Jwts.builder().setSubject(user.getId()+","+user.getUsername()).setIssuer("Tuan").setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, secretkey)
				.compact();
	}
	 public boolean validateAccessToken(String token) {
	        try {
	            Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
	            return true;
	        } catch (ExpiredJwtException ex) {
	            LOGGER.error("JWT expired", ex.getMessage());
	        } catch (IllegalArgumentException ex) {
	            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
	        } catch (MalformedJwtException ex) {
	            LOGGER.error("JWT is invalid", ex);
	        } catch (UnsupportedJwtException ex) {
	            LOGGER.error("JWT is not supported", ex);
	        } catch (SignatureException ex) {
	            LOGGER.error("Signature validation failed");
	        }
	         
	        return false;
	    }
	     
	    public String getSubject(String token) {
	        return parseClaims(token).getSubject();
	    }
	     
	    private Claims parseClaims(String token) {
	        return Jwts.parser()
	                .setSigningKey(secretkey)
	                .parseClaimsJws(token)
	                .getBody();
	    }
	
}
