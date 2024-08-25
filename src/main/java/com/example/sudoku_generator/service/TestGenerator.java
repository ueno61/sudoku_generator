package com.example.sudoku_generator.service;

import com.example.sudoku_generator.service.board.Board;

public class TestGenerator extends Generator {

    public TestGenerator(int difficult) {
        super(difficult);
    }

    public void test1() {
        test1(this.board);
    }
    private void test1(Board board) {
        board = new Board();
        board.setNumber(1,1,9);
        System.out.println("test1:in function");
        board.printBoard();
    }

    public void test2() {
        test2(this.board);
    }

    private void test2(Board board) {
        // setNumberはboardのメンバ変数cellsの一つをを新たに作成したCellクラスで置き換えている
        board.setNumber(2,2,9);
        System.out.println("test2:in function");
        board.printBoard();
    }
}
