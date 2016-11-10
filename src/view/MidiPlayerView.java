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
	
	@FXML private Button startButton;
	@FXML private ToggleGroup graphicsGroup;
	@FXML private ToggleGroup musicGroup;
	
	@FXML 
	private void initialize()
	{
	}
	
	@FXML
	protected void onSubmitGraphicsClicked() throws InterruptedException
	{
		ArtToMusicLogger.getInstance().info("Submit Graphics button clicked.");
		
		graphicsGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) 
		    {
		        RadioButton radioButton = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
		        ArtToMusicLogger.getInstance().info("Selected filter: " + radioButton.getText());
		        
		        MathManager.edgeDetection(radioButton.getText());
		    }
		});
	}
	
	@FXML
	protected void onSubmitMusicClicked() throws MidiUnavailableException, InterruptedException
	{
		ArtToMusicLogger.getInstance().info("Submit Music button clicked.");
		
		musicGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) 
		    {
		        RadioButton radioButton = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
		        ArtToMusicLogger.getInstance().info("Selected filter: " + radioButton.getText());
		        
		        MathManager.edgeDetection(radioButton.getText());
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
