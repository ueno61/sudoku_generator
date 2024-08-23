package com.example.sudoku_generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.example.sudoku_generator.service.field.Field;

@Controller
public class SudokuController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }
   
    //問題ページ
    @GetMapping("/problem")
    public String getProblem(Model model, @RequestParam String diff) {
    
        Field sampleField = new Field();
        int[][] sampleProblem = new int[9][9];
        //ここで生成したfieldを取得
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                sampleProblem[i][j] = (i == j) ? i + 1 : 0;
            }
        }
        sampleField.initField(sampleProblem);

        //Fieldをint[9][9]に変換

        // int[][] displayField = new int[9][9];
        // for(int i = 0; i < 9; i++){
        //     for(int j = 0; j < 9; j++){

        //         displayField[i][j] = sampleField.getCellFromRow(i, j).getConfNum();
        // }

        //難易度の文字列と問題を渡す
        model.addAttribute("diff", diff);
        model.addAttribute("field", sampleProblem);
        return "problem";
    }
}