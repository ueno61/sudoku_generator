package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.*;

public class TechniqueApplier3 extends TechniqueApplier {
    // 各セルで候補が１つしかない場合に数字を確定させる

    protected TechniqueApplier3() {
        super(3);
    }

    @Override
    protected Board applyTechnique(Board board) {
        board.finalizeConfirmedNumbers();
        return board;
    }
}
