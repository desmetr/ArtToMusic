package view;

import javax.sound.midi.MidiUnavailableException;

import controller.MidiPlayerController;
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
import utilities.ArtToMusicLogger;

/**
 * Created by rafael on 22.10.16.
 */
public class MidiPlayerView extends Application
{
	private Scene scene;
	private MathManager mathManager;
	
	@FXML private ToggleGroup graphicsGroup;
	@FXML private ToggleGroup musicGroup;
	
	@FXML 
	private void initialize()
	{
		try 
		{
			mathManager = new MathManager();
		} 
		catch (InterruptedException e) {	e.printStackTrace();	}
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
		
		musicGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) 
		    {
		        RadioButton radioButton = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
		        ArtToMusicLogger.getInstance().info("Selected filter: " + radioButton.getText());
		        
		        mathManager.edgeDetection(radioButton.getText());
		    }
		});
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
        new MidiPlayerController();
		launch(args);
	}
}
