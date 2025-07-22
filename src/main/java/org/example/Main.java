package org.example;

import org.example.model.User;
import org.example.service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n[1] 회원가입  [2] 로그인  [0] 종료");
            System.out.print("선택: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("사용자명 입력: ");
                    String username = sc.nextLine();
                    System.out.print("비밀번호 입력: ");
                    String password = sc.nextLine();

                    try {
                        User user = service.register(username, password);
                        System.out.println("✅ 회원가입 완료: " + user.getUsername());
                    } catch (Exception e) {
                        System.out.println("❌ 오류: " + e.getMessage());
                    }
                }
                case "2" -> {
                    System.out.print("사용자명 입력: ");
                    String username = sc.nextLine();
                    System.out.print("비밀번호 입력: ");
                    String password = sc.nextLine();

                    try {
                        User user = service.login(username, password);
                        System.out.println("✅ 로그인 성공! 환영합니다, " + user.getUsername());
                    } catch (Exception e) {
                        System.out.println("❌ 로그인 실패: " + e.getMessage());
                    }
                }
                case "0" -> {
                    System.out.println("👋 프로그램을 종료합니다.");
                    sc.close();
                    return;
                }
                default -> System.out.println("❗ 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }
}