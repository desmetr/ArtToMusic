package model.graphics;

import org.rosuda.JRI.Rengine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * Created by rafael on 26.10.16.
 */
public class MathManager
{
	private static ObservableList<ObservableList<Double>> convertToObservableMatrix(double[][] source)
	{
		ObservableList<ObservableList<Double>> destination = FXCollections.<ObservableList<Double>>observableArrayList();
		
		for (int i = 0; i < source.length; i++)
        {
			final ObservableList<Double> row = FXCollections.<Double> observableArrayList();
			destination.add(i, row);
			
        	for (int j = 0; j < source[i].length; j++)
        	{ 
        		row.add(source[i][j]);
        	}
        }
		return destination;
	}
	
    public static void edgeDetection(String algorithm)
    {
//    	ArtToMusicLogger.getInstance().info("Performing the " + algorithm + " filter."); 
    			
    	// Start Rengine.
        Rengine engine = new Rengine(new String[] {"--no-save" }, false, null);
        Globals.getInstance();
        
        engine.eval("library('OpenImageR')");
		engine.eval("im = readImage('" + Globals.pathToImages + "picasso.jpg')");
        engine.eval("imGray = rgb_2gray(im)");
        engine.eval("imEdge = edge_detection(imGray, method = '" + algorithm + "', conv_mode = 'same')");
                
        // Retrieve matrix of image.
        double[][] imEdgeMatrix = engine.eval("imEdge").asDoubleMatrix();
        ObservableList<ObservableList<Double>> oImEdgeMatrix = convertToObservableMatrix(imEdgeMatrix);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < oImEdgeMatrix.size(); i++)
        {
        	for (int j = 0; j < oImEdgeMatrix.get(i).size(); j++)
        	{
        		sb.append(String.valueOf(oImEdgeMatrix.get(i).get(j)) + " ");
        	}
        	sb.append("\n");
        }
//        ArtToMusicLogger.getInstance().info(sb.toString());
        
        engine.end();
    }

    public MathManager() throws InterruptedException
    {
    }

    public static void main(String[] args) throws InterruptedException
    {
        new MathManager();
    }
}
