package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.Board;

import java.util.ArrayList;
import java.util.List;

public class TechniqueManager {
    final private List<TechniqueApplier> techniqueAppliers;
    final private List<Board> boards;
    final private int difficult;

    public TechniqueManager(int difficult) {
        techniqueAppliers = new ArrayList<>();

        // テクニックを追加実装するたびに追加
        techniqueAppliers.add(new TechniqueApplier1());
        techniqueAppliers.add(new TechniqueApplier2());
        techniqueAppliers.add(new TechniqueApplier3());

        boards = new ArrayList<>(techniqueAppliers.size());
        this.difficult = difficult;
    }

    public Board applyTechniques(Board board) {
        for (int techniqueId = 0; techniqueId < techniqueAppliers.size(); techniqueId++) {
            if (techniqueAppliers.get(techniqueId).getDifficult() <= difficult) {
                boards.add(techniqueId, techniqueAppliers.get(techniqueId).applyTechnique(board));
            }
        }
        return connectBoards(boards);
    }

    private Board connectBoards(List<Board> boards) {
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
