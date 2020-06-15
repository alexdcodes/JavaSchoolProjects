/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pendulum;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.util.Duration;
import javafx.animation.PathTransition;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
/**
 *
 * @author sctcad
 */
public class Pendulum extends Application {
    
      @Override
    public void start(Stage primaryStage) throws Exception {

        PendulumPane pendulumPane = new PendulumPane(400, 400);

        Scene scene = new Scene(pendulumPane);
        primaryStage.setTitle("Pendulum Animation");
        primaryStage.setScene(scene);
        pendulumPane.play();
        primaryStage.show();


      scene.setOnKeyPressed((KeyEvent e)->
      {
            switch (e.getCode()) {
                case UP: pendulumPane.increase(); break;
                case DOWN: pendulumPane.decrease(); break;
            }
        });
       

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private class PendulumPane extends Pane {

        private double w = 400;
        private final double h;
        PathTransition bPath;

        Line line;
        Arc arc;
        Circle topC;
        Circle lowerC;

        PendulumPane(double width, double height) {

            w = width;
            h = height;
            setPrefWidth(w);
            setPrefHeight(h);
            arc = new Arc(w / 2, h * 0.8, w * 0.15, w * 0.15, 180, 180);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Color.BLACK);

            lowerC = new Circle(arc.getCenterX() - arc.getRadiusX(), arc.getCenterY(), 10);
            topC = new Circle(arc.getCenterX(), arc.getCenterY() - h / 2, lowerC.getRadius() / 2);
            arc = new Arc(topC.getCenterX(), topC.getCenterY(), w / 2, h / 2, 240, 60);
            line = new Line(
                    topC.getCenterX(), topC.getCenterY(),
                    lowerC.getCenterX(), lowerC.getCenterY());

            line.endXProperty().bind(lowerC.translateXProperty().add(lowerC.getCenterX()));
            line.endYProperty().bind(lowerC.translateYProperty().add(lowerC.getCenterY()));
            bPath = new PathTransition();
            bPath.setDuration(Duration.millis(4000));
                       bPath.setNode(lowerC);
            bPath.setOrientation(PathTransition.OrientationType.NONE);
            bPath.setCycleCount(PathTransition.INDEFINITE);
            bPath.setAutoReverse(true);
            bPath.setPath(arc);
 

            getChildren().addAll(lowerC, topC,line);

        }

        public void increase() {
            bPath.setRate(bPath.getCurrentRate() + 1);
        }

        public void decrease() {
            bPath.setRate(bPath.getCurrentRate() - 1);
        }
        public void play() {
            bPath.play();
        }

    }
}
