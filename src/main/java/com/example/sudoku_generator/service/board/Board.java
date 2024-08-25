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

    public int getConfirmedNumber(int row, int col) {
        return cells[row][col].getConfirmedNumber();
    }

    public void setNumber(int row, int col, int num) {
        cells[row][col] = new Cell(num);
    }

    public void removeNumber(int row, int col) {
        cells[row][col] = new Cell(0);
    }

    public Square getSquare(int startRow, int startCol) {
        startRow = (startRow / 3) * 3;
        startCol = (startCol / 3) * 3;
        Cell[] squareCells = new Cell[9];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                squareCells[row * 3 + col] = this.cells[startRow + row][startCol + col].clone();
            }
        }
        return new Square(squareCells);
    }

    public void applySquareToBoard(int startRow, int startCol, Square square) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.cells[startRow + row][startCol + col].reconCell(square.getCell(row,col));
            }
        }
    }
    public Row getRow(int row) {
        Cell[] rowCells = new Cell[9];
        for (int col = 0; col < 9; col++) {
            rowCells[col] = cells[row][col].clone();
        }
        return new Row(rowCells);
    }

    public void applyRowToBoard(int row, Row rowUnit) {
        for (int col = 0; col < 9; col++) {
            cells[row][col].clone().reconCell(rowUnit.getCell(col));
        }
    }

    public Col getCol(int col) {
        Cell[] colCells = new Cell[9];
        for (int row = 0; row < 9; row++) {
            colCells[row] = cells[row][col].clone();
        }
        return new Col(colCells);
    }

    public void applyColToBoard(int col, Col colUnit) {
        for (int row = 0; row < 9; row++) {
            cells[row][col].clone().reconCell(colUnit.getCell(row));
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

    public boolean isValid() {
        for (int startRow = 0; startRow < 9; startRow += 3) {
            for (int startCol = 0; startCol < 9; startCol += 3) {
                if (!getSquare(startRow, startCol).isValid()) {
                    return false;
                }
            }
        }

        for (int row = 0; row < 9; row++) {
            if (!getRow(row).isValid()) {
                return false;
            }
        }

        for (int col = 0; col < 9; col++) {
            if (!getCol(col).isValid()) {
                return false;
            }
        }
        return true;
    }

    public void printBoard() {
        System.out.println("---------");
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(cells[row][col].getConfirmedNumber());
            }
            System.out.println();
        }
        System.out.println("---------");
    }

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            clone.cells = new Cell[9][9];
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    clone.cells[row][col] = new Cell(0);
                    clone.cells[row][col].setConfirmedNumber(this.cells[row][col].getConfirmedNumber());
                    clone.cells[row][col].setMemo(this.cells[row][col].getMemo());
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