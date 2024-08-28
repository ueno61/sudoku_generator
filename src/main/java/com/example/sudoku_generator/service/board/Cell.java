package com.example.sudoku_generator.service.board;

import java.util.Arrays;

@SuppressWarnings({"SameParameterValue", "BooleanMethodIsAlwaysInverted"})
class Cell implements Cloneable {
    // 1~9のそれぞれについて、入る可能性があるならtrue、ないならfalseをそれぞれに対応する番目に入れておく
    // 配列の要素数は10として、0番目は常にfalse、それ以降の1番目から９番目を使用する
    private boolean[] memo;
    // そのmemoに入る数の候補が一つの時、confirmedNumberにはその数字が入る(決まっていない時には0がはいる)
    private int confirmedNumber;

    protected Cell(int num) {
        memo = new boolean[10];
        Arrays.fill(memo, false);
        // 最初から数が入っているならその数字を、入っていないなら0を渡す
        if (num != 0) {
            memo[num] = true;
        } else {
            // 最初から数が入っていない場合、全ての数が入る可能性があるとして初期化する
            Arrays.fill(memo, true);
            memo[0] = false;
        }
        confirmedNumber = num;
    }

    protected void reconCell(Cell other) {
        memo = other.memo;
        confirmedNumber = other.confirmedNumber;
    }

    protected int getConfirmedNumber() {
        return confirmedNumber;
    }

    protected void removeCandidate(int num) {
        memo[num] = false;
    }

    protected void finalizeConfirmedNumber() {
        if (confirmedNumber != 0) return;
        int count = 0;
        int candidateNumber = 0;
        for (int memoNum = 1; memoNum <= 9; memoNum++) {
            // 入る可能性のある数字の個数をカウント
            if (memo[memoNum]) {
                count++;
                candidateNumber = memoNum;
            }
        }

        if (count == 1) {
            confirmedNumber = candidateNumber;
        } else if (count == 0) {
            System.err.println("No valid candidates found for the cell.");
        }
    }

    protected void setConfirmedNumber(int num) {
        this.confirmedNumber = num;
        for (int memoNum = 1; memoNum <= 9; memoNum++) {
            if (memoNum != num) {
                memo[memoNum] = false;
            }
        }
    }

    protected boolean[] getMemo() {
        return memo;
    }
    protected void setMemo(boolean[] memo) {
        this.memo = Arrays.copyOf(memo,10);
    }

    protected boolean isCandidate(int memoNum) {
        return memo[memoNum];
    }

    protected int getSumOfCandidates() {
        int count = 0;
        for (int memoNum = 1; memoNum <= 9; memoNum++) {
            if (memo[memoNum]) {
                count++;
            }
        }
        return count;
    }

    public boolean myEquals(Cell otherCell) {
        return Arrays.equals(this.memo, otherCell.memo) && (this.confirmedNumber == otherCell.confirmedNumber);
    }

    @Override
    public Cell clone() {
        try {
            Cell clone = (Cell) super.clone();
            clone.memo = Arrays.copyOf(this.memo,10);
            clone.confirmedNumber = this.confirmedNumber;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
