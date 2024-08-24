package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.Board;

abstract class TechniqueApplier {
    final static int difficult = 1;

    protected abstract void applyTechnique(Board board);
}
