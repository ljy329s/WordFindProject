package com.jy.lang.wordfindproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Alias("sermonPreach")
public class SermonPreach {

    /**
     * 식별키
     */
    private String id;

    /**
     * 언어코드
     */
    private String lang;

    /**
     * 발표코드
     */
    private String code;

    /**
     * 내용
     */
    private String contents;

    /**
     * 타입 ex)T(제목),ST(소제목),C(내용)
     */
    private String type;

    /**
     * 내부정렬순서
     */
    private int innerOrder;

}
