package com.jy.lang.wordfindproject.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Getter
@Slf4j
public enum LanguageCodeEnum {

    TR("tr", new Locale("tr", "TR")),
    EN("en", Locale.ENGLISH),
    JA("ja", new Locale("ja", "JP")),
    ZH("zh", new Locale("zh", "CN"));

    private final String code;
    private final Locale locale;

    // 생성자
    LanguageCodeEnum(String code, Locale locale) {
        this.code = code;
        this.locale = locale;
    }

    // code를 기준으로 Locale을 찾는 메서드
    public static Locale getLocaleByCode(String code) {
        for (LanguageCodeEnum lang : LanguageCodeEnum.values()) {
            if (lang.code.equalsIgnoreCase(code)) {
                return lang.locale;
            }
        }
        return Locale.ENGLISH; // 기본값은 영어로 설정
    }
}

