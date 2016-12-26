package controller.graphicalanalysis;

import java.util.Random;
import java.util.concurrent.Callable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GraphicalAnalysisPublisher implements Callable<Object>
{
	private IntegerProperty source = new SimpleIntegerProperty(0);
		
	public GraphicalAnalysisPublisher(IntegerProperty target)
	{
		target.bind(source);
	}

	@Override
	public Object call() throws Exception 
	{
		Random random = new Random();
		int randNum = random.nextInt(11) + 10;
		
		for (int i = 3; i < 20; i++) 
		{
			try 
			{
				System.out.println("Publishing change: " + i);
				source.set(i);	
				
				Thread.sleep(randNum * 100);
			}
			catch (Exception e) {}
			
		}
		return null;
	}
}
