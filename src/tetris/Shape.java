/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Personal Computer
 */
public class Shape {

	private static String[][] S
			= {
				{
					".....",
					"......",
					"..00..",
					".00...",
					"....."
				},
				{
					".....",
					"..0..",
					"..00.",
					"...0.",
					"....."
				}
			};
	private static String[][] Z
			= {
				{
					".....",
					".....",
					".00..",
					"..00.",
					"....."
				},
				{
					".....",
					"..0..",
					".00..",
					".0...",
					"....."
				}
			};
	private static String[][] I
			= {
				{
					"..0..",
					"..0..",
					"..0..",
					"..0..",
					"....."
				},
				{
					".....",
					"0000.",
					".....",
					".....",
					"....."
				}
			};

	private static String[][] O
			= {
				{
					".....",
					".....",
					".00..",
					".00..",
					"....."
				}
			};

	private static String[][] J
			= {
				{
					".....",
					".0...",
					".000.",
					".....",
					"....."
				},
				{
					".....",
					"..00.",
					"..0..",
					"..0..",
					"....."
				},
				{
					".....",
					".....",
					".000.",
					"...0.",
					"....."
				},
				{
					".....",
					"..0..",
					"..0..",
					".00..",
					"....."
				}
			};

	private static String[][] L
			= {
				{
					".....",
					"...0.",
					".000.",
					".....",
					"....."
				},
				{
					".....",
					"..0..",
					"..0..",
					"..00.",
					"....."
				},
				{
					".....",
					".....",
					".000.",
					".0...",
					"....."
				},
				{
					".....",
					".00..",
					"..0..",
					"..0..",
					"....."
				}
			};

	private static String[][] T
			= {
				{
					".....",
					"..0..",
					".000.",
					".....",
					"....."
				},
				{
					".....",
					"..0..",
					"..00.",
					"..0..",
					"....."
				},
				{
					".....",
					".....",
					".000.",
					"..0..",
					"....."
				},
				{
					".....",
					"..0..",
					".00..",
					"..0..",
					"....."
				}
			};
	public static String[][][] shapes = {S, Z, I, O, J, L, T};
	public static Color[] color = {Color.SLATEGREY, Color.DARKGOLDENROD, Color.INDIANRED, Color.FORESTGREEN, Color.CADETBLUE, Color.HOTPINK, Color.SANDYBROWN};
	public String[][] shape;
	public int rotation = 0;
	public int x0, y0;
	public ArrayList<Block> block = new ArrayList();

	public class Block extends Rectangle {

		Block(int x, int y, Color color) {
			super(x, y, Tetris.SIZE, Tetris.size);
			this.setFill(color);
		}

		public void setPosition(int x, int y) {
			this.setX(x);
			this.setY(y);
		}

	}

	public void printCoordinate() {
		block.forEach(e -> {
			System.out.println("x = " + e.getX() + ", y = " + e.getY());

		});
		System.out.println("----");
	}

	private void redraw() {
		int index = 0;

		for (int line = 0; line < shape[rotation].length; line++) {
			for (int row = 0; row < shape[rotation][line].length(); row++) {
				if (shape[rotation][line].charAt(row) == '0') {
					block.get(index).setPosition(this.x0 + (line) * Tetris.SIZE, this.y0 + (row) * Tetris.SIZE);

					index++;

				}
			}
		}
		printCoordinate();

	}

	public Shape(int x, int y, int shapeIndex) {

		shape = shapes[shapeIndex];
		this.x0 = x;
		this.y0 = y;
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

	public void moveUp() {
		this.rotation = (this.rotation + 1) % shape.length;
		this.redraw();
	}

	public void moveDown() {
		int move = 1;
		for (Block s : block) {
			if (s.getY() + Tetris.MOVE < Tetris.YMAX) {
				int availableMove = Tetris.grids[(int) s.getX() / Tetris.SIZE][(int) s.getY() / Tetris.SIZE];
				if (availableMove == 0) {
					move = 1;
				}
				else {
					move = 0;
				}
			}
			else {
				move = 0;
			}
		}
		if (move == 1) {
			block.forEach((Block s) -> {
				if (this.y0 < Tetris.YMAX) {
					int availableMove = Tetris.grids[(int) s.getX() / Tetris.SIZE][(int) s.getY() / Tetris.SIZE];
					if (availableMove == 0) {
						s.setY(s.getY() + Tetris.MOVE);

					}
					else {

					}
				}

			});
			this.y0 += Tetris.MOVE;

		}
		else {

		}
	}

	public void moveLeft() {
		block.forEach(s -> {
			s.setTranslateX(s.getTranslateX() - Tetris.MOVE);
		});
		this.x0 -= Tetris.MOVE;
	}

	public void moveRight() {
		block.forEach(s -> {
			s.setTranslateX(s.getTranslateX() + Tetris.MOVE);
		});
		this.x0 += Tetris.MOVE;
	}
}
