package com.example.sudoku_generator.service.technique;

import com.example.sudoku_generator.service.board.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 適用テクニック：
 * ①各正方形、たて、よこですでに確定している数字を各マスの候補から除く。
 * ②各正方形、たて、よこで数字ごとに見て、一箇所しか入る場所がなければその数字で確定させる。
 */
class TechniqueApplier1 extends TechniqueApplier {

    protected TechniqueApplier1() {
        super(1);
    }

    @Override
    protected Board applyTechnique(Board board) {

        Board newBoard = new Board();
        for (int row = 0; row < 9;row++){
            for (int col = 0; col < 9;col++){
                newBoard.setNumber(row,col, board.getConfirmedNumber(row,col));
            }
        }
        // ① メモから数字を除く
        // 各正方形に対して適用
        for (int startRow = 0; startRow < 9; startRow += 3) {
            for (int startCol = 0; startCol < 9; startCol += 3) {
                Square square = newBoard.getSquare(startRow, startCol);
                removeFromSquare(square);
                newBoard.applySquareToBoard(startRow, startCol, square);
            }
        }

        // 各行に対して適用
        for (int rowNum = 0; rowNum < 9; rowNum++) {
            Row row = newBoard.getRow(rowNum);
            removeFromRow(row);
            newBoard.applyRowToBoard(rowNum, row);
        }

        // 各列に対して適用
        for (int colNum = 0; colNum < 9; colNum++) {
            Col col = newBoard.getCol(colNum);
            removeFromCol(col);
            newBoard.applyColToBoard(colNum, col);
        }

        // ② メモの内容から数字を確定させる
        List<Board> retBoards = new ArrayList<>();

        // 各正方形で確定
        for (int startRow = 0; startRow < 9; startRow += 3) {
            for (int startCol = 0; startCol < 9; startCol += 3) {
                Square square = newBoard.getSquare(startRow, startCol);
                square.confirmOnlyOneCellInUnit();
                Board addBoard = new Board();
                addBoard.applySquareToBoard(startRow,startCol,square);
                retBoards.add(addBoard);
            }
        }

        // 各行で確定
        for (int rowNum = 0; rowNum < 9; rowNum++) {
            Row row = newBoard.getRow(rowNum);
            row.confirmOnlyOneCellInUnit();
            Board addBoard = new Board();
            addBoard.applyRowToBoard(rowNum, row);
            retBoards.add(addBoard);
        }

        // 各列で確定
        for (int colNum = 0; colNum < 9; colNum++) {
            Col col = newBoard.getCol(colNum);
            col.confirmOnlyOneCellInUnit();
            Board addBoard = new Board();
            addBoard.applyColToBoard(colNum, col);
            retBoards.add(addBoard);
        }
        return connectBoards(retBoards);
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

    private void removeFromRow(Row row) {
        for (int col = 0; col < 9; col++) {
            int confirmedNumber = row.getConfirmedNumber(col);
            for (int colToRemove = 0; colToRemove < 9; colToRemove++) {
                if (col != colToRemove) {
                    row.removeCandidateFromCell(colToRemove, confirmedNumber);
                }
            }
        }
    }

    private void removeFromCol(Col col) {
        for (int row = 0; row < 9; row++) {
            int confirmedNumber = col.getConfirmedNumber(row);
            for (int rowToRemove = 0; rowToRemove < 9; rowToRemove++) {
                if (row != rowToRemove) {
                    col.removeCandidateFromCell(rowToRemove, confirmedNumber);
                }
            }
        }
    }
}
