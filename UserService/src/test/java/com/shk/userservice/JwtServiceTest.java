//package com.shk.userservice;
//
//import com.shk.userservice.services.JwtService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class JwtServiceTest {
//
//    private JwtService jwtService;
//    private UserDetails userDetails;
//
//    @BeforeEach
//    void setUp() {
//        jwtService = new JwtService();
//        userDetails = new User("testUser", "password", null); // Replace with your actual UserDetails implementation
//    }
//
//    @Test
//    void testGenerateToken() {
//        String token = jwtService.generateToken(userDetails);
//        assertTrue(token != null && !token.isEmpty());
//    }
//
//    @Test
//    void testGenerateRefreshToken() {
//        String refreshToken = jwtService.generateRefreshToken(userDetails);
//        assertTrue(refreshToken != null && !refreshToken.isEmpty());
//    }
//
//    @Test
//    void testExtractUsername() {
//        String token = jwtService.generateToken(userDetails);
//        String extractedUsername = jwtService.extractUsername(token);
//        assertEquals(userDetails.getUsername(), extractedUsername);
//    }
//
//    @Test
//    void testIsTokenValid() {
//        String token = jwtService.generateToken(userDetails);
//        boolean isTokenValid = jwtService.isTokenValid(token, userDetails);
//        assertTrue(isTokenValid);
//    }
//}
