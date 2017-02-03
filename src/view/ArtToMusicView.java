package view;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.ArtToMusicController;
import controller.graphicalanalysis.GraphicalAnalysisPublisher;
import controller.graphicalobserver.GraphicalAnalysisObserver;
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
import utilities.Pair;

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
			Data.readDegrees();
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
		ArtToMusicLogger.getInstance().info("Start Graphical Analysis button clicked.");
		
		rManager.colorAnalysis();
		MusicData.analyseRGB();
		
//		String radioButtonText = ((RadioButton) graphicsGroup.getSelectedToggle()).getText();
//				
//		switch (radioButtonText)
//		{
//			case "Frei Chen":
//				rManager.edgeDetection("Frei_chen");
//				break;
//			case "Roberts Cross":
//				rManager.edgeDetection("Roberts_cross");
//				break;
//			case "Color Analysis":
//				rManager.colorAnalysis();
//				MusicData.analyseRGB();
//				break;
//			case "Thread Test":
//				GraphicalAnalysisObserver gaObserver = new GraphicalAnalysisObserver();
//				GraphicalAnalysisPublisher gaPublisher = new GraphicalAnalysisPublisher(gaObserver.getTarget());
//				
//				final ExecutorService service = Executors.newSingleThreadExecutor();
//				service.submit(gaPublisher);
//				
//				break;
//			default:
//				rManager.edgeDetection(radioButtonText);
//				break;
//		}
	}
	
	@FXML
	protected void on1251Clicked()
	{
		beadsManager = new BeadsManager();
		Chord chord = new Chord(Globals.ChordKey.C, Globals.ChordType.MAJOR, 4, Globals.Chord.C_MAJOR);
		beadsManager.playChordProgression1251(Globals.ChordProgression.I_II_V_I, 120, chord, Buffer.SINE);
	}
	
	@FXML 
	protected void on1346Clicked()
	{
		beadsManager = new BeadsManager();
		Chord chord = new Chord(Globals.ChordKey.C, Globals.ChordType.MAJOR, 4, Globals.Chord.C_MAJOR);
		beadsManager.playChordProgression1251(Globals.ChordProgression.I_III_IV_VI, 120, chord, Buffer.SINE);
	}
	
	@FXML 
	protected void on1624Clicked()
	{
		beadsManager = new BeadsManager();
		Chord chord = new Chord(Globals.ChordKey.C, Globals.ChordType.MAJOR, 4, Globals.Chord.C_MAJOR);
		beadsManager.playChordProgression1251(Globals.ChordProgression.I_VI_II_IV, 120, chord, Buffer.SINE);
	}

	@FXML 
	protected void on13625Clicked()
	{
		beadsManager = new BeadsManager();
		Chord chord = new Chord(Globals.ChordKey.C, Globals.ChordType.MAJOR, 4, Globals.Chord.C_MAJOR);
		beadsManager.playChordProgression1251(Globals.ChordProgression.I_III_VI_II_V, 120, chord, Buffer.SINE);
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
