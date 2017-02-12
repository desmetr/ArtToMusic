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

public class ArtToMusicView extends Application
{
	private Scene scene;
	private RManager rManager;
	
	@FXML private ToggleGroup graphicsGroup;
	@FXML private ToggleGroup musicGroup;
	
	@FXML 
	private void initialize()
	{
		try 
		{
			rManager = new RManager();

			Data.readMidiNoteNumbers();
			Data.readNoteFrequencies();
			Data.readDegrees();
		} 
		catch (InterruptedException e) 		{	e.printStackTrace();	} 
	}
	
	@FXML
	protected void onGraphicalAnalysisClicked() throws InterruptedException
	{
		ArtToMusicLogger.getInstance().info("Start Graphical Analysis button clicked.");
		System.out.println(Globals.imageName);
		
		rManager.edgeDetection("Frei_chen");
		MusicData.setTempo();
		
		Thread.sleep(1000);
		
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
    
    public static void main(String[] args)
	{
    	ArtToMusicLogger.getInstance().info("In constructor of Main.");
    	
		launch(args);
		new ArtToMusicController();
	}
}
