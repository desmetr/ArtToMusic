package model.graphics;

import org.rosuda.JRI.Rengine;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import model.music.MusicData;
import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * Created by rafael on 26.10.16.
 */
public class MathManager
{
	private Rengine engine;
	private ObservableList<ObservableList<Double>> sourceEdgeMatrix = FXCollections.observableArrayList();
	
	private void convertToObservableMatrix(double[][] source)
	{
		for (int i = 0; i < source.length; i++)
        {
			final ObservableList<Double> row = FXCollections.<Double> observableArrayList();
					
        	for (int j = 0; j < source[i].length; j++)
        	{ 
        		row.add(source[i][j]);
        	}
        	
        	sourceEdgeMatrix.add(row);
        }
	}
	
    public void edgeDetection(String algorithm)
    {
    	ArtToMusicLogger.getInstance().info("Performing the " + algorithm + " filter."); 
    	sourceEdgeMatrix.addListener((Change<? extends ObservableList<Double>> change) -> 
    	{
    		MusicData.destinationEdgeMatrix.setAll(change.getList());
        });   
    	 
        engine.eval("library('OpenImageR')");
		engine.eval("im = readImage('" + Globals.getInstance().pathToImages + "picasso.jpg')");
        engine.eval("imGray = rgb_2gray(im)");
        engine.eval("imEdge = edge_detection(imGray, method = '" + algorithm + "', conv_mode = 'same')");
                
        // Retrieve matrix of image.
        double[][] imEdgeMatrix = engine.eval("imEdge").asDoubleMatrix();
        convertToObservableMatrix(imEdgeMatrix);
        
        ArtToMusicLogger.getInstance().info("MathManager: logging sourceEdgeMatrix");
        ArtToMusicLogger.getInstance().info(String.valueOf(sourceEdgeMatrix.size()));
        ArtToMusicLogger.getInstance().info(String.valueOf(sourceEdgeMatrix.get(0).size()));
        
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < sourceEdgeMatrix.size(); i++)
//        {
//        	for (int j = 0; j < sourceEdgeMatrix.get(i).size(); j++)
//        	{
//        		sb.append(String.valueOf(sourceEdgeMatrix.get(i).get(j)) + " ");
//        	}
//        	sb.append("\n");
//        }
//        ArtToMusicLogger.getInstance().info(sb.toString());
        
        MusicData.printEdgeMatrix();
        
        engine.end();
    }

    public MathManager() throws InterruptedException
    {
    	// Start Rengine.
    	engine = new Rengine(new String[] {"--no-save" }, false, null);
    }
}
