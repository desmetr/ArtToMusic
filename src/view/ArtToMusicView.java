package view;

import java.io.File;

import javax.sound.midi.MidiUnavailableException;

import controller.ArtToMusicController;
import controller.music.MidiPlayer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.graphics.RManager;
import model.music.MusicData;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Pitch;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Noise;
import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * Created by rafael on 22.10.16.
 */
public class ArtToMusicView extends Application
{
	private Scene scene;
	private RManager rManager;
	private MidiPlayer midiPlayer;
	
	@FXML private ToggleGroup graphicsGroup;
	@FXML private ToggleGroup musicGroup;
	
	@FXML 
	private void initialize()
	{
		try 
		{
			rManager = new RManager();
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
				rManager.edgeDetection("Frei_chen");
				break;
			case "Roberts Cross":
				rManager.edgeDetection("Roberts_cross");
				break;
			case "Color Analysis":
				rManager.colorAnalysis();
//				MusicData.analyseRGB();
				break;
			default:
				rManager.edgeDetection(radioButtonText);
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
				midiPlayer.chooseDrums();
				midiPlayer.playNotes(120, MusicData.generate(Globals.getInstance().pathToMusic + "RhythmSample.xml"));
				midiPlayer.setMidiChannel(0);
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
    	
		AudioContext ac;
		ac = new AudioContext();
		Noise n = new Noise(ac);
		Gain g = new Gain(ac, 1, 0.1f);
		g.addInput(n);
		ac.out.addInput(g);
		ac.start();
		
		System.out.println(Pitch.mixolydian[2]);
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Image");
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
    	File selectedFile = fileChooser.showOpenDialog(primaryStage);
    	if (selectedFile != null) 
    	    Globals.imageName = selectedFile.getName();
    
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
