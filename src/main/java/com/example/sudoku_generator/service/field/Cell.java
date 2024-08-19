package com.example.sudoku_generator.service.field;

import java.util.Arrays;

@SuppressWarnings({"SameParameterValue", "BooleanMethodIsAlwaysInverted"})
class Cell implements Cloneable{
    // 1~9のそれぞれについて、入る可能性があるならtrue、ないならfalseをそれぞれに対応する番目に入れておく
    // 配列の要素数は10として、0番目は常にfalse、それ以降の1番目から９番目を使用する
    private boolean[] cell;
    // そのcellに入る数の候補が一つの時、confNumにはその数字が入る(決まっていない時には0がはいる)
    private int confNum;
    protected Cell(int num){
        cell = new boolean[10];
        Arrays.fill(cell,false);
        // 最初から数が入っているならその数字を、入っていないなら0を渡す
        if(num != 0){
            cell[num] = true;
        } else {
            // 最初から数が入っていない場合、全ての数が入る可能性があるとして初期化する
            Arrays.fill(cell,true);
            cell[0] = false;
        }
        confNum = num;
    }

    protected void setCell(int num, boolean bool){
        cell[num] = bool;
    }

    protected void reconCell(Cell other){
        cell = other.cell;
        confNum = other.confNum;
    }

    protected boolean getCell(int num){
        return cell[num];
    }

    protected int getConfNum(){
        return confNum;
    }

    protected void setConfNum(){
        for(int i=1;i<=9;i++){
            /* confNumとcellのtrueとなっているところが一致しているならok、
            なっていないかつconfNumが0ならtrueとなっている数字を入れる
            なっていないかつconfNumが0でないならconfNumを0にしてbreak
            */
            if(cell[i]&&confNum==i) return;
            else if (cell[i]&&confNum==0) {
                confNum = i;
            }
            else if (cell[i]&&confNum!=i) {
                confNum = 0;
                return;
            }
        }
    }

    protected void setConfNum(int num){
        this.confNum = num;
        for(int i=1;i<=9;i++){
            if(i!=num){
                cell[i]=false;
            }
        }
    }

    protected int getNumOfCand(){
        int count=0;
        for(int i=1;i<=9;i++){
            if(cell[i]){
                count++;
            }
        }
        return count;
    }

    /*protected void printCell(){
        System.out.print(confNum+" ");
    }*/

    public boolean myEquals(Cell otherCell) {
        return Arrays.equals(this.cell,otherCell.cell)&&(this.confNum==otherCell.confNum);
    }

    @Override
    public Cell clone() {
        try {
            Cell clone = (Cell) super.clone();
            clone.cell = this.cell.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

