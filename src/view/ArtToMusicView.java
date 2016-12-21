package view;

import java.io.File;

import controller.ArtToMusicController;
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
import model.Data;
import model.graphics.RManager;
import model.music.BeadsManager;
import model.music.Chord;
import model.music.MusicData;
import net.beadsproject.beads.data.Buffer;
import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * The start of the ArtToMusic JavaFX application.
 * 
 * @author rafael
 * @version 1.0
 * @since 2016.11.01.
 */
public class ArtToMusicView extends Application
{
	private Scene scene;
	private RManager rManager;
	private BeadsManager beadsManager;
	
	@FXML private ToggleGroup graphicsGroup;
	@FXML private ToggleGroup musicGroup;
	
	/**
	 * Initializes all the private objects of this class.
	 * FXML annotation.
	 */
	@FXML 
	private void initialize()
	{
		try 
		{
			rManager = new RManager();
			beadsManager = new BeadsManager();
			
			Data.readMidiNoteNumbers();
			Data.readNoteFrequencies();
		} 
		catch (InterruptedException e) 		{	e.printStackTrace();	} 
	}
	
	/**
	 * Triggers the right edge detection algorithm, chosen by the user.
	 * FXML annotation.
	 * 
	 * @throws InterruptedException
	 */
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
				MusicData.analyseRGB();
				break;
			default:
				rManager.edgeDetection(radioButtonText);
				break;
		}
	}
	
	/**
	 * PLays the chosen music by the user.
	 * FXML annotation.
	 *  
	 * @throws 
	 * @throws InterruptedException
	 */
	@FXML
	protected void onMusicGenerationClicked() throws InterruptedException
	{
		ArtToMusicLogger.getInstance().info("Music Generation button clicked.");
		
		String radioButtonText = ((RadioButton) musicGroup.getSelectedToggle()).getText();
		
		switch (radioButtonText)
		{
			case "Melody Sample":
		        System.out.println("MAGWEG?");
		        break;
			case "Rhythm Sample":
				System.out.println("MAGWEG?");
				break;
			case "Combined Sample":
				System.out.println("MAGWEG?");
				break;
			case "Play Chord":
				beadsManager.playChord(120, new Chord(Globals.ChordNames.C, Globals.ChordKeys.MAJOR, 6), Buffer.SINE);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Start method of the JavaFX application.
	 * 
	 * @param primaryStage	the stage to build application on
	 */
    @Override
    public void start(Stage primaryStage) throws Exception
    {   	
    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/view/ArtToMusicView.fxml"));
    	scene = new Scene(root);
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Image");
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
    	File selectedFile = fileChooser.showOpenDialog(primaryStage);
    	if (selectedFile != null) 
    	    Globals.imageName = selectedFile.getName();
    
        primaryStage.setTitle("ArtToMusic");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Main method.
     * 
     * @param args	default arguments
     */
    public static void main(String[] args)
	{
    	ArtToMusicLogger.getInstance().info("In constructor of Main.");
    	
		launch(args);
		new ArtToMusicController();
	}
}
