package model.graphics;

import org.rosuda.JRI.Rengine;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableDoubleValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import model.music.MusicData;
import utilities.ArtToMusicLogger;
import utilities.Globals;

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
	
	private DoubleProperty sourceAHash = new SimpleDoubleProperty(0.0);
	private DoubleProperty sourceDHash = new SimpleDoubleProperty(0.0);
	private DoubleProperty sourcePHash = new SimpleDoubleProperty(0.0);
	
	private DoubleProperty sourceEntropy = new SimpleDoubleProperty(0.0);
	
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
		Globals.getInstance();
		engine.eval("im = readImage('" + Globals.pathToImages + Globals.imageName + "')");
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

	@SuppressWarnings("unchecked")
	public void colorAnalysis()
	{
		ArtToMusicLogger.getInstance().info("Getting RGB values from image " + Globals.imageName + ".");
		sourceRGBValues.addListener((Change<? extends ObservableList<Pixel>> change) -> 
		{
			MusicData.destinationRGBValuesMatrix.setAll(change.getList());
		});
		
		engine.eval("library(jpeg)");
		Globals.getInstance();
		engine.eval("img <- readJPEG('" + Globals.pathToImages + Globals.imageName + "')");

		int length = engine.eval("dim(img)[1]").asInt();
		int width = engine.eval("dim(img)[2]").asInt();

		double[][] rValues = engine.eval("img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,1] * 255").asDoubleMatrix();
		double[][] gValues = engine.eval("img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,2] * 255").asDoubleMatrix();
		double[][] bValues = engine.eval("img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,3] * 255").asDoubleMatrix();

		for (int i = 0; i < length; i++)
		{
			final ObservableList<Pixel> row = FXCollections.<Pixel> observableArrayList();
			
			for (int j = 0; j < width; j++)
			{
				row.add(new Pixel(rValues[i][j], gValues[i][j], bValues[i][j]));
			}
			
			sourceRGBValues.add(row);
		}
		
		// -------------------------------------------------------------------
		
		// TODO bind
		
		ChangeListener<?> changeListenerMeanR = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationMeanR).set((double) newValue);
			}
		};
		double meanR = engine.eval("mean(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,1] * 255)").asDouble();
		sourceMeanR.addListener((ChangeListener<? super Number>) changeListenerMeanR);
		sourceMeanR.set(meanR);

		ChangeListener<?> changeListenerMeanG = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationMeanG).set((double) newValue);
			}
		};
		double meanG = engine.eval("mean(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,2] * 255)").asDouble();
		sourceMeanG.addListener((ChangeListener<? super Number>) changeListenerMeanG);
		sourceMeanG.set(meanG);
		
		ChangeListener<?> changeListenerMeanB = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationMeanB).set((double) newValue);
			}
		};
		double meanB = engine.eval("mean(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,3] * 255)").asDouble();
		sourceMeanB.addListener((ChangeListener<? super Number>) changeListenerMeanB);
		sourceMeanB.set(meanB);
		
		ChangeListener<?> changeListenerMedianR = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationMedianR).set((double) newValue);
			}
		};
		double medianR = engine.eval("median(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,1] * 255)").asInt();
		sourceMedianR.addListener((ChangeListener<? super Number>) changeListenerMedianR);
		sourceMedianR.set(medianR);
		
		ChangeListener<?> changeListenerMedianG = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationMedianG).set((double) newValue);
			}
		};
		double medianG = engine.eval("median(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,2] * 255)").asInt();
		sourceMedianG.addListener((ChangeListener<? super Number>) changeListenerMedianG);
		sourceMedianG.set(medianG);
		
		ChangeListener<?> changeListenerMedianB = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationMedianB).set((double) newValue);
			}
		};
		double medianB = engine.eval("median(img[0:" + String.valueOf(length) + ", 0:" + String.valueOf(width) + ",][,,3] * 255)").asInt();
		sourceMedianB.addListener((ChangeListener<? super Number>) changeListenerMedianB);
		sourceMedianB.set(medianB);
	}
	
	public void hashAnalysis()
	{
		ArtToMusicLogger.getInstance().info("Performing hash analysis.");
		Globals.getInstance();
		
		engine.eval("library('OpenImageR')");
		engine.eval("im = readImage('" + Globals.pathToImages + Globals.imageName + "')");
		engine.eval("imGray = rgb_2gray(im)");
		engine.eval("aH = average_hash(imGray, hash_size = 8, MODE = 'hash')");
		engine.eval("dH = dhash(imGray, hash_size = 8, MODE = 'hash')");
		engine.eval("pH = phash(imGray, hash_size = 8, highfreq_factor = 4, MODE = 'hash', resize = 'nearest')");
		
		ChangeListener<?> changeListenerAHash = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationAHash).set((double) newValue);
			}
		};
		sourceAHash.addListener((ChangeListener<? super Number>) changeListenerAHash);
		sourceAHash.set(engine.eval("aH").asDoubleArray()[0]);
		
		ChangeListener<?> changeListenerDHash = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationDHash).set((double) newValue);
			}
		};
		sourceDHash.addListener((ChangeListener<? super Number>) changeListenerDHash);
		sourceDHash.set(engine.eval("dH").asDoubleArray()[0]);
		
		ChangeListener<?> changeListenerPHash = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationPHash).set((double) newValue);
			}
		};
		sourcePHash.addListener((ChangeListener<? super Number>) changeListenerPHash);
		sourcePHash.set(engine.eval("pH").asDoubleArray()[0]);
	}

	public void getEntropy()
	{
		ArtToMusicLogger.getInstance().info("Calculating entropy of image.");
		Globals.getInstance();
		
		engine.eval("library('OpenImageR')");
		engine.eval("library('entropy')");
		engine.eval("im = readImage('" + Globals.pathToImages + Globals.imageName + "')");
		engine.eval("histogram = HOG(im, cells = 3, orientations = 6)");
		engine.eval("entropy = entropy.empirical(histogram)");

		ChangeListener<?> changeListenerEntropy = new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				((WritableDoubleValue) MusicData.destinationEntropy).set((double) newValue);
			}
		};
		sourceEntropy.addListener((ChangeListener<? super Number>) changeListenerEntropy);
		sourceEntropy.set(engine.eval("entropy").asDoubleArray()[0]);
	} 

	public RManager() throws InterruptedException
	{
		// Start Rengine.
		engine = new Rengine(new String[] {"--no-save" }, false, null);
	}
}
