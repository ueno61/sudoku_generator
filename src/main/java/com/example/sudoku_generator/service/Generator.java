package com.example.sudoku_generator.service;

import com.example.sudoku_generator.service.board.Board;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    // 問題を作成するクラス
    int difficult;
    Board board;

    public Generator(int difficult) {
        this.difficult = difficult;
    }

    public Board getBoard() {
        return this.board;
    }
    public void generateProblem() {
        generateCompletedBoard();
    }
    private void generateCompletedBoard() {
        this.board = new Board();
        fillBoard(this.board);
    }

    private boolean fillBoard(Board board) {
        // 引数のboardの空いているマス一つに入れられる数字を入れるという処理を再帰的に行う
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getConfirmedNumber(row,col) == 0) { // 空いているマスを見つける
                    List<Integer> list9 = generateRandomNumbers(1,9);
                    for (int num : list9) {
                        board.setNumber(row,col,num);
                        if (board.isValid()) { // 数字を入れて矛盾がないかチェック
                            if (fillBoard(board)) {
                                return true; // 成功した場合、再帰を続ける
                            }
                            board.setNumber(row,col,0); // 失敗した場合、元に戻す
                        } else {
                            board.setNumber(row,col,0);
                        }
                    }
                    return false; // どの数字も入らなければ戻る
                }
            }
        }
        return true;
    }

    private List<Integer> generateRandomNumbers(int start,int end) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers); // リストをランダムにシャッフル
        return numbers;
    }
    public void printBoard() {
        this.board.printBoard();
    }
}

