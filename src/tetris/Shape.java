/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Personal Computer
 */
public class Shape {

	private static String[][] S = {
		{".....",
			"......",
			"..00..",
			".00...",
			"....."},
		{".....",
			"..0..",
			"..00.",
			"...0.",
			"....."}};
	private static String[][] Z = {
		{
			".....",
			".....",
			".00..",
			"..00.",
			"....."},
		{".....",
			"..0..",
			".00..",
			".0...",
			"....."}
	};
	private static String[][] I = {{"..0..",
		"..0..",
		"..0..",
		"..0..",
		"....."},
	{".....",
		"0000.",
		".....",
		".....",
		"....."}};

	private static String[][] O = {{".....",
		".....",
		".00..",
		".00..",
		"....."}};

	private static String[][] J = {{".....",
		".0...",
		".000.",
		".....",
		"....."},
	{".....",
		"..00.",
		"..0..",
		"..0..",
		"....."},
	{".....",
		".....",
		".000.",
		"...0.",
		"....."},
	{".....",
		"..0..",
		"..0..",
		".00..",
		"....."}};

	private static String[][] L = {{".....",
		"...0.",
		".000.",
		".....",
		"....."},
	{".....",
		"..0..",
		"..0..",
		"..00.",
		"....."},
	{".....",
		".....",
		".000.",
		".0...",
		"....."},
	{".....",
		".00..",
		"..0..",
		"..0..",
		"....."}};

	private static String[][] T
			= {{".....",
				"..0..",
				".000.",
				".....",
				"....."},
			{".....",
				"..0..",
				"..00.",
				"..0..",
				"....."},
			{".....",
				".....",
				".000.",
				"..0..",
				"....."},
			{".....",
				"..0..",
				".00..",
				"..0..",
				"....."}};
	public static String[][][] shapes = {S, Z, I, O, J, L, T};
	public static Color[] color = {Color.SLATEGREY, Color.DARKGOLDENROD, Color.INDIANRED, Color.FORESTGREEN, Color.CADETBLUE, Color.HOTPINK, Color.SANDYBROWN};
	public String[][] shape;
	public int rotation = 0;
	ArrayList<Block> block = new ArrayList();

	public class Block extends Rectangle {

		Block(int x, int y, Color color) {
			super(x, y, Tetris.SIZE, Tetris.size);
			this.setFill(color);
		}
	}

	public Shape(int x, int y, int shapeIndex) {

		shape = shapes[shapeIndex];

		for (int line = 0; line < shape[rotation].length; line++) {
			for (int row = 0; row < shape[rotation][line].length(); row++) {
				if (shape[rotation][line].charAt(row) == '0') {
					block.add(new Block(x + line * Tetris.SIZE, y + row * Tetris.SIZE, color[shapeIndex]));

				}
			}
		}

	}

	public ArrayList<Block> getNode() {
		return block;
	}

	public void moveDown() {
		block.forEach(s -> {
			s.setTranslateY(s.getTranslateY() + Tetris.MOVE);
		});
	}

	public void moveLeft() {
		block.forEach(s -> {
			s.setTranslateX(s.getTranslateX() - Tetris.MOVE);
		});
	}

	public void moveRight() {
		block.forEach(s -> {
			s.setTranslateX(s.getTranslateX() + Tetris.MOVE);
		});
	}
}
