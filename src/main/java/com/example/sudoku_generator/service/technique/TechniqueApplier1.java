package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.*;

public class TechniqueApplier1 extends TechniqueApplier {
    // 各正方形、たて、よこですでに確定している数字をメモから除く

    protected TechniqueApplier1() {
        super(1);
    }

    @Override
    protected void applyTechnique(Board board) {
        for (int startRow = 0; startRow < 9; startRow += 3) {
            for (int startCol = 0; startCol < 9; startCol += 3) {
                Square square = board.getSquare(startRow, startCol);
                removeFromSquare(square);
                board.applySquareToBoard(startRow, startCol, square);
            }
        }
    }

    private void removeFromSquare(Square square) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int confirmedNumber = square.getConfirmedNumber(row, col);
                for (int rowToRemove = 0; rowToRemove < 3; rowToRemove++) {
                    for (int colToRemove = 0; colToRemove < 3; colToRemove++) {
                        if (!(row == rowToRemove && col == colToRemove)) {
                            square.removeCandidateFromCell(rowToRemove, colToRemove, confirmedNumber);
                        }
                    }
                }
            }
        }
    }
}
