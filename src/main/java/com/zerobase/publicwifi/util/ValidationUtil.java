package com.zerobase.publicwifi.util;

public class ValidationUtil {

    // 예시: 문자열이 비어있지 않은지 확인
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // 예시: 숫자가 양수인지 확인
    public static boolean isPositiveNumber(int value) {
        return value > 0;
    }

    // 추가적인 유효성 검사 메서드들을 필요에 따라 구현할 수 있습니다.
}
