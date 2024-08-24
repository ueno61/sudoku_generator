package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.Board;

import java.util.ArrayList;
import java.util.List;

public class TechniqueManager {
    final private List<TechniqueApplier> techniqueAppliers;

    public TechniqueManager() {
        techniqueAppliers = new ArrayList<>();
        techniqueAppliers.add(new TechniqueApplier1());
    }

    public void applyTechniques(Board board) {
        for(TechniqueApplier techniqueApplier:techniqueAppliers) {
            techniqueApplier.applyTechnique(board);
        }
    }
}
