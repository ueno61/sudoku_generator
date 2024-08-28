package com.example.sudoku_generator.service.board;

public class Square extends Unit {
    protected Square(int[] numbs) {
        super(numbs);
    }

    protected Square(Cell[] cells) {
        super(cells);
    }

    public Square() {
        super();
    }

    public void setCell(int row, int col, Cell cell) {
        cells[row * 3 + col].reconCell(cell);
    }

    protected Cell getCell(int row, int col) {
        return cells[row * 3 + col];
    }

    public void removeCandidateFromCell(int row, int col, int removedNumber) {
        super.removeCandidateFromCell(row * 3 + col, removedNumber);
    }

    public int getConfirmedNumber(int row, int col) {
        return super.getConfirmedNumber(row * 3 + col);
    }

    public boolean isCandidate(int row, int col, int memoNum) {
        return super.isCandidate(row * 3 + col, memoNum);
    }
}
