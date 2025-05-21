package com.jy.lang.wordfindproject.controller;

import com.jy.lang.wordfindproject.domain.SermonPreach;
import com.jy.lang.wordfindproject.domain.Word;
import com.jy.lang.wordfindproject.service.SermonPreachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/apis")
@RequiredArgsConstructor
public class MainController {

    private final SermonPreachService sermonPreachService;

    @GetMapping("/lang/{code}")
    public String selectSermonPreach(@PathVariable String code, Model model) {

        Map<String,Object> sermonPreachMap = sermonPreachService.selectSermonPreach(code);

        List<SermonPreach> sermonPreachList = (List<SermonPreach>)sermonPreachMap.get("spanTagPreachList");
        Map<String,Word> wordMap = (Map<String,Word>)sermonPreachMap.get("wordMap");

        model.addAttribute("wordMap",wordMap);
        model.addAttribute("sermonPreachList",sermonPreachList);
        return "preach";
    }
}
