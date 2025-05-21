package com.jy.lang.wordfindproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Alias("ward")
public class Word {
    /**
     * 식별키
     */
    private String id;

    /**
     * 발표코드
     */
    private String code;

    /**
     * 예문
     */
    private String wordForm;

    /**
     * 단어, 동사원형등
     */
    private String baseForm;

    /**
     * 뜻, 의미
     */
    private String mean;
}
