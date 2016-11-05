package model.graphics;

import org.rosuda.JRI.Rengine;

import utilities.ArtToMusicLogger;
import utilities.Globals;

/**
 * Created by rafael on 26.10.16.
 */
public class MathManager
{
    public static void edgeDetection(String algorithm)
    {
    	// Start Rengine.
        Rengine engine = new Rengine(new String[] {"--no-save" }, false, null);

        engine.eval("library('OpenImageR')");
        engine.eval("im = readImage('" + Globals.getInstance().pathToImages + "picasso.jpg')");
        engine.eval("imGray = rgb_2gray(im)");
        engine.eval("imEdge = edge_detection(imGray, method = '" + algorithm + "', conv_mode = 'same')");
                
        // Retrieve matrix of image.
        double[][] imEdgeMatrix = engine.eval("imEdge").asDoubleMatrix();
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < imEdgeMatrix.length; i++)
        {
        	for (int j = 0; j < imEdgeMatrix[i].length; j++)
        	{
        		sb.append(String.valueOf(imEdgeMatrix[i][j]) + " ");
        	}
        	sb.append("\n");
        }
        
        ArtToMusicLogger.getInstance().info(sb.toString());
        
        engine.end();
    }

    public MathManager() throws InterruptedException
    {
        edgeDetection("Sobel");
//        edgeDetection("LoG");
    }

    public static void main(String[] args) throws InterruptedException
    {
        new MathManager();
    }
}
