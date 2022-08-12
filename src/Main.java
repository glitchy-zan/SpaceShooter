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

public class Main extends Application {

    Stage window;
    Canvas canvas;
    GraphicsContext gc;
    Timeline tl;

    private static final int WIDTH = 420;
    private static final int HEIGHT = 420;

    private double playerXValue = WIDTH / 2;
    private double playerYValue = HEIGHT / 2;

    private ArrayList<Fire> fires = new ArrayList<>();

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

        window.setScene(scene);
        window.setResizable(false);
        window.show();

        tl.play();
    }

    public void draw(GraphicsContext gc) {
        // set up
        double[] xPointsShip = {playerXValue, playerXValue + 25, playerXValue - 25, playerXValue};
        double[] yPointsShip = {playerYValue - 20, playerYValue + 20, playerYValue + 20, playerYValue - 20};

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
    }
}
