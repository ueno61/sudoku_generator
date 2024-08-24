package com.example.sudoku_generator.service;

import com.example.sudoku_generator.service.board.Board;
import com.example.sudoku_generator.service.technique.TechniqueManager;

public class Solver {
    int difficult;
    TechniqueManager techniqueManager;

    public Solver(int difficult) {
        this.difficult = difficult;
        techniqueManager = new TechniqueManager(difficult);
    }

    public void solve(Board board) {
        Board copyBoard = board.clone();
        while(true) {
            techniqueManager.applyTechniques(board);
            board.finalizeConfirmedNumbers();
            if(board.isAllCellFilled()||copyBoard.myEquals(board))break;
            copyBoard = board.clone();
        }
    }
}
