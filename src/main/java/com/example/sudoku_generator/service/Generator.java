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
        fillBoard();
        removeNumbers(20);
    }

    private boolean fillBoard() {
        // 引数のboardの空いているマス一つに入れられる数字を入れるという処理を再帰的に行う
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getConfirmedNumber(row, col) == 0) { // 空いているマスを見つける
                    List<Integer> list9 = generateRandomNumbers(1, 9);
                    for (int num : list9) {
                        board.setNumber(row, col, num);
                        if (board.isValid()) { // 数字を入れて矛盾がないかチェック
                            if (fillBoard()) {
                                return true; // 成功した場合、再帰を続ける
                            }
                            board.removeNumber(row, col); // 失敗した場合、元に戻す
                        } else {
                            board.removeNumber(row, col);
                        }
                    }
                    return false; // どの数字も入らなければ戻る
                }
            }
        }
        return true;
    }

    private void removeNumbers(int holeNum) {
        List<Integer> list81 = generateRandomNumbers(0, 80);
        int removed = 0;
        for (int popNum : list81) {
            int row = popNum / 9;
            int col = popNum % 9;
            int targetNumber = board.getConfirmedNumber(row, col);
            board.removeNumber(row, col);
            if (isSolvableByTechniques()) {
                removed++;
            } else {
                board.setNumber(row, col, targetNumber);
            }

            if (removed >= holeNum) break;
        }
    }

    private boolean isSolvableByTechniques() {
        Board copyBoard = board.clone();
        Solver solver = new Solver(difficult);
        solver.solve(copyBoard);
        return copyBoard.isAllCellFilled();
    }

    private List<Integer> generateRandomNumbers(int start, int end) {
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

