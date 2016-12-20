package model.graphics;

import org.rosuda.JRI.Rengine;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableDoubleValue;
import javafx.beans.value.WritableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import model.music.MusicData;
import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * This class controls all interaction with the mathematical software R.
 * 
 * @author rafael
 * @version 1
 * @since 2016.10.16.
 */
public class RManager
{
	private Rengine engine;
	
	private ObservableList<ObservableList<Double>> sourceEdgeMatrix = FXCollections.observableArrayList();
	private ObservableList<ObservableList<Pixel>> sourceRGBValues= FXCollections.observableArrayList();
	
	private DoubleProperty sourceMeanR = new SimpleDoubleProperty(0.0);
	private DoubleProperty sourceMeanG = new SimpleDoubleProperty(0.0);
	private DoubleProperty sourceMeanB = new SimpleDoubleProperty(0.0);
	private DoubleProperty sourceMedianR = new SimpleDoubleProperty(0.0);
	private DoubleProperty sourceMedianG = new SimpleDoubleProperty(0.0);
	private DoubleProperty sourceMedianB = new SimpleDoubleProperty(0.0);
	
	/**
	 * Converts a two dimensional array of doubles into an observable list of observable lists.
	 * 
	 * @param source	the 2D array of doubles
	 * @param destination	the observable list of observable lists
	 */
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
	
	/**
	 * Tells R to perform a certain edge detection algorithm.
	 * 
	 * @param algorithm		the edge detection algorithm to perform
	 */
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
    }

    /**
     * Tells R to give information about the pixels of the image.
     */
    public void colorAnalysis()
    {
    	ArtToMusicLogger.getInstance().info("Getting RGB values from image " + Globals.imageName + ".");
    	sourceRGBValues.addListener((Change<? extends ObservableList<Pixel>> change) -> 
    	{
    		MusicData.destinationRGBValuesMatrix.setAll(change.getList());
        });
    	
    	engine.eval("library(jpeg)");
    	engine.eval("img <- readJPEG('" + Globals.getInstance().pathToImages + Globals.imageName + "')");
    	
    	int length = engine.eval("dim(img)[1]").asInt();
    	int width = engine.eval("dim(img)[2]").asInt();
    	
    	double[][] rValues = engine.eval("img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,1] * 255").asDoubleMatrix();
    	double[][] gValues = engine.eval("img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,2] * 255").asDoubleMatrix();
    	double[][] bValues = engine.eval("img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,3] * 255").asDoubleMatrix();
    	
    	final ChangeListener changeListenerMeanR = new ChangeListener()
    	{
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) 
			{
				System.out.println("blabl1");
				System.out.println((double) newValue);
				((WritableDoubleValue) MusicData.destinationMeanR).set((double) newValue);
			}
    	};
    	double meanR = engine.eval("mean(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,1] * 255)").asDouble();
    	sourceMeanR.addListener(changeListenerMeanR);
    	sourceMeanR.set(meanR);

    	final ChangeListener changeListenerMeanG = new ChangeListener()
    	{
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) 
			{
				System.out.println("blabl2");
				System.out.println((double) newValue);
				((WritableDoubleValue) MusicData.destinationMeanG).set((double) newValue);
			}
    	};
    	double meanG = engine.eval("mean(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,2] * 255)").asDouble();
    	sourceMeanR.addListener(changeListenerMeanG);
    	sourceMeanG.set(meanG);
    	
    	final ChangeListener changeListenerMeanB = new ChangeListener()
    	{
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) 
			{
				System.out.println("blabl3");
				System.out.println((double) newValue);
				((WritableDoubleValue) MusicData.destinationMeanB).set((double) newValue);
			}
    	};
    	double meanB = engine.eval("mean(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,3] * 255)").asDouble();
    	sourceMeanR.addListener(changeListenerMeanB);
    	sourceMeanB.set(meanB);
    	
    	final ChangeListener changeListenerMedianR = new ChangeListener()
    	{
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationMedianR).set((double) newValue);
			}
    	};
    	double medianR = engine.eval("median(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,1] * 255)").asInt();
    	sourceMeanR.addListener(changeListenerMedianR);
    	sourceMedianR.set(medianR);
    	
    	final ChangeListener changeListenerMedianG = new ChangeListener()
    	{
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationMedianG).set((double) newValue);
			}
    	};
    	sourceMeanR.addListener(changeListenerMedianG);
    	double medianG = engine.eval("median(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,2] * 255)").asInt();
    	sourceMedianR.set(medianG);
    	
    	final ChangeListener changeListenerMedianB = new ChangeListener()
    	{
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationMedianB).set((double) newValue);
			}
    	};
    	sourceMeanR.addListener(changeListenerMedianB);
    	double medianB = engine.eval("median(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,3] * 255)").asInt();
      	sourceMedianR.set(medianB);
          
    	for (int i = 0; i < length; i++)
    	{
    		final ObservableList<Pixel> row = FXCollections.<Pixel> observableArrayList();
    		
    		for (int j = 0; j < width; j++)
    		{
    			row.add(new Pixel(rValues[i][j], gValues[i][j], bValues[i][j]));
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
    
    /**
     * Constructor of this class.
     * @throws InterruptedException
     */
    public RManager() throws InterruptedException
    {
    	// Start Rengine.
    	engine = new Rengine(new String[] {"--no-save" }, false, null);
    }
}
