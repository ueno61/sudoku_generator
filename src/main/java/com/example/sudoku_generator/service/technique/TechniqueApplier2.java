package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.*;

public class TechniqueApplier2 extends TechniqueApplier {
    // 各正方形、たて、よこに対して、そのユニット内で一つのセルの候補にしか残っていない数字があった場合に、
    // そのセルの候補から他の数字を外す(そのセルに入る数字を確定させる)

    protected TechniqueApplier2() {
        super(1);
    }

    @Override
    protected void applyTechnique(Board board) {

        // 各正方形に対して適用
        for (int startRow = 0; startRow < 9; startRow += 3) {
            for (int startCol = 0; startCol < 9; startCol += 3) {
                Square square = board.getSquare(startRow, startCol);
                determineSingleCandidateInSquare(square);
                board.applySquareToBoard(startRow, startCol, square);
            }
        }

        // 各行に対して適用
        for (int rowNum = 0; rowNum < 9; rowNum++) {
            Row row = board.getRow(rowNum);
            determineSingleCandidateInRow(row);
            board.applyRowToBoard(rowNum, row);
        }

        // 各列に対して適用
        for (int colNum = 0; colNum < 9; colNum++) {
            Col col = board.getCol(colNum);
            determineSingleCandidateInCol(col);
            board.applyColToBoard(colNum, col);
        }
    }

    private void determineSingleCandidateInSquare(Square square) {
        for (int num = 1; num <= 9; num++) {
            int count = 0;
            int candRow = -1;
            int candCol = -1;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (square.isCandidate(row, col, num)) {
                        count++;
                        candRow = row;
                        candCol = col;
                    }
                }
            }
            if (count == 1) {
                for (int removeNumber = 1; removeNumber <= 9; removeNumber++) {
                    if (removeNumber != num) {
                        square.removeCandidateFromCell(candRow, candCol, removeNumber);
                    }
                }
            }
        }
    }

    private void determineSingleCandidateInRow(Row row) {
        for (int num = 1; num <= 9; num++) {
            int count = 0;
            int candCol = -1;
            for (int col = 0; col < 9; col++) {
                if (row.isCandidate(col, num)) {
                    count++;
                    candCol = col;
                }
            }
            if (count == 1) {
                for (int removeNumber = 1; removeNumber <= 9; removeNumber++) {
                    if (removeNumber != num) {
                        row.removeCandidateFromCell(candCol, removeNumber);
                    }
                }
            }
        }
    }

    private void determineSingleCandidateInCol(Col col) {
        for (int num = 1; num <= 9; num++) {
            int count = 0;
            int candRow = -1;
            for (int row = 0; row < 9; row++) {
                if (col.isCandidate(row, num)) {
                    count++;
                    candRow = row;
                }
            }
            if (count == 1) {
                for (int removeNumber = 1; removeNumber <= 9; removeNumber++) {
                    if (removeNumber != num) {
                        col.removeCandidateFromCell(candRow, removeNumber);
                    }
                }
            }
        }
    }
}
