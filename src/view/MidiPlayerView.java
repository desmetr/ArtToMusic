package view;

import controller.MidiPlayerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.sound.midi.MidiUnavailableException;

// github test

/**
 * Created by rafael on 22.10.16.
 */
public class MidiPlayerView extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("MidiPlayer");

        Button button = new Button();
        button.setText("Play");
        button.setOnAction(event -> {
            try
            {
                MidiPlayerController.playNotes();
            }
            catch (MidiUnavailableException e)  {   e.printStackTrace();    }
            catch (InterruptedException e)      {   e.printStackTrace();    }
        });

        primaryStage.setScene(new Scene(button, 600, 300));
        primaryStage.show();
    }
}
