package controller.graphicalanalysis;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

public class GraphicalAnalysisPublisher extends Task<Object>
{
	private IntegerProperty sourceInteger = new SimpleIntegerProperty(0);
	private BooleanProperty sourceBoolean = new SimpleBooleanProperty();
		
	public GraphicalAnalysisPublisher(IntegerProperty targetInteger, BooleanProperty targetBoolean)
	{
		targetInteger.bind(sourceInteger);
		targetBoolean.bindBidirectional(sourceBoolean);
	}

	@Override
	public Object call() throws Exception 
	{
		Random random = new Random();
		int randNum;
		
		for (int i = 3; i < 30; i++) 
		{
			randNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
			try 
			{
				System.out.println("Publishing change: " + i);
				sourceInteger.set(i);	
				
				Thread.sleep(randNum * 100);
				
				System.out.println(randNum);
				if (randNum >= 5)
					sourceBoolean.setValue(true);
			}
			catch (Exception e) {}
			
		}
		return null;
	}
}
