package com.jy.lang.wordfindproject.service;

import com.jy.lang.wordfindproject.domain.Range;
import com.jy.lang.wordfindproject.domain.SermonPreach;
import com.jy.lang.wordfindproject.domain.Word;
import com.jy.lang.wordfindproject.enums.LanguageCodeEnum;
import com.jy.lang.wordfindproject.repository.SermonPreachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class SermonPreachService {

    private final SermonPreachRepository sermonPreachRepository;

    private final WordService wordService;

    /**
     * 발표화면을 위한 메인 메서드
     * @param code : 발표코드 ex)tr-0105
     */
    public Map<String, Object> selectSermonPreach(String code) {

        //발표 문장 객체 조회
        List<SermonPreach> sermonPreachList = sermonPreachRepository.selectSermonPreach(code);

        // 발표 속 단어객체 조회
        List<Word> wordList = wordService.selectWord(code);

        List<String> lowerWordFormList = new ArrayList<>();//Word객체에서 wordForm을 소문자로 변경 문장속 단어들과 비교하기 위해

        lowerWordFormList = wordService.selectWordFormToLowerList(wordList);//객체 리스트에서 문장속 형태의 단어를 꺼내 소문자로 바꾸고 길이순으로 정렬해 리턴

        List<SermonPreach> spanTagPreachList = new ArrayList<>();//span태그를 추가한 발표 문장을 담기 위함

        for (SermonPreach sermonPreach : sermonPreachList) {
            String contents = sermonPreach.getContents();//SermonPreach 객체에서 발표 문장 데이터 꺼냄
            List<Range> rangeList = findTagIndexLocation(contents, lowerWordFormList, code);//문장에서 태그를 추가할곳 위치를 담음
            String appendTagContent = appendTag(rangeList, contents);//태그가 추가된 문장
            spanTagPreachList.add(sermonPreach.toBuilder().contents(appendTagContent).build());//span 태그가 추가된 발표문장 리스트
        }

        Map<String, Word> wordMap = new HashMap<>();
        for (Word word : wordList) {
            wordMap.put(word.getWordForm().toLowerCase(), word);
        }

        Map<String, Object> sermonPreachMap = new HashMap<>();
        sermonPreachMap.put("spanTagPreachList", spanTagPreachList);
        sermonPreachMap.put("wordMap", wordMap);

        return sermonPreachMap;
    }

    /**
     * span 태그를 달아주기 위해 앞 뒤 인덱스 위치값을 저장하는 메서드
     *
     * @param contents           문장
     * @param lowerWordFormList 소문자로 변경한 단어 목록
     * @return
     */
    public List<Range> findTagIndexLocation(String contents, List<String> lowerWordFormList, String code) {

        List<Range> rangeList = new ArrayList<>();//위치값을 담을 객체 리스트
        Locale locale = LanguageCodeEnum.getLocaleByCode(code.substring(0, 2));//튀르키예어 문제 발생해서 locale
        contents = contents.toLowerCase(locale);

        //단어 목록에서 단어 꺼냄
        for (String wordForm : lowerWordFormList) {
            int startIndexNum;
            int endIndexNum = 0;

            //문장내에 단어가 있는지 확인하고, 마지막 인덱스를 기준으로 다시 단어가 있는지 확인함 위치값을 저장해놓기위함
            //(한문장에 같은 단어가 여러개 있기 때문)
            while (true) {
                startIndexNum = contents.indexOf(wordForm, endIndexNum);//시작 인덱스 번호
                if (startIndexNum == -1) {// contents 내에 단어가 없으면 리턴
                    break;
                }
                endIndexNum = startIndexNum + wordForm.length();

                //지정된 범위 내에 또 추가 되지 않도록 ex)aile, ile 라는 단어가 있을때 <span>a<sapn>ile</span></span> 이렇게 안되도록
                if (overlapCheck(rangeList, startIndexNum, endIndexNum)) {//태그 영역 중복 확인
                    rangeList.add(new Range(startIndexNum, endIndexNum));
                }
            }

        }
        return rangeList;
    }

    /**
     * 스팬태그를 지정하기 위한 범위와 겹치지 않는지 조회
     */
    public boolean overlapCheck(List<Range> rangeList, int startIndexNum, int endIndexNum) {

        if (!rangeList.isEmpty()) {
            for (Range range : rangeList) {
                if (startIndexNum < range.getEnd() && endIndexNum > range.getStart()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 객체를 통해 전달된 index를 가지고 한번에 태그 추가
     *
     * @param rangeList : 인덱스 위치값이 기록된 리스트
     * @param contents  : 문장
     * @return : span태그가 추가된 문장.
     */
    public String appendTag(List<Range> rangeList, String contents) {
        StringBuilder sb = new StringBuilder();
        sb.append(contents);
        String spanTag = "<span class=\"words\">";
        String spanEndTag = "</span>";

        int startIndexNum = 0;
        int endIndexNum = 0;
        rangeList.sort(Comparator.comparingInt(Range::getEnd).reversed());// 뒷자리 인덱스부터 추가하기 위함 앞에서 부터 넣으면 태그 넣으면서 순서 밀리니까

        for (Range range : rangeList) {
            endIndexNum = range.getEnd();
            sb.insert(endIndexNum, spanEndTag);
            startIndexNum = range.getStart();
            sb.insert(startIndexNum, spanTag);
        }

        return sb.toString();
    }

}





















