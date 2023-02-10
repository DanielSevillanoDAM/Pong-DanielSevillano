package com.example.pong;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {

    //VARIABLES
    private static final int ancho = 800;
    private static final int alto = 600;
    private double posYJug1 = alto /2 - 50;
    private double posYJug2 = alto /2 - 50;
    private double posXBola = ancho /2; //La bola aparecerá en el centro de
    private double posYbola = ancho /2 - 100; //la pantalla
    private int puntosJ1 = 0;
    private int puntosJ2 = 0;
    private int posXJug1 = 20;
    private double posXJug2 = 760;
    private int shift = 10;

    private boolean onLUp = false;
    private boolean onLDown = false;

    private boolean onRUp = false;
    private boolean onRDown = false;

    private int yDirection = -shift;

    private int xDirection = -shift;

    @Override
    public void start(Stage stage) throws IOException {
        var scoreJ1 = new Text(Integer.toString(puntosJ1));
        scoreJ1.relocate(200,30);
        scoreJ1.setFill(Color.WHITE);
        var scoreJ2 = new Text(Integer.toString(puntosJ2));
        scoreJ2.relocate(600,30);
        scoreJ2.setFill(Color.WHITE);
        var rect1 =  new Rectangle(20, 100);
        rect1.relocate(posXJug1, posYJug1);
        rect1.setFill(Color.WHITE);
        var rect2 =  new Rectangle(20, 100);
        rect2.relocate(posXJug2, posYJug2);
        rect2.setFill(Color.WHITE);
        var circle = new Circle(10);
        circle.relocate(posXBola, posYbola);
        circle.setFill(Color.WHITE);
        var pane = new Pane(rect1, rect2, circle, scoreJ1, scoreJ2);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY) ));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(pane, ancho, alto);
        scene.setOnKeyPressed(keyEvent -> {

            switch (keyEvent.getCode())
            {
                //JUGADOR DERECHA
                case UP -> {
                    if(rect2.getY() > -250)
                    {
                        onRUp = true;
                    } else {
                        onRUp = false;
                    }
                }
                case DOWN -> {
                    if(rect2.getY() < 250)
                    {
                        onRDown = true;
                    }else {
                        onRDown = false;
                    }
                }

                //JUGADOR IZQUIERDA
                case W -> {
                    if(rect1.getY() > -250)
                    {
                        onLUp = true;
                    } else {
                        onLUp = false;
                    }
                }
                case S -> {
                    if(rect1.getY() < 250)
                    {
                        onLDown = true;
                    }else {
                        onLDown = false;
                    }
                }
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode())
            {
                //JUGADOR DERECHA
                case UP -> onRUp = false;
                case DOWN -> onRDown = false;

                //JUGADOR IZQUIERDA
                case W -> onLUp = false;
                case S -> onLDown = false;
            }
        });
        stage.setTitle("PONG");
        stage.setScene(scene);
        stage.show();

        Timeline timelinePaddle = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            rect2.setY(onRUp ? rect2.getY() : rect2.getY() + shift);
            rect2.setY(onRDown? rect2.getY() : rect2.getY() - shift);
            rect1.setY(onLUp ? rect1.getY() : rect1.getY() + shift);
            rect1.setY(onLDown ? rect1.getY() : rect1.getY() - shift);
            if(circle.getLayoutY() + shift > scene.getHeight() || circle.getLayoutY() + shift < 10)
            {
                yDirection *= -1;
            }
            if(rect1.getBoundsInParent().contains(circle.getBoundsInParent()) ||
                    rect2.getBoundsInParent().contains(circle.getBoundsInParent()))
            {
                xDirection *= -1;
            }
            if(circle.getLayoutX() + shift > scene.getWidth())
            {
                puntosJ1 += 1;
                scoreJ1.setText(String.valueOf(puntosJ1));
                circle.relocate(posXBola, posYbola);
                xDirection *= -1;
            } else if (circle.getLayoutX() + shift < 0)
            {
                puntosJ2 += 1;
                scoreJ2.setText(String.valueOf(puntosJ2));
                circle.relocate(posXBola, posYbola);
                xDirection *= -1;
            }

            circle.setLayoutX(circle.getLayoutX() + xDirection);
            circle.setLayoutY(circle.getLayoutY() + yDirection);

        }));
        timelinePaddle.setCycleCount(Animation.INDEFINITE);
        timelinePaddle.play();
    }

    public static void main(String[] args) {
        launch();
    }
}