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
public class RManager
{
	private Rengine engine;
	private ObservableList<ObservableList<Double>> sourceEdgeMatrix = FXCollections.observableArrayList();
	private ObservableList<ObservableList<PixelRGB>> sourceRGBValues= FXCollections.observableArrayList();
	
	private void convertToObservableMatrix(double[][] source, ObservableList<ObservableList<Double>> destination)
	{
		for (int i = 0; i < source.length; i++)
        {
			final ObservableList<Double> row = FXCollections.<Double> observableArrayList();
					
        	for (int j = 0; j < source[i].length; j++)
        	{ 
        		row.add(source[i][j]);
        	}
        	
        	destination.add(row);
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
        convertToObservableMatrix(imEdgeMatrix, sourceEdgeMatrix);
        
        ArtToMusicLogger.getInstance().info("MathManager: logging sourceEdgeMatrix");
        ArtToMusicLogger.getInstance().info(String.valueOf(sourceEdgeMatrix.size()));
        ArtToMusicLogger.getInstance().info(String.valueOf(sourceEdgeMatrix.get(0).size()));
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sourceEdgeMatrix.size(); i++)
        {
        	for (int j = 0; j < sourceEdgeMatrix.get(i).size(); j++)
        	{
        		sb.append(String.valueOf(Globals.decimalFormat.format(sourceEdgeMatrix.get(i).get(j))) + " ");
        	}
        	sb.append("\n");
        }
        ArtToMusicLogger.getInstance().info(sb.toString());
        
        MusicData.printEdgeMatrix();
        
//        engine.end();
    }

    public void rgbAnalysis()
    {
    	ArtToMusicLogger.getInstance().info("Getting RGB values from image.");
    	sourceRGBValues.addListener((Change<? extends ObservableList<PixelRGB>> change) -> 
    	{
    		MusicData.destinationRGBValuesMatrix.setAll(change.getList());
        });
    	
    	engine.eval("library(jpeg)");
    	engine.eval("img <- readJPEG('" + Globals.getInstance().pathToImages + "picasso.jpg')");
    	
    	int length = engine.eval("dim(img)[1]").asInt();
    	int width = engine.eval("dim(img)[2]").asInt();
    	
    	double[][] rValues = engine.eval("img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,1] * 255").asDoubleMatrix();
    	double[][] gValues = engine.eval("img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,2] * 255").asDoubleMatrix();
    	double[][] bValues = engine.eval("img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,3] * 255").asDoubleMatrix();
    	
    	for (int i = 0; i < length; i++)
    	{
    		final ObservableList<PixelRGB> row = FXCollections.<PixelRGB> observableArrayList();
    		
    		for (int j = 0; j < width; j++)
    		{
    			row.add(new PixelRGB(rValues[i][j], gValues[i][j], bValues[i][j]));
    		}
    		
    		sourceRGBValues.add(row);
    	}
    	
//    	StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < sourceRGBValues.size(); i++)
//        {
//        	for (int j = 0; j < sourceRGBValues.get(i).size(); j++)
//        	{
//        		sb.append(sourceRGBValues.get(i).get(j).toString() + " ");
//        	}
//        	sb.append("\n");
//        }
//        ArtToMusicLogger.getInstance().info(sb.toString());
    }
    
    public RManager() throws InterruptedException
    {
    	// Start Rengine.
    	engine = new Rengine(new String[] {"--no-save" }, false, null);
    }
}
