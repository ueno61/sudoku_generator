package com.example.sudoku_generator.service;

import com.example.sudoku_generator.service.board.Board;
import com.example.sudoku_generator.service.technique.TechniqueManager;

public class Solver {
    int difficult;
    TechniqueManager techniqueManager;
//    private int step = 0;

    public Solver(int difficult) {
        this.difficult = difficult;
        techniqueManager = new TechniqueManager(difficult);
    }

    public Board solve(Board board) {
        Board copyBoard = board.clone();
        while(true) {
//            step++;
            board = techniqueManager.applyTechniques(board);
//            {
//                System.out.println("step:" + step);
//                board.printBoard();
//            }
            if(board.isAllCellFilled()||copyBoard.myEquals(board))break;
            copyBoard = board.clone();
        }
        return board;
    }
}
