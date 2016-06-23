package UlamSpiral;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UlamSpiralController implements Initializable {
    public static final double RADIUS = .96;
    public TextField startIndex;
    public TextField gridWidth;
    public static final Circle PRIME_CIRCLE = new Circle(1, Color.BLACK);
    public static final Circle NONPRIME_CIRCLE = new Circle(1, Color.GREY);
    public Canvas spiral;
    public ScrollPane scrollPane;
    public ProgressBar progress;
    @FXML
    public BooleanProperty spiralRunning = new SimpleBooleanProperty(false);
    public HBox progressBox;
    public Label progressMessage;
    private double scale;
    private GraphicsContext gc;
    private long maxProgress = 1;
    private long currentProgress = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void close(ActionEvent actionEvent) {
        ((Stage) spiral.getScene().getWindow()).close();
    }

    public void computePrimes(ActionEvent actionEvent) {
        spiralRunning.setValue(true);
        long start = Long.parseLong(startIndex.getText());
        long width = Long.parseLong(gridWidth.getText());

        spiral.setWidth(scrollPane.getWidth() - 40);
        spiral.setHeight(scrollPane.getHeight() - 40);
        scale = Math.min(spiral.getHeight(), spiral.getWidth()) / (width + 1);

        gc = spiral.getGraphicsContext2D();
        gc.clearRect(0, 0, spiral.getWidth(), spiral.getHeight());
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.GRAY);
        gc.setTextAlign(TextAlignment.CENTER);

        progress.setProgress(0D);
        progressBox.setVisible(true);

        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(new SpiralGenerator(this, start, width));
    }

    public void drawPoint(long point, boolean isPrime, long x, long y) {
        double xPos = x * scale;
        double yPos = y * scale;
        double fontSize = scale / 4;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (isPrime) {
                    gc.fillOval(xPos, yPos, RADIUS * scale, RADIUS * scale);
                }
                if (fontSize > 4) {
                    gc.setFont(new Font(fontSize));
                    gc.strokeText(new Long(point).toString(), xPos + scale * (RADIUS / 2D), yPos + scale * (RADIUS/2D) + fontSize / 2);
                }
            }
        });
    }

    public void setMaxProgress(long maxProgress) {
        if (maxProgress < 1) {
            throw new IllegalArgumentException("Max Progress must be greater than zero.");
        }
        this.currentProgress = 0;
        this.maxProgress = maxProgress;
    }

    public void incrementProgress() {
        currentProgress++;
        double percentCompleted = (double) currentProgress / (double) maxProgress;
        if ((percentCompleted - progress.getProgress()) > .01 ||
                progress.getProgress() > percentCompleted) {
            progress.setProgress(percentCompleted);
        }
    }

    public void spiralCompleted() {
        progressBox.setVisible(false);
        spiralRunning.setValue(false);
    }

    public void cancelPrimes(ActionEvent actionEvent) {

    }

    public void finishProgress() {
//        final Timeline timeline = new Timeline();
//        final KeyValue kv = new KeyValue(progress.progressProperty(), 100,
//                Interpolator.EASE_BOTH);
//        final KeyFrame kf = new KeyFrame(Duration.seconds(1D), kv);
//        timeline.getKeyFrames().add(kf);
//        timeline.play();
        progress.setProgress(100D);
    }

    public void setProgressMessage(String messageText) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progressMessage.setText(messageText);
            }
        });
    }
}
