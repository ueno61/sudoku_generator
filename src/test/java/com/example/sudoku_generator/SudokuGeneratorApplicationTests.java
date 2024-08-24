package com.example.sudoku_generator;

import com.example.sudoku_generator.service.*;
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
}
