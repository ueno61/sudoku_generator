package com.example.sudoku_generator.service.board;

import java.util.Arrays;

@SuppressWarnings({"SameParameterValue", "BooleanMethodIsAlwaysInverted"})
class Cell implements Cloneable{
    // 1~9のそれぞれについて、入る可能性があるならtrue、ないならfalseをそれぞれに対応する番目に入れておく
    // 配列の要素数は10として、0番目は常にfalse、それ以降の1番目から９番目を使用する
    private boolean[] memo;
    // そのmemoに入る数の候補が一つの時、confNumにはその数字が入る(決まっていない時には0がはいる)
    private int confNum;
    
    protected Cell(int num){
        memo = new boolean[10];
        Arrays.fill(memo,false);
        // 最初から数が入っているならその数字を、入っていないなら0を渡す
        if(num != 0){
            memo[num] = true;
        } else {
            // 最初から数が入っていない場合、全ての数が入る可能性があるとして初期化する
            Arrays.fill(memo,true);
            memo[0] = false;
        }
        confNum = num;
    }

    protected void setCell(int num, boolean bool){
        memo[num] = bool;
    }

    protected void reconCell(Cell other){
        memo = other.memo;
        confNum = other.confNum;
    }

    protected boolean getCell(int num){
        return memo[num];
    }

    protected int getConfNum(){
        return confNum;
    }

    protected void setConfNum(){
        for(int i=1;i<=9;i++){
            /* confNumとmemoのtrueとなっているところが一致しているならok、
            なっていないかつconfNumが0ならtrueとなっている数字を入れる
            なっていないかつconfNumが0でないならconfNumを0にしてbreak
            */
            if(memo[i]&&confNum==i) return;
            else if (memo[i]&&confNum==0) {
                confNum = i;
            }
            else if (memo[i]&&confNum!=i) {
                confNum = 0;
                return;
            }
        }
    }

    protected void setConfNum(int num){
        this.confNum = num;
        for(int i=1;i<=9;i++){
            if(i!=num){
                memo[i]=false;
            }
        }
    }

    protected int getNumOfCand(){
        int count=0;
        for(int i=1;i<=9;i++){
            if(memo[i]){
                count++;
            }
        }
        return count;
    }

    /*protected void printCell(){
        System.out.print(confNum+" ");
    }*/

    public boolean myEquals(Cell otherCell) {
        return Arrays.equals(this.memo,otherCell.memo)&&(this.confNum==otherCell.confNum);
    }

    @Override
    public Cell clone() {
        try {
            Cell clone = (Cell) super.clone();
            clone.memo = this.memo.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

