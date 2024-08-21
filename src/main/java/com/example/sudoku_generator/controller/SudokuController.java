package com.example.sudoku_generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SudokuController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }
   
    //問題ページ
    @GetMapping("/problem")
    public String getProblem() {
        return "Problem";
    }
}