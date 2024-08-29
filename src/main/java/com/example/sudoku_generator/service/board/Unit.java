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

    /**
     * 各数字について、ユニット内で一箇所しか入る場所がなければそこのセルはその数字で確定させる
     */
    public void confirmOnlyOneCellInUnit() {
        for (int num = 1; num <= 9; num++) {
            int count = 0;
            int confirmedId = 0;
            for (int id = 0; id < 9; id++) {
                if (cells[id].isCandidate(num)) {
                    count++;
                    confirmedId = id;
                }
            }
            if (count == 1) {
                cells[confirmedId].setConfirmedNumber(num);
            }
        }
    }

    public int getConfirmedNumber(int num) {
        return cells[num].getConfirmedNumber();
    }

    public boolean isCandidate(int num, int memoNum) {
        return cells[num].isCandidate(memoNum);
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
