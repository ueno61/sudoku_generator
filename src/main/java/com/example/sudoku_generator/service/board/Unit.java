package com.example.sudoku_generator.service.board;

import java.util.Iterator;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class Unit implements Iterable<Cell>, Cloneable {
    // 9つのcellを用意する
    protected Cell[] cells;

    protected Unit(int[] numbs) {
        cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            cells[i] = new Cell(numbs[i]);
        }
    }

    protected Unit(Cell[] cells) {
        this.cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            this.cells[i] = cells[i].clone();
        }
    }

    protected Unit() {
        this.cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            this.cells[i] = new Cell(0);
        }
    }

    protected Cell getCell(int num) {
        return cells[num];
    }

    protected boolean isAllCellFilled() { // cellsの全てのcellでconfNumが0でないならばtrueを返す
        boolean endFrag = true;
        for (Cell cell : cells) {
            if (cell.getConfirmedNumber() == 0) {
                endFrag = false;
                break;
            }
        }
        return endFrag;
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

    protected void excludeConfFromCand() { // そのcellsのcellで入る可能性のない数をfalseにする
        for (Cell cell : cells) {
            int confNum = cell.getConfirmedNumber();
            for (Cell otherCell : cells) {
                if (confNum != otherCell.getConfirmedNumber()) {
                    otherCell.setCell(confNum, false);
                }
            }
        }
        // 二つのセルについて、同じ二つの数のみを候補とする場合、それらのセル以外のセルに入る数の候補からその二つの数を外す
        for (Cell firstCell : cells) {
            for (Cell secondCell : cells) {
                if ((firstCell != secondCell) && (firstCell.myEquals(secondCell)) && (firstCell.getSumOfCandidates() == 2)) {
                    for (Cell otherCell : cells) {
                        if (!otherCell.myEquals(firstCell)) {
                            for (int k = 1; k <= 9; k++) { // firstCellとsecondCell以外のセルについてfirstCellの候補の数を候補から外す
                                if (firstCell.getCell(k)) {
                                    otherCell.setCell(k, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        // ３つのセルについて、同じ3つの数もしくはそれらの組み合わせのみを候補とする3つのセルがあった場合、それら以外のセルに入る数の候補からその3つの数を外す。
        for (int i = 1; i <= 9; i++) {
            for (int j = i + 1; j <= 9; j++) {
                for (int k = j + 1; k <= 9; k++) {
                    // 考えられる数の組み合わせは(i,j,k),(i,j),(i,k),(j,k)
                    // (i,j,k)を候補とする場合はセルの候補の数は3つ、(i,j),(i,k),(j,k)を候補とする場合はセルの候補の数は2つでないといけないため、場合分け
                    for (Cell firstCell : cells) {
                        boolean firstNumOfCandEqual3, firstNumOfCandEqual2;
                        firstNumOfCandEqual3 = firstCell.getCell(i) && firstCell.getCell(j) && firstCell.getCell(k) && (firstCell.getSumOfCandidates() == 3);
                        firstNumOfCandEqual2 = ((firstCell.getCell(i) && firstCell.getCell(j)) || (firstCell.getCell(j) && firstCell.getCell(k)) || (firstCell.getCell(i) && firstCell.getCell(k))) && (firstCell.getSumOfCandidates() == 2);
                        if (firstNumOfCandEqual3 || firstNumOfCandEqual2) {
                            for (Cell secondCell : cells) {
                                boolean secondNumOfCandEqual3, secondNumOfCandEqual2;
                                secondNumOfCandEqual3 = secondCell.getCell(i) && secondCell.getCell(j) && secondCell.getCell(k) && (secondCell.getSumOfCandidates() == 3);
                                secondNumOfCandEqual2 = ((secondCell.getCell(i) && secondCell.getCell(j)) || (secondCell.getCell(j) && secondCell.getCell(k)) || (secondCell.getCell(i) && secondCell.getCell(k))) && (secondCell.getSumOfCandidates() == 2);
                                if ((secondNumOfCandEqual3 || secondNumOfCandEqual2) && !secondCell.myEquals(firstCell)) {
                                    for (Cell thirdCell : cells) {
                                        boolean thirdNumOfCandEqual3, thirdNumOfCandEqual2;
                                        thirdNumOfCandEqual3 = thirdCell.getCell(i) && thirdCell.getCell(j) && thirdCell.getCell(k) && (thirdCell.getSumOfCandidates() == 3);
                                        thirdNumOfCandEqual2 = ((thirdCell.getCell(i) && thirdCell.getCell(j)) || (thirdCell.getCell(j) && thirdCell.getCell(k)) || (thirdCell.getCell(i) && thirdCell.getCell(k))) && (thirdCell.getSumOfCandidates() == 2);
                                        if ((thirdNumOfCandEqual3 || thirdNumOfCandEqual2) && !thirdCell.myEquals(firstCell) && !thirdCell.myEquals(secondCell)) {
                                            for (Cell otherCell : cells) {
                                                if ((otherCell.myEquals(firstCell)) && (otherCell.myEquals(secondCell)) && (otherCell.myEquals(thirdCell))) {
                                                    otherCell.setCell(i, false);
                                                    otherCell.setCell(j, false);
                                                    otherCell.setCell(k, false);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected void setConfNumUnit() {
        for (int i = 1; i <= 9; i++) {
            Cell onlyCell = null;
            int count = 0;
            for (Cell cell : cells) {
                if (cell.getCell(i)) {
                    count++;
                    onlyCell = cell;
                }
            }
            if (count == 1) {
                onlyCell.setConfirmedNumber(i);
                for (Cell cell : cells) {
                    if (!cell.myEquals(onlyCell)) {
                        cell.setCell(i, false);
                    }
                }
            }
        }
    }

    /* protected void printUnit(){
        for(Cell cell:cells){
            cell.printCell();
        }
        System.out.println();
    } */

    protected boolean contains(Cell cell) {
        boolean contain = false;
        for (Cell cellInThisUnit : cells) {
            if (cell.myEquals(cellInThisUnit)) {
                contain = true;
                break;
            }
        }
        return contain;
    }

    public boolean myEquals(Unit otherUnit) {
        boolean sameUnit = true;
        for (int i = 0; i < 9; i++) {
            if (!this.cells[i].myEquals(otherUnit.cells[i])) {
                sameUnit = false;
                break;
            }
        }
        return sameUnit;
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
