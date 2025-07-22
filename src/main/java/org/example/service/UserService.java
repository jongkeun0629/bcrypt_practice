package org.example.service;

import org.example.model.User;

import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> userRepo = new HashMap<>();

    public User register(String username, String rawPassword){
        // 키 값 확인. 있으면 중복
        if(userRepo.containsKey(username)){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        // 패스워드 암호화. 10: 암호화 강도
        String hash = BCrypt.hashpw(rawPassword, BCrypt.gensalt(10));

        // 저장. 유저 생성
        User user = new User(username, hash);
        userRepo.put(username, user);

        // 사용자 응답
        return user;
    }

    public User login(String username, String rawPassword) {
        User user = userRepo.get(username);
        if (user == null) {
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }

        // 패스워드 비교. 맞으면 로그인 틀리면 에러
        if(!BCrypt.checkpw(rawPassword, user.getPasswordHash())){
            throw new RuntimeException("패스워드가 틀렸습니다.");
        }

        return user;
    }
}
