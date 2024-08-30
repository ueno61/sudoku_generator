package com.example.sudoku_generator.controller;

import com.example.sudoku_generator.service.Generator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.example.sudoku_generator.service.board.Board;

@Controller
public class SudokuController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    //問題ページ
    @GetMapping("/problem")
    public String getProblem(Model model, @RequestParam String diff) {

        int diffInt;
        if (diff.equals("easy")) {
            diffInt = 1;
        } else if (diff.equals("normal")) {
            diffInt = 3;
        } else {
            diffInt = 5;
        }
        Generator generator = new Generator(diffInt);
        generator.generateProblem();//ここで生成したfieldを取得
        int[][] problem = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                problem[i][j] = generator.getBoard().getConfirmedNumber(i, j);
            }
        }

        //難易度の文字列と問題を渡す
        model.addAttribute("diff", diff);
        model.addAttribute("field", problem);
        return "problem";
    }
}