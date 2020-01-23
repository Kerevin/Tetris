/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

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
					".....",
					"0000.",
					".....",
					".....",
					"....."
				},
				{
					"..0..",
					"..0..",
					"..0..",
					"..0..",
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
	public static Color[] colorTable = {Color.SLATEGREY, Color.DARKGOLDENROD, Color.INDIANRED, Color.FORESTGREEN, Color.CADETBLUE, Color.HOTPINK, Color.SANDYBROWN};
	public Color color;
	public String[][] shape;
	public int rotation = 0;
	public int x0, y0;
	public ArrayList<Block> block = new ArrayList();
	public boolean isPlaced = false; // check if block is placed or not.

	public class Block extends Rectangle {

		Block(int x, int y, Color color) {
			super(x, y, Tetris.SIZE - 2, Tetris.SIZE - 2);
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
		int[][] newPosition = new int[block.size()][2];

		boolean isMove = true;

		// Check if turning pose is valid 
		// There is still a space to turn
		for (int line = 0; line < shape[rotation].length; line++) {
			for (int row = 0; row < shape[rotation][line].length(); row++) {
				if (shape[rotation][line].charAt(row) == '0') {
					int x = this.x0 + (line) * Tetris.SIZE;
					int y = this.y0 + (row) * Tetris.SIZE;
					if (x < Tetris.XMAX && x >= 0 && y < Tetris.YMAX && y >= 0 && Tetris.grids[(int) x / Tetris.SIZE][(int) y / Tetris.SIZE] == 0) {
						newPosition[index][0] = x;
						newPosition[index][1] = y;
						index++;
					}
					else {

						isMove = false;
						break;
					}
				}
			}
		}
		if (isMove) {
			// Turning to another pose //
			for (int i = 0; i < index; i++) {
				block.get(i).setPosition(newPosition[i][0], newPosition[i][1]);
			}
		}
	}

	public Shape(int x, int y, int shapeIndex) {

		this.shape = shapes[shapeIndex];
		this.x0 = x;
		this.y0 = y;
		this.color = colorTable[shapeIndex];
		for (int line = 0; line < shape[rotation].length; line++) {
			for (int row = 0; row < shape[rotation][line].length(); row++) {
				if (shape[rotation][line].charAt(row) == '0') {
					block.add(new Block(x + line * Tetris.SIZE, y + row * Tetris.SIZE, color));

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
		boolean move = true;
		for (Block s : block) {
			// Check if there is still an available move //
			if (s.getY() + Tetris.MOVE < Tetris.YMAX) {
				int availableMove = Tetris.grids[(int) s.getX() / Tetris.SIZE][((int) s.getY() / Tetris.SIZE) + 1];
				if (availableMove == 1) {
					move = false;
					break;
				}
			}
			else {
				move = false;
				break;
			}
		}
		// If there is a space to move
		if (move == true) {
			block.forEach((Block s) -> {
				s.setY(s.getY() + Tetris.MOVE);
			});
			this.y0 += Tetris.MOVE;

		}
		else {
			// Make a move 
			block.forEach((Block s) -> {
				Tetris.grids[(int) s.getX() / Tetris.SIZE][((int) s.getY() / Tetris.SIZE)] = 1;

			});
			this.isPlaced = true;
		}
	}

	public void moveLeft() {
		boolean move = true;
		for (Block s : block) {
			// Check if there is still an available move //
			if (s.getX() - Tetris.MOVE >= 0) {
				try {
					int availableMove = Tetris.grids[((int) s.getX() / Tetris.SIZE) - 1][(int) s.getY() / Tetris.SIZE];
					if (availableMove == 1) {
						move = false;
						break;
					}
				} catch (Exception e) {
					System.out.println("moving left error");
					System.out.println(s.getX() + " " + s.getY());
				}

			}
			else {
				move = false;
				break;
			}
		}
		if (move) {
			block.forEach(s -> {
				s.setX(s.getX() - Tetris.MOVE);
			});
			this.x0 -= Tetris.MOVE;
		}

	}

	public void moveRight() {
		boolean move = true;
		for (Block s : block) {
			// Check if there is still an available move //
			if (s.getX() + Tetris.MOVE <= Tetris.XMAX - Tetris.SIZE) {
				try {
					int availableMove = Tetris.grids[((int) s.getX() / Tetris.SIZE) + 1][(int) s.getY() / Tetris.SIZE];
					if (availableMove == 1) {
						move = false;
						break;
					}
				} catch (Exception e) {
					System.out.println("moving right error");
					System.out.println(s.getX() + " " + s.getY());
				}

			}
			else {
				move = false;
				break;
			}
		}
		if (move) {
			block.forEach(s -> {
				s.setX(s.getX() + Tetris.MOVE);
			});
			this.x0 += Tetris.MOVE;
		}

	}
}
