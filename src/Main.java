import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    Stage window;
    Canvas canvas;
    GraphicsContext gc;
    Timeline tl;

    private static final int WIDTH = 420;
    private static final int HEIGHT = 420;

    private double playerXValue = WIDTH / 2;
    private double playerYValue = HEIGHT / 2;

    private int numberOfEnemies = 10;
    private int score = 0;

    private ArrayList<Fire> fires = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Space Shooter");

        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        tl = new Timeline(new KeyFrame(Duration.millis(10), e -> draw(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        StackPane layout = new StackPane(canvas);

        Scene scene = new Scene(layout);
        scene.setCursor(Cursor.NONE);
        scene.setOnMouseMoved(e -> {
            playerXValue = e.getX();
            playerYValue = e.getY();
        });
        scene.setOnMouseDragged(e -> {
            playerXValue = e.getX();
            playerYValue = e.getY();
        });
        scene.setOnMouseClicked(e -> {
            fires.add(new Fire(playerXValue, playerYValue - 20, 3));
        });

        // add starting enemies
        for (int i = 0; i < numberOfEnemies; i++) {
            enemies.add(new Enemy(new Random().nextDouble(WIDTH), 20));
        }

        window.setScene(scene);
        window.setResizable(false);
        window.show();

        tl.play();
    }

    public void draw(GraphicsContext gc) {
        // set up
        double[] xPointsShip = {playerXValue, playerXValue + 25, playerXValue - 25, playerXValue};
        double[] yPointsShip = {playerYValue - 20, playerYValue + 20, playerYValue + 20, playerYValue - 20};

        // add enemies if needed
        if (enemies.size() < 10) enemies.add(new Enemy(new Random().nextDouble(WIDTH), 20));

        // background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // ship
        gc.setFill(Color.RED);
        gc.fillPolygon(xPointsShip, yPointsShip, 4);

        // fire
        try {
            for (Fire fire : fires) {
                // draw fire
                gc.setFill(Color.YELLOW);
                gc.fillOval(fire.getxValue(), fire.getyValue(), 5, 5);
                // move fire
                fire.setyValue(fire.getyValue() - fire.getSpeed());
                // remove from arr when out
                if (fire.getyValue() <= 0) fires.remove(0);
            }
        } catch (Exception e) {
        }

        // enemies

        for (Enemy enemy : enemies) {
            // draw enemies
            gc.setFill(Color.GREEN);
            gc.fillRect(enemy.getxValue() - 10, enemy.getyValue() - 10, 20, 20);
            // move enemies
            enemy.setxValue((enemy.getxValue() < playerXValue) ? enemy.getxValue() + 0.1 : enemy.getxValue() - 0.1);
            enemy.setyValue(enemy.getyValue() + 0.1);
        }

        // destroying enemies
        try {
            for (Fire fire : fires) {
                for (Enemy enemy : enemies) {
                    if (fire.getxValue() >= enemy.getxValue() - 10 && fire.getxValue() <= enemy.getxValue() + 10 && fire.getyValue() <= enemy.getyValue() + 10 && fire.getyValue() >= enemy.getyValue() - 10) {
                        enemies.remove(enemy);
                        score++;
                    }
                }
            }
        } catch (Exception e) {
        }

        // score
        gc.setFill(Color.WHITE);
        gc.fillText(String.valueOf("score: " + score), 20, 400);
    }
}
