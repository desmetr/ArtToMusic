package view;

import java.util.Vector;

import javax.sound.midi.MidiUnavailableException;

import controller.ArtToMusicController;
import controller.music.MidiPlayer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.graphics.MathManager;
import model.music.MusicData;
import model.music.Note;
import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * Created by rafael on 22.10.16.
 */
public class ArtToMusicView extends Application
{
	private Scene scene;
	private MathManager mathManager;
	private MidiPlayer midiPlayer;
	
	@FXML private ToggleGroup graphicsGroup;
	@FXML private ToggleGroup musicGroup;
	
	@FXML 
	private void initialize()
	{
		try 
		{
			mathManager = new MathManager();
			midiPlayer = new MidiPlayer();
		} 
		catch (InterruptedException e) 		{	e.printStackTrace();	} 
		catch (MidiUnavailableException e) 	{	e.printStackTrace();	}
	}
	
	@FXML
	protected void onGraphicalAnalysisClicked() throws InterruptedException
	{
		ArtToMusicLogger.getInstance().info("Graphical Analysis button clicked.");
		
		String radioButtonText = ((RadioButton) graphicsGroup.getSelectedToggle()).getText();
				
		switch (radioButtonText)
		{
			case "Frei Chen":
				mathManager.edgeDetection("Frei_chen");
				break;
			case "Roberts Cross":
				mathManager.edgeDetection("Roberts_cross");
				break;
			default:
				mathManager.edgeDetection(radioButtonText);
				break;
		}
	}
	
	@FXML
	protected void onMusicGenerationClicked() throws MidiUnavailableException, InterruptedException
	{
		ArtToMusicLogger.getInstance().info("Music Generation button clicked.");
		
		String radioButtonText = ((RadioButton) musicGroup.getSelectedToggle()).getText();
		
		switch (radioButtonText)
		{
			case "Melody Sample":
		        midiPlayer.playNotes(120, MusicData.generate(Globals.getInstance().pathToMusic + "MelodySample.xml"));
		        break;
			case "Rhythm Sample":
				midiPlayer.playNotes(120, MusicData.generate(Globals.getInstance().pathToMusic + "RhythmSample.xml"));
				break;
			case "Combined Sample":
				midiPlayer.playNotes(120, MusicData.generate(Globals.getInstance().pathToMusic + "CombinedSample.xml"));
				break;
		}
	}
	
    @Override
    public void start(Stage primaryStage) throws Exception
    {
    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/view/ArtToMusicView.fxml"));
    	scene = new Scene(root);
    
        primaryStage.setTitle("MidiPlayer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args)
	{
    	ArtToMusicLogger.getInstance().info("In constructor of Main.");
		launch(args);
		new ArtToMusicController();
	}
}
