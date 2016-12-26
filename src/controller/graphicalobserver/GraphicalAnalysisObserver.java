package controller.graphicalobserver;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GraphicalAnalysisObserver
{
	private IntegerProperty target = new SimpleIntegerProperty(0); 
	
	public GraphicalAnalysisObserver() 
	{
		ChangeListener<?> changeListener = new ChangeListener<Object>()
    	{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) 
			{
				System.out.println("Observing change: " + newValue);
			}
    	};
    	target.addListener((ChangeListener<? super Number>) changeListener);
	}

	public IntegerProperty getTarget() {	return target;	}

}
