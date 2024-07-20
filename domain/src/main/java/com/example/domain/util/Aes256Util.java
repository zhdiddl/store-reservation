package com.example.domain.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Aes256Util {
    public static String algorithm = "AES/CBC/PKCS5Padding"; // CBC 모드 사용
    private static final String KEY = "12345678901234567890123456789012"; // 32바이트 AES 키
    private static final String IV = KEY.substring(0, 16); // 16바이트 IV 키

    // 암호화 메소드
    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm); // 암복호화 알고리즘 설정
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES"); // AES 키 설정
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)); // IV 설정
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec); // 암호화 모드 설정

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)); // 평문을 암호화해서 바이트 배열로 변환
            return Base64.getEncoder().encodeToString(encryptedBytes); // 바이트 배열을 문자열로 인코딩

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 복호화 메소드
    public static String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm); // 암복호화 알고리즘 설정
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES"); // AES 키 설정
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)); // IV 설정
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec); // 복호화 모드 설정

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText); // 문자열을 바이트 배열로 디코딩
            byte[] decryptedBytes = cipher.doFinal(decodedBytes); // 암호화된 바이트 배열을 복호화해서 평문 바이트 배열로 변환
            return new String(decryptedBytes, StandardCharsets.UTF_8); // 평문 바이트 배열을 문자열 변환해서 반환

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
