package com.example.sudoku_generator.service.board;

public class Board implements Cloneable {
    Cell[][] cells;

    public Board() {
        cells = new Cell[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new Cell(0);
            }
        }
    }

    public boolean isAllCellFilled() {  // Boardの全てのセルでconfNumが0じゃないならtrueを返す
        boolean endFrag = true;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (cells[row][col].getConfirmedNumber() == 0) {
                    endFrag = false;
                    break;
                }
            }
        }
        return endFrag;
    }

    public void finalizeConfirmedNumbers() {
        // 各セルで候補が１つしかない場合に数字を確定させる
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].finalizeConfirmedNumber();
            }
        }
    }

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    clone.cells[row][col] = this.cells[row][col].clone();
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public boolean myEquals(Board otherBoard) {
        boolean same = true;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!this.cells[row][col].myEquals(otherBoard.cells[row][col])) {
                    same = false;
                }
            }
        }
        return same;
    }
}