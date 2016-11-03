package model.graphics;

import org.rosuda.JRI.Rengine;

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
        engine.eval("im = readImage('/home/rafael/Dropbox/MasterInformatica/Eerste Jaar/Onderzoeksproject 1/ArtToMusic/img/picasso.jpg')");
        engine.eval("imGray = rgb_2gray(im)");
        engine.eval("imEdge = edge_detection(imGray, method = '" + algorithm + "', conv_mode = 'same')");
                
        // Retrieve matrix of image.
        double[][] imEdgeMatrix = engine.eval("imEdge").asDoubleMatrix();
        
        //Print output values
        System.out.println("Length of matrix : " + imEdgeMatrix.length);
        
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