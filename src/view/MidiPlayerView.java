package view;

import javax.sound.midi.MidiUnavailableException;

import controller.MidiPlayerController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utilities.ArtToMusicLogger;

/**
 * Created by rafael on 22.10.16.
 */
public class MidiPlayerView extends Application
{
	@FXML private Button startButton;
	
	@FXML 
	private void initialize()
	{
		
	}
	
	@FXML
	protected void onStartButtonClicked() throws MidiUnavailableException, InterruptedException
	{
		ArtToMusicLogger.getInstance().info("Start button clicked.");
		MidiPlayerController.playNotes();
	}
	
    @Override
    public void start(Stage primaryStage) throws Exception
    {
    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/view/ArtToMusicView.fxml"));
    	
        primaryStage.setTitle("MidiPlayer");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();
    }
    
    public static void main(String[] args)
	{
    	ArtToMusicLogger.getInstance().info("In constructor of Main.");
        MidiPlayerController midiPlayerController = new MidiPlayerController();
		launch(args);
	}
}
