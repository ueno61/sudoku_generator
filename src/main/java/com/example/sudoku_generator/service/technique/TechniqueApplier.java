package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.*;

abstract class TechniqueApplier {
    final int difficult; // テクニックの難易度
    // テクニックの難易度は、メモから数字を減らすときに、他のマスの確定した数字を見るだけならeasy(1)、
    // 同ユニットのメモを参照するならnormal(3)、別ユニットのメモを参照するならdifficult(5)

    protected TechniqueApplier(int difficult) {
        this.difficult = difficult;
    }
    protected abstract void applyTechnique(Board board); // boardの各セルのメモから数字を減らすだけの処理を行う
    protected int getDifficult() {
        return this.difficult;
    }
}
