package com.jy.lang.wordfindproject.service;

import com.jy.lang.wordfindproject.domain.Word;
import com.jy.lang.wordfindproject.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WordService {

    private final WordRepository wordRepository;

    /**
     * 발표주제의 단어 조회하는 메서드
     * @param code ex)TR_0105
     * @return
     */
    public List<Word> selectWord(String code){
        List<Word> selectWordList = wordRepository.selectWord(code);
        return selectWordList;
    }

    /**
     * Word 객체 리스트에서 문장속 형태의 단어를 꺼내 소문자로 바꾸고 길이순으로 정렬해 리턴하는 메서드
     * 길이순으로 정렬 긴단어부터 태그 처리하기 위함
     * @return
     */
    public List<String>selectWordFormToLowerList(List<Word> wordList){
        List<String> wordFormList = new ArrayList<>();

        for(Word word : wordList){
            wordFormList.add(word.getWordForm().toLowerCase());//단어객체속 단어원형을 소문자로 변경
        }
        wordFormList.sort(Comparator.comparingInt(String::length).reversed());//길이순으로 정렬 긴단어부터 태그 처리하기 위함
        return wordFormList;
    }
}
