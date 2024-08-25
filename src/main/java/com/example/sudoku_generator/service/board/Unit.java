package com.example.sudoku_generator.service.board;

import java.util.Iterator;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class Unit implements Iterable<Cell>, Cloneable {
    // 9つのcellを用意する
    protected Cell[] cells;

    public Unit(int[] numbs) {
        cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            cells[i] = new Cell(numbs[i]);
        }
    }

    public Unit(Cell[] cells) {
        this.cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            this.cells[i] = cells[i].clone();
        }
    }

    public Unit() {
        this.cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            this.cells[i] = new Cell(0);
        }
    }

    protected Cell getCell(int num) {
        return cells[num];
    }

    public void removeCandidateFromCell(int num, int removedNumber) {
        cells[num].removeCandidate(removedNumber);
    }

    public int getConfirmedNumber(int num) {
        return cells[num].getConfirmedNumber();
    }

    protected boolean isValid() {
        int[] numberCounts = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (Cell cell : cells) {
            if (cell.getConfirmedNumber() != 0) {
                numberCounts[cell.getConfirmedNumber() - 1]++;
            }
        }
        for (int count : numberCounts) {
            if (count > 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Unit clone() {
        try {
            Unit clone = (Unit) super.clone();
            clone.cells = this.cells.clone();
            for (int i = 0; i < 9; i++) {
                clone.cells[i] = this.cells[i].clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private class MyIterator implements Iterator<Cell> {
        int i;

        public MyIterator() {
            i = 0;
        }

        @Override
        public boolean hasNext() {
            if (i < 9) {
                i++;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Cell next() {
            return cells[i - 1];
        }
    }

    public Iterator<Cell> iterator() {
        return new MyIterator();
    }
}
