package model.graphics;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;

/**
 * Created by rafael on 26.10.16.
 */
public class MathManager
{

    // Create a proxy, which we will use to control MATLAB
    private static MatlabProxyFactory factory = new MatlabProxyFactory();
    private static MatlabProxy proxy;

    public MathManager() throws MatlabConnectionException
    {
    }

    public static void computeSobelEdgeDetection(String pathToFile) throws MatlabInvocationException, MatlabConnectionException
    {
        proxy = factory.getProxy();

        // Read file in MATLAB.
        proxy.eval("I = imread('img/picasso.tif');");
    }

    public static void main(String[] args) throws MatlabConnectionException, MatlabInvocationException
    {
        computeSobelEdgeDetection("");
        //Display 'hello world' just like when using the demo
        /*
        proxy.eval("disp('hello world')");
         */

        //Set a variable, add to it, retrieve it, and print the result
        /*
        proxy.setVariable("a", 5);
        proxy.eval("a = a + 6");
        double result = ((double[]) proxy.getVariable("a"))[0];
        System.out.println("Result: " + result);
        */

        /*
        //Create a 4x3x2 array filled with random values
        proxy.eval("array = randn(4,3,2)");

        //Print a value of the array into the MATLAB Command Window
        proxy.eval("disp(['entry: ' num2str(array(3, 2, 1))])");

        //Get the array from MATLAB
        MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
        MatlabNumericArray array = processor.getNumericArray("array");

        //Print out the same entry, using Java's 0-based indexing
        System.out.println("entry: " + array.getRealValue(2, 1, 0));

        //Convert to a Java array and print the same value again
        double[][][] javaArray = array.getRealArray3D();
        System.out.println("entry: " + javaArray[2][1][0]);
        */

        /*
        //Create and print a 2D double array
        double[][] array = new double[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        System.out.println("Original: ");
        for(int i = 0; i < array.length; i++)
        {
            System.out.println(Arrays.toString(array[i]));
        }

        //Send the array to MATLAB, transpose it, then retrieve it and convert it to a 2D double array
        MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
        processor.setNumericArray("array", new MatlabNumericArray(array, null));
        proxy.eval("array = transpose(array)");
        double[][] transposedArray = processor.getNumericArray("array").getRealArray2D();

        //Print the returned array, now transposed
        System.out.println("Transposed: ");
        for(int i = 0; i < transposedArray.length; i++)
        {
            System.out.println(Arrays.toString(transposedArray[i]));
        }
        */

        /*
        //Create an array for this example
        proxy.eval("array = magic(3)");

        //Invoke eval, specifying 1 argument to be returned - arguments are returned as an array
        Object[] returnArguments = proxy.returningEval("array(2,2)", 1);
        //Retrieve the first (and only) element from the returned arguments
        Object firstArgument = returnArguments[0];
        //Like before, cast and index to retrieve the double value
        double innerValue = ((double[]) firstArgument)[0];
        //Print the result
        System.out.println("Result: " + innerValue);

        //Or all in one step
        double val = ((double[]) proxy.returningEval("array(2,2)", 1)[0])[0];
        System.out.println("Result: " + val);
        */

        //Display 'hello world' like before, but this time using feval
        /*
        proxy.feval("disp", "hello world");
         */

        //By specifying 3 return arguments, returns as String arrays the loaded M-files, MEX files, and Java classes
        /*
        Object[] inmem = proxy.returningFeval("inmem", 3);
        System.out.println("Java classes loaded:");
        System.out.println(Arrays.toString((String[]) inmem[2]));

        //Retrieve MATLAB's release date by providing the -date argument
        Object[] releaseDate = proxy.returningFeval("version", 1, "-date");
        System.out.println("MATLAB Release Date: " + releaseDate[0]);
        */

        //Disconnect the proxy from MATLAB
        proxy.disconnect();

    }
}
