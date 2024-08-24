package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.Board;

abstract class TechniqueApplier {
    final int difficult = 1; // テクニックの難易度

    protected abstract void applyTechnique(Board board);
    protected int getDifficult() {
        return this.difficult;
    }
}
