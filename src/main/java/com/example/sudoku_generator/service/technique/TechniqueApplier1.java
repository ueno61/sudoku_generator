package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.Board;

public class TechniqueApplier1 extends TechniqueApplier{
    // 各正方形、たて、よこですでに確定している数字をメモから除く

    protected TechniqueApplier1() {
        super(1);
    }
    @Override
    protected void applyTechnique(Board board) {
    }
}
