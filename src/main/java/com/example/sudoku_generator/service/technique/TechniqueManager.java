package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.Board;

import java.util.ArrayList;
import java.util.List;

public class TechniqueManager {
    final private List<TechniqueApplier> techniqueAppliers;
    final private int difficult;

    public TechniqueManager(int difficult) {
        techniqueAppliers = new ArrayList<>();

        // テクニックを追加実装するたびに追加
        techniqueAppliers.add(new TechniqueApplier1());
        techniqueAppliers.add(new TechniqueApplier2());


        this.difficult = difficult;
    }

    public void applyTechniques(Board board) {
        for(TechniqueApplier techniqueApplier:techniqueAppliers) {
            if(techniqueApplier.getDifficult() <= difficult) {
                techniqueApplier.applyTechnique(board);
            }
        }
    }
}
