/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tetris.Shape.Block;

/**
 *
 * @author Personal Computer
 */
public class Tetris extends Application {

	public static int size = 25;
	public static final int MOVE = 25;
	public static final int SIZE = 25;
	public static int XMAX = SIZE * 12;
	public static int YMAX = SIZE * 24;
	public static int[][] grids = new int[XMAX / SIZE][YMAX / SIZE];
	public static Pane root = new Pane();
	public static Text scoreText;
	private static int top = 0;
	public static int score = 0;

	ArrayList<Rectangle> next;
	Shape currentShape = new Shape(XMAX / 4, -25, (int) (Math.random() * 10) % 7);
	Shape nextShape = new Shape(XMAX / 4, -25, (int) (Math.random() * 10) % 7);

	public static long preTime = 0;

	private void createGrid() {
		for (int[] grid : grids) {
			Arrays.fill(grid, (int) 0);
		}
	}

	public void run() {

		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (now - preTime >= 400000000) {
					// Fall down every 0.4 seconds
					update();
					preTime = now;
				}

			}

		};

		timer.start();
	}

	@Override
	public void start(Stage primaryStage) {
		createGrid();
		Line line = new Line(XMAX, 0, XMAX, YMAX);

		scoreText = new Text("Score: ");
		scoreText.setStyle("-fx-font: 20 arials;");
		scoreText.setX(XMAX + 5);
		scoreText.setY(50);

		next = getNextShape(XMAX + 25, 100, nextShape.shape, nextShape.color);
		root.getChildren().addAll(line, scoreText);
		root.getChildren().addAll(currentShape.getNode());
		root.getChildren().addAll(next);
		root.setPrefSize(XMAX + 150, YMAX);

		Scene scene = new Scene(root);
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case LEFT:
					currentShape.moveLeft();
					break;
				case RIGHT:
					currentShape.moveRight();
					break;
				case UP:
					currentShape.moveUp();
					break;
				case DOWN:
					currentShape.moveDown();
					score += 1;
					break;
			}
		});
		run();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tetris");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	List<Block> getBlocks() {
		return root.getChildren().stream().filter(n -> n instanceof Block).map(n -> (Block) n).collect(Collectors.toList());
	}

	void removeRows() {
		ArrayList<Integer> lines = new ArrayList();
		ArrayList<Node> removedBlocks = new ArrayList();
		for (int i = 0; i < grids[0].length; i++) {
			int full = 0;
			for (int j = 0; j < grids.length; j++) {
				if (grids[j][i] == 1) {
					full++;
				}
				if (full == grids.length) {
					lines.add(i);
					for (Node node : root.getChildren()) {
						if (node instanceof Block) {
							Block a = (Block) node;
							if ((int) a.getY() == (i * SIZE)) {
								removedBlocks.add(node);

							}
						}
					}

				}
			}
		}
		removedBlocks.forEach(e -> {
			Block a = (Block) e;
			grids[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
			root.getChildren().remove(e);

		});
		score += lines.size() * 5;
		while (lines.size() > 0) {
			getBlocks().forEach(b -> {
				if (b.getY() < lines.get(0) * SIZE) {
					grids[(int) b.getX() / SIZE][(int) b.getY() / SIZE] = 0;
					b.setY(b.getY() + MOVE);

				}
			});
			lines.remove(0);

		}
		getBlocks().forEach(b -> {
			grids[(int) b.getX() / SIZE][(int) b.getY() / SIZE] = 1;
		});
	}

	void update() {
		currentShape.moveDown();
		scoreText.setText("Score: " + score);
		getBlocks().forEach(b -> {
			if ((int) b.getY() < Tetris.MOVE) {
				this.top++;

			}

		});
		if (this.top > 1) {

			Text over = new Text("Game Over");
			over.setFill(Color.RED);
			over.setFont(new Font("Arial", 70));
			over.setY(250);
			over.setX(10);
			root.getChildren().add(over);

		}
		else {
			this.top = 0;
		}
		if (top == 15) {
			System.exit(0);
		}
		if (currentShape.isPlaced == true) {
			removeRows();
			Shape a = nextShape;
			root.getChildren().removeAll(next);
			nextShape = new Shape(XMAX / 4, -25, (int) (Math.random() * 10) % 7);

			next = getNextShape(XMAX + 25, 100, nextShape.shape, nextShape.color);

			currentShape = a;
			root.getChildren().addAll(a.getNode());
			root.getChildren().addAll(next);

		}
	}

	public ArrayList<Rectangle> getNextShape(int x, int y, String[][] shape, Color color) {
		ArrayList<Rectangle> block = new ArrayList<Rectangle>();
		for (int line = 0; line < shape[0].length; line++) {
			for (int row = 0; row < shape[0][line].length(); row++) {
				if (shape[0][line].charAt(row) == '0') {
					Rectangle rec = new Rectangle(x + line * SIZE, y + row * SIZE, SIZE - 2, SIZE - 2);
					rec.setFill(color);
					block.add(rec);

				}
			}
		}
		return block;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
