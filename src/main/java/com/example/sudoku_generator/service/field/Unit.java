package com.example.sudoku_generator.service.field;

import java.util.Iterator;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
class Unit implements Iterable<Cell>, Cloneable{
    // 9つのcellを用意する
    protected Cell[] unit;
    protected Unit(int[] num){
        unit = new Cell[9];
        for(int i=0;i<9;i++){
            unit[i] = new Cell(num[i]);
        }
    }

    protected Cell getCell(int num){
        return unit[num];
    }

    protected boolean unitFillEnd(){ // unitの全てのcellでconfNumが0でないならばtrueを返す
        boolean endFrag = true;
        for(Cell cell:unit){
            if(cell.getConfNum()==0){
                endFrag = false;
                break;
            }
        }
        return endFrag;
    }

    protected void excludeConfFromCand(){ // そのunitのcellで入る可能性のない数をfalseにする
        for(Cell cell:unit){
            int confNum = cell.getConfNum();
            for(Cell otherCell:unit){
                if(confNum!=otherCell.getConfNum()){
                    otherCell.setCell(confNum,false);
                }
            }
        }
        // 二つのセルについて、同じ二つの数のみを候補とする場合、それらのセル以外のセルに入る数の候補からその二つの数を外す
        for(Cell firstCell:unit){
            for(Cell secondCell:unit){
                if((firstCell!=secondCell)&&(firstCell.myEquals(secondCell))&&(firstCell.getNumOfCand()==2)){
                    for(Cell otherCell:unit){
                        if(!otherCell.myEquals(firstCell)){
                            for(int k=1;k<=9;k++){ // firstCellとsecondCell以外のセルについてfirstCellの候補の数を候補から外す
                                if(firstCell.getCell(k)){
                                    otherCell.setCell(k,false);
                                }
                            }
                        }
                    }
                }
            }
        }
        // ３つのセルについて、同じ3つの数もしくはそれらの組み合わせのみを候補とする3つのセルがあった場合、それら以外のセルに入る数の候補からその3つの数を外す。
        for(int i=1;i<=9;i++){
            for(int j=i+1;j<=9;j++){
                for(int k=j+1;k<=9;k++){
                    // 考えられる数の組み合わせは(i,j,k),(i,j),(i,k),(j,k)
                    // (i,j,k)を候補とする場合はセルの候補の数は3つ、(i,j),(i,k),(j,k)を候補とする場合はセルの候補の数は2つでないといけないため、場合分け
                    for(Cell firstCell:unit){
                        boolean firstNumOfCandEqual3,firstNumOfCandEqual2;
                        firstNumOfCandEqual3 = firstCell.getCell(i) && firstCell.getCell(j) && firstCell.getCell(k) && (firstCell.getNumOfCand() == 3);
                        firstNumOfCandEqual2 = ((firstCell.getCell(i) && firstCell.getCell(j)) || (firstCell.getCell(j) && firstCell.getCell(k)) || (firstCell.getCell(i) && firstCell.getCell(k))) && (firstCell.getNumOfCand() == 2);
                        if(firstNumOfCandEqual3 || firstNumOfCandEqual2){
                            for(Cell secondCell:unit){
                                boolean secondNumOfCandEqual3,secondNumOfCandEqual2;
                                secondNumOfCandEqual3 = secondCell.getCell(i) && secondCell.getCell(j) && secondCell.getCell(k) && (secondCell.getNumOfCand() == 3);
                                secondNumOfCandEqual2 = ((secondCell.getCell(i) && secondCell.getCell(j)) || (secondCell.getCell(j) && secondCell.getCell(k)) || (secondCell.getCell(i) && secondCell.getCell(k))) && (secondCell.getNumOfCand() == 2);
                                if((secondNumOfCandEqual3 || secondNumOfCandEqual2) && !secondCell.myEquals(firstCell)){
                                    for(Cell thirdCell:unit){
                                        boolean thirdNumOfCandEqual3,thirdNumOfCandEqual2;
                                        thirdNumOfCandEqual3 = thirdCell.getCell(i) && thirdCell.getCell(j) && thirdCell.getCell(k) && (thirdCell.getNumOfCand() == 3);
                                        thirdNumOfCandEqual2 = ((thirdCell.getCell(i) && thirdCell.getCell(j)) || (thirdCell.getCell(j) && thirdCell.getCell(k)) || (thirdCell.getCell(i) && thirdCell.getCell(k))) && (thirdCell.getNumOfCand() == 2);
                                        if((thirdNumOfCandEqual3 || thirdNumOfCandEqual2) && !thirdCell.myEquals(firstCell) && !thirdCell.myEquals(secondCell)){
                                            for(Cell otherCell:unit){
                                                if((otherCell.myEquals(firstCell)) && (otherCell.myEquals(secondCell)) && (otherCell.myEquals(thirdCell))){
                                                    otherCell.setCell(i,false);
                                                    otherCell.setCell(j,false);
                                                    otherCell.setCell(k,false);
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

    protected void setConfNumUnit(){
        for(int i=1;i<=9;i++){
            Cell onlyCell = null;
            int count=0;
            for(Cell cell:unit){
                if(cell.getCell(i)){
                    count++;
                    onlyCell=cell;
                }
            }
            if(count==1){
                onlyCell.setConfNum(i);
                for(Cell cell:unit){
                    if(!cell.myEquals(onlyCell)){
                        cell.setCell(i,false);
                    }
                }
            }
        }
    }

    /* protected void printUnit(){
        for(Cell cell:unit){
            cell.printCell();
        }
        System.out.println();
    } */

    protected boolean contains(Cell cell){
        boolean contain = false;
        for(Cell cellInThisUnit:unit){
            if(cell.myEquals(cellInThisUnit)){
                contain = true;
                break;
            }
        }
        return contain;
    }

    public boolean myEquals(Unit otherUnit) {
        boolean sameUnit = true;
        for(int i=0;i<9;i++){
            if (!this.unit[i].myEquals(otherUnit.unit[i])) {
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
            clone.unit = this.unit.clone();
            for(int i=0;i<9;i++){
                clone.unit[i] = this.unit[i].clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private class MyIterator implements Iterator<Cell> {
        int i;

        public MyIterator(){
            i=0;
        }
        @Override
        public boolean hasNext() {
            if(i<9){
                i++;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Cell next() {
            return unit[i-1];
        }
    }

    public Iterator<Cell> iterator(){
        return new MyIterator();
    }
}
