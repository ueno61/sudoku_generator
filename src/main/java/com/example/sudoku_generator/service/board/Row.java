package com.example.sudoku_generator.service.board;

class Row extends Unit {
    protected Row(int[] numbs) {
        super(numbs);
    }

    protected Row(Cell[] cells) {
        super(cells);
    }

    protected Row() {
        super();
    }
}
