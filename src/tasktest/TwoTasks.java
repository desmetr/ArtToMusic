package tasktest;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.sound.midi.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rafael on 14.10.16.
 */
public class TwoTasks extends Application
{
    Synthesizer synth = MidiSystem.getSynthesizer();
    final MidiChannel[] midiChannels = synth.getChannels();
    Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();

    ExecutorService executorService1 = Executors.newSingleThreadExecutor();
    ExecutorService executorService2 = Executors.newSingleThreadExecutor();

    public TwoTasks() throws MidiUnavailableException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        int instrumentNumber = 96;

        synth.open();
        synth.loadInstrument(instruments[instrumentNumber]);   // Load piano sound.
        midiChannels[0].programChange(0, instrumentNumber);

        primaryStage.setTitle("TwoTasks");

        HBox hbox = new HBox(25);

        Task<Integer> task1On = new Task<Integer>()
        {
            @Override protected Integer call() throws Exception
            {
                midiChannels[0].noteOff(67, 600);
                midiChannels[0].noteOn(60, 600);
                Thread.sleep(3000);
                midiChannels[0].noteOff(60, 600);
                return null;
            }
        };

        Task<Integer> task2On = new Task<Integer>()
        {
            @Override protected Integer call() throws Exception
            {
                midiChannels[0].noteOff(60, 600);
                midiChannels[0].noteOn(67, 600);
                Thread.sleep(5000);
                midiChannels[0].noteOff(67, 600);
                return null;
            }
        };

        Task<Integer> task1Off = new Task<Integer>()
        {
            @Override
            protected Integer call() throws Exception
            {
                midiChannels[0].noteOff(60, 600);
                return null;
            }
        };

        Task<Integer> task2Off = new Task<Integer>()
        {
            @Override
            protected Integer call() throws Exception
            {
                midiChannels[0].noteOff(67, 600);
                return null;
            }
        };

        Button buttonTask1On = new Button();
        buttonTask1On.setText("Start task 1");
        buttonTask1On.setOnAction(event -> executorService1.execute(task1On));

        Button buttonTask2On = new Button();
        buttonTask2On.setText("Start task 2");
        buttonTask2On.setOnAction(event -> executorService2.execute(task2On));

        Button buttonTask1Off = new Button();
        buttonTask1Off.setText("Stop task 1");
        buttonTask1Off.setOnAction(event -> executorService1.execute(task1Off));

        Button buttonTask2Off = new Button();
        buttonTask2Off.setText("Stop task 2");
        buttonTask1Off.setOnAction(event -> executorService1.execute(task2Off));

        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(buttonTask1On, buttonTask2On, buttonTask1Off, buttonTask2Off);

        StackPane root = new StackPane();
        root.getChildren().add(hbox);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
