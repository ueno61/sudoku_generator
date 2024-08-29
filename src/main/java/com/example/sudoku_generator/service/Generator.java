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
        this.board = new Board();
        this.difficult = difficult;
    }

    public Board getBoard() {
        return this.board;
    }

    public void generateProblem() {
        generateCompletedBoard();
        if (difficult == 1) {
            removeNumbers(20);
        } else if (difficult <= 3) {
            removeNumbers(40);
        } else {
            removeNumbers(81);
        }
    }

    private void generateCompletedBoard() {
        fillBoardRandomly(this.board);
    }

    private boolean fillBoardRandomly(Board board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getConfirmedNumber(row, col) == 0) { // 空いているマスを見つける
                    List<Integer> list9 = generateRandomNumbers(1, 9);
                    for (int num : list9) {
                        board.setNumber(row, col, num);
                        if (board.isValid()) { // 数字を入れて矛盾がないかチェック
                            if (fillBoardRandomly(board)) {
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

    private boolean fillBoardInOrder(Board board, int[] numbs) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getConfirmedNumber(row, col) == 0) { // 空いているマスを見つける
                    for (int num : numbs) {
                        board.setNumber(row, col, num);
                        if (board.isValid()) { // 数字を入れて矛盾がないかチェック
                            if (fillBoardInOrder(board, numbs)) {
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

            // 穴を開けた盤面に二パターンの穴の埋め方を行い、そのにパターンで結果が一致したら解は一つだとする
            Board board1, board2;
            board1 = board.clone(); // 空いているマスにバックトラックで{1,2,...,9}の順で入れていく盤面
            board2 = board.clone(); // 空いているマスにバックトラックで{9,8,...,1}の順で入れていく盤面
            int[] ascendingOrder = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            int[] descendingOrder = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
            fillBoardInOrder(board1, ascendingOrder);
            fillBoardInOrder(board2, descendingOrder);
            boolean isUniqueSolution = board1.myEquals(board2);

            if (isSolvableByTechniques() && isUniqueSolution) {
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
        return solver.solve(copyBoard).isAllCellFilled();
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

