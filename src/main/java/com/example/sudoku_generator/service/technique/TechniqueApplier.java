package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.*;

import java.util.List;

abstract class TechniqueApplier {
    final int difficult; // テクニックの難易度
    // テクニックの難易度は、メモから数字を減らすときに、他のマスの確定した数字を見るだけならeasy(1)、
    // 同ユニットのメモを参照するならnormal(3)、別ユニットのメモを参照するならdifficult(5)

    protected TechniqueApplier(int difficult) {
        this.difficult = difficult;
    }
    protected abstract Board applyTechnique(Board board); // boardの各セルのメモから数字を減らすだけの処理を行う
    protected int getDifficult() {
        return this.difficult;
    }
    protected Board connectBoards(List<Board> boards) {
        // 各テクニックを適用した盤面を結合

        Board retBoard = new Board();
        // 各セルについて、いずれかの盤面で確定している数字があればそれに合わせる。
        // 確定している数字がなければ、全ての盤面で共通しているメモの数字のみのこす
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                for (Board board : boards) {
                    // 確定している数字のチェック
                    if (board.getConfirmedNumber(row, col) != 0) {
                        retBoard.setNumber(row, col, board.getConfirmedNumber(row, col));
                        break;
                    }

                    // メモのチェック
                    boolean[] memo = board.getCellMemo(row, col);
                    for (int num = 1; num <= 9; num++) {
                        if (!memo[num]) {
                            retBoard.removeCandidate(row, col, num);
                        }
                    }

                }
            }
        }
        return retBoard;
    }
}
