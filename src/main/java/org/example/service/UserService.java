package org.example.service;

import org.example.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> userRepo = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public User register(String username, String rawPassword){
        // 키 값 확인. 있으면 중복
        if(userRepo.containsKey(username)){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        // 패스워드 암호화
        String salt = generateSalt();
        String hash = hashWithSHA256(rawPassword, salt);

        // 저장. 유저 생성
        User user = new User(username, hash, salt);
        userRepo.put(username, user);

        // 사용자 응답
        return user;
    }

    public User login(String username, String rawPassword) {
        User user = userRepo.get(username);
        if (user == null) {
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }

        // 입력된 패스워드 해시
        String inputHash = hashWithSHA256(rawPassword, user.getSalt());

        // 패스워드 비교. 맞으면 로그인 틀리면 에러
        if(!inputHash.equals(user.getPasswordHash())){
            throw new RuntimeException("패스워드가 틀렸습니다.");
        }

        return user;
    }

    public String generateSalt(){
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public String hashWithSHA256(String password, String salt){
        try{
            // sha-256 방식의 계산기
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 솔팅
            String saltedPassword = password  + salt;

            // 패스워드 바이트로 변환 후 계산 수행
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch(Exception e){
            throw new RuntimeException("에러: ", e);
        }
    }
}
