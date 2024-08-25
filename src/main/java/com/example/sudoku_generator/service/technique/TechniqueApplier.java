package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.*;

abstract class TechniqueApplier {
    final int difficult; // テクニックの難易度

    protected TechniqueApplier(int difficult) {
        this.difficult = difficult;
    }
    protected abstract void applyTechnique(Board board); // boardの各セルのメモから数字を減らすだけの処理を行う
    protected int getDifficult() {
        return this.difficult;
    }
}
