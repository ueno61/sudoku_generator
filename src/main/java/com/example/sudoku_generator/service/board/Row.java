package com.example.sudoku_generator.service.board;

public class Row extends Unit {
    public Row(int[] numbs) {
        super(numbs);
    }

    public Row(Cell[] cells) {
        super(cells);
    }

    public Row() {
        super();
    }
}
