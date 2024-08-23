package com.example.sudoku_generator.service.board;

import java.util.ArrayList;
import java.util.List;

public class Board implements Cloneable{
    // たて、よこ、正方形で探索できるようにしたい
    // square,row,colを9つずつ持つようにする
    Unit[] squares,rows,cols;

    public void initBoard(){
        
    }

    public boolean wholeFillEnd(){  // Boardの全てのセルでconfNumが0じゃないならtrueを返す
        boolean endFrag = true;
        for(Unit square:squares){
            if(!square.unitFillEnd()){ // どれか一つのsquareが埋まっていなかったらfalseを返す
                endFrag = false;
                break;
            }
        }
        return endFrag;
    }

    public void excludeConfFromCand(){
        for(Unit square:squares){
            square.excludeConfFromCand();
        }
        for(Unit row:rows){
            row.excludeConfFromCand();
        }
        for(Unit col:cols){
            col.excludeConfFromCand();
        }
        matchCells();
        // unitの中で、同じsquare,row,colの中にしかない数がある場合、それらの数を同じsquare,row,colの他のセルの候補から消す。
        for(int cand=1;cand<=9;cand++){
            for(int i=0;i<9;i++){
                List<Integer> rowList = new ArrayList<>();
                List<Integer> colList = new ArrayList<>();
                for(int rowNum=0;rowNum<3;rowNum++){
                    for(int colNum=0;colNum<3;colNum++){
                        // i番目のsquareに含まれる行は(i/3)*3から((i/3)*3+2)まで
                        // i番目のsquareに含まれる行は(i%3)*3から((i%3)*3+2)まで
                        if(squares[i].cells[3*rowNum+colNum].getCell(cand)){
                            if(!rowList.contains((i/3)*3+rowNum)){
                                rowList.add((i/3)*3+rowNum);
                            }
                            if(!colList.contains((i%3)*3+colNum)){
                                colList.add((i%3)*3+colNum);
                            }
                        }
                    }
                }
                if(rowList.size()==1) {
                    for (int colNum = 0; colNum < 9; colNum++) {
                        Cell targetCell = rows[rowList.get(0)].getCell(colNum);
                        if(!squares[i].contains(targetCell)){
                            targetCell.setCell(cand,false);
                        }
                    }
                }
                if(colList.size()==1) {
                    for (int rowNum = 0; rowNum < 9; rowNum++) {
                        Cell targetCell = cols[colList.get(0)].getCell(rowNum);
                        if(!squares[i].contains(targetCell)){
                            targetCell.setCell(cand,false);
                        }
                    }
                }
            }
        }
        matchCells();
    }

    protected void matchCells(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Cell cellFromSqr = getCellFromSqr(i,j);
                Cell cellFromRow = getCellFromRow(i,j);
                Cell cellFromCol = getCellFromCol(i,j);
                for(int k=1;k<=9;k++){
                    if(!cellFromCol.getCell(k)){
                        cellFromSqr.setCell(k,false);
                    }
                    if (!cellFromRow.getCell(k)){
                        cellFromSqr.setCell(k,false);
                    }
                    cellFromRow.reconCell(cellFromSqr);
                    cellFromCol.reconCell(cellFromSqr);
                }
            }
        }
    }

    protected Cell getCellFromSqr(int row, int col){ // 元の配列での位置を渡す
        // numbs[row][col]は[row/3]行[col/3]列のsquareの(row%3)行(col%3)列に格納されている
        return squares[(row/3)*3+(col/3)].getCell((row%3)*3+(col%3));
    }

    protected Cell getCellFromRow(int row, int col){
        // numbs[row][col]は[row]番目のrowのcol番目に格納されている
        return rows[row].getCell(col);
    }

    protected Cell getCellFromCol(int row, int col){
        // numbs[row][col]は[col]番目のcolのrow番目に格納されている
        return cols[col].getCell(row);
    }

    /* public void printRows(){
        for(Unit row:rows){
            row.printUnit();
        }
    } */

    /* public void printCols(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                cols[j].unit[i].printCell();
            }
            System.out.println();
        }
    } */

    public void setBoard(){
        for(Unit square:squares){
            for(Cell cell:square){
                cell.setConfNum();
            }
            square.setConfNumUnit();
        }
        for(Unit row:rows){
            for(Cell cell:row){
                cell.setConfNum();
            }
            row.setConfNumUnit();
        }
        for(Unit col:cols){
            for(Cell cell:col){
                cell.setConfNum();
            }
            col.setConfNumUnit();
        }
        matchCells();
    }

    /*public void printSquares(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    for(int l=0;l<3;l++){
                        squares[3*i+k].unit[3*j+l].printCell();
                    }
                }
                System.out.println();
            }
        }
    }*/

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            clone.squares = this.squares.clone();
            for(int i=0;i<9;i++){
                clone.squares[i] = this.squares[i].clone();
            }
            clone.rows = this.rows.clone();
            for(int i=0;i<9;i++){
                clone.rows[i] = this.rows[i].clone();
            }
            clone.cols = this.cols.clone();
            for(int i=0;i<9;i++){
                clone.cols[i] = this.cols[i].clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public boolean myEquals(Board otherBoard) {
        boolean sameSqr = true;
        if(otherBoard.squares!=null) {
            for (int i = 0; i < 9; i++) {
                if (!this.squares[i].myEquals(otherBoard.squares[i])) {
                    sameSqr = false;
                    break;
                }
            }
        } else {
            sameSqr = false;
        }
        return sameSqr;
    }
}