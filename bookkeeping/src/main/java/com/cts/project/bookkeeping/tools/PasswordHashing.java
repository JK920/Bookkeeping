package com.cts.project.bookkeeping.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class PasswordHashing {

    
    
    public String hashPassword(String password) {
        try {
          MessageDigest md = MessageDigest.getInstance("SHA-256");
          byte[] hashedPassword = md.digest(password.getBytes());
          return bytesToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException("Error hashing password", e);
        }
      }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
          sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
