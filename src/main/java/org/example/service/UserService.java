package org.example.service;

import org.example.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> userRepo = new HashMap<>();

    public User register(String username, String rawPassword){
        // 키 값 확인. 있으면 중복
        if(userRepo.containsKey(username)){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        // 패스워드 암호화
        String hash = hashWithSHA256(rawPassword);

        // 저장
        User user = new User(username, hash);
        userRepo.put(username, user);

        // 사용자 응답
        return user;
    }

    public String hashWithSHA256(String password){
        try{
            // sha-256 방식의 계산기
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 패스워드 바이트로 변환 후 계산 수행
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch(Exception e){
            throw new RuntimeException("에러: ", e);
        }
    }
}
