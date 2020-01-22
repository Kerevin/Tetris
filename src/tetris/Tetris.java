/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.Arrays;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
	Shape s = new Shape(XMAX / 2, 100, (int) (Math.random() * 7));
	Shape nextOb = new Shape(XMAX + 25, 50, (int) (Math.random() * 7));
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
				if (now - preTime >= 500000000) {
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
		root.getChildren().addAll(s.getNode());
		root.getChildren().addAll(nextOb.getNode());
		root.setPrefSize(XMAX + 150, YMAX);

		Scene scene = new Scene(root);
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {

				case LEFT:
					s.moveLeft();
					break;
				case RIGHT:
					s.moveRight();
					break;
				case UP:
					s.moveUp();
					break;

			}
		});
		run();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tetris");

		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	void update() {
		s.moveDown();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
