package controller.graphicalobserver;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GraphicalAnalysisObserver
{
	private IntegerProperty targetInteger = new SimpleIntegerProperty(0);
	private BooleanProperty targetBoolean = new SimpleBooleanProperty();
	
	public GraphicalAnalysisObserver() 
	{
		ChangeListener<?> changeListener = new ChangeListener<Object>()
    	{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				System.out.println("Observing change: " + newValue);
				if (targetBoolean.get())
				{
					System.out.println("Stop all");
					targetBoolean.set(false);
				}
			}
    	};
    	targetInteger.addListener((ChangeListener<? super Number>) changeListener);
	}

	public IntegerProperty getTargetInteger()	{	return targetInteger;	}
	public BooleanProperty getTargetBoolean()	{	return targetBoolean;	}
}
