package com.jy.lang.wordfindproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 단어 위치값 객체
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Range {

    /**
     * 시작 위치값
     */
    private int start;

    /**
     * 종료 위치값
     */
    private int end;
}
