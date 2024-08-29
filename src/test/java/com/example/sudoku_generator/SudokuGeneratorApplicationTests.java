package com.example.sudoku_generator;

import com.example.sudoku_generator.service.*;
import com.example.sudoku_generator.service.board.Board;
import com.example.sudoku_generator.service.technique.TechniqueManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SudokuGeneratorApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test1() {
        TestGenerator generator1 = new TestGenerator(1);
        System.out.println("before test1");
        generator1.printBoard();
        generator1.test1();
        System.out.println("after test1");
        generator1.printBoard();
        TestGenerator generator2 = new TestGenerator(1);
        System.out.println("before test2");
        generator2.printBoard();
        generator2.test2();
        System.out.println("after test2");
        generator2.printBoard();
    }

    @Test
    void generateTest() {
        Generator generator = new Generator(1);
        generator.generateProblem();
        generator.printBoard();
    }

    @Test
    void checkClone() {
        Board board = new Board();
        board.setNumber(3, 3, 3);
        board.printBoard();
        Board copy = board.clone();
        copy.setNumber(1, 1, 1);
        board.printBoard();
        copy.printBoard();
    }

    @Test
    void tech3() {
        int[][] boardInt = {
                {0, 2, 0, 0, 7, 8, 1, 6, 3},
                {3, 7, 8, 1, 6, 9, 2, 4, 5},
                {0, 0, 1, 0, 0, 0, 8, 7, 9},
                {0, 4, 0, 8, 1, 3, 0, 9, 0},
                {0, 0, 3, 2, 9, 5, 4, 1, 0},
                {0, 0, 0, 7, 4, 6, 0, 3, 0},
                {0, 5, 4, 0, 0, 0, 9, 8, 0},
                {0, 3, 2, 9, 8, 0, 0, 5, 4},
                {8, 9, 7, 6, 5, 4, 3, 2, 1}
        };

        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board.setNumber(row, col, boardInt[row][col]);
            }
        }

        System.out.println("board");
        board.printBoard();
        System.out.println("copy");
        board.clone().printBoard();

        Board copy1 = board.clone();
        Board copy3 = board.clone();
        Solver lev1Solver = new Solver(1);
        Solver lev3solver = new Solver(3);
        System.out.println("level1");
        lev1Solver.solve(copy1).printBoard();
        System.out.println("level3");
        lev3solver.solve(copy3).printBoard();
    }

    @Test
    void checkBackTracking() {
        Generator generator = new Generator(1);
        generator.generateProblem();
    }

    @Test
    void solveTest() {
        int[][] boardInt = {
                {0, 2, 0, 0, 7, 8, 1, 6, 3},
                {3, 7, 8, 1, 6, 9, 2, 4, 5},
                {0, 0, 1, 0, 0, 0, 8, 7, 9},
                {0, 4, 0, 8, 1, 3, 0, 9, 0},
                {0, 0, 3, 2, 9, 5, 4, 1, 0},
                {0, 0, 0, 7, 4, 6, 0, 3, 0},
                {0, 5, 4, 0, 0, 0, 9, 8, 0},
                {0, 3, 2, 9, 8, 0, 0, 5, 4},
                {8, 9, 7, 6, 5, 4, 3, 2, 1}
        };

        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board.setNumber(row, col, boardInt[row][col]);
            }
        }
        Solver solver = new Solver(1);
        solver.solve(board);
    }

    @Test
    void checkTechnique() {
        TechniqueManager techniqueManager = new TechniqueManager(1);
        int[][] boardInt = {
                {0, 2, 0, 0, 7, 8, 1, 6, 3},
                {3, 7, 8, 1, 6, 9, 2, 4, 5},
                {0, 0, 1, 0, 0, 0, 8, 7, 9},
                {0, 4, 0, 8, 1, 3, 0, 9, 0},
                {0, 0, 3, 2, 9, 5, 4, 1, 0},
                {0, 0, 0, 7, 4, 6, 0, 3, 0},
                {0, 5, 4, 0, 0, 0, 9, 8, 0},
                {0, 3, 2, 9, 8, 0, 0, 5, 4},
                {8, 9, 7, 6, 5, 4, 3, 2, 1}
        };

        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board.setNumber(row, col, boardInt[row][col]);
            }
        }
        board = techniqueManager.applyTechniques(board);
        board.printBoard();
    }
}
