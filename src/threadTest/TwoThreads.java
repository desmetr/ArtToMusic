package threadTest;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.sound.midi.MidiUnavailableException;

/**
 * Created by rafael on 14.10.16.
 */
public class TwoThreads extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("threadTest.TwoThreads");

        HBox hbox = new HBox(25);
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        Button buttonThread1 = new Button();
        buttonThread1.setText("Start thread 1");
        buttonThread1.setOnAction(event -> {
            try {
                (new Thread(new Thread1())).start();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        });

        Button buttonThread2 = new Button();
        buttonThread2.setText("Start thread 2");
        buttonThread2.setOnAction(event -> {
            try {
                (new Thread(new Thread2())).start();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        });

        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(buttonThread1, buttonThread2);

        StackPane root = new StackPane();
        root.getChildren().add(hbox);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
