package com.example.sudoku_generator.service.board;

class Square extends Unit {
    protected Square(int[] numbs) {
        super(numbs);
    }

    protected Square(Cell[] cells) {
        super(cells);
    }

    protected Square() {
        super();
    }

    protected void setCell(int row, int col, Cell cell) {
        cells[row * 3 + col].reconCell(cell);
    }

    protected Cell getCell(int row, int col) {
        return cells[row * 3 + col];
    }
}
