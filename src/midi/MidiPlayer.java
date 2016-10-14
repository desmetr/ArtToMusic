package midi;

import javax.sound.midi.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by rafael on 12.10.16.
 */
public class MidiPlayer extends Application
{
    private Synthesizer synth = MidiSystem.getSynthesizer();
    final MidiChannel[] midiChannels = synth.getChannels();
    Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();

    public MidiPlayer() throws MidiUnavailableException
    {
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        synth.open();
        synth.loadInstrument(instruments[5]);   // Load piano sound.

        primaryStage.setTitle("MidiPlayer");

        HBox cBox = new HBox(25);
        Button buttonCOn = new Button();
        buttonCOn.setText("Play C");
        buttonCOn.setOnAction(event -> midiChannels[5].noteOn(60, 600));

        Button buttonCOff = new Button();
        buttonCOff.setText("Stop C");
        buttonCOff.setOnAction(event -> midiChannels[5].noteOff(60));

        cBox.setAlignment(Pos.CENTER);
        cBox.getChildren().addAll(buttonCOn, buttonCOff);

        HBox cSharpBox = new HBox(25);
        Button buttonCSharpOn = new Button();
        buttonCSharpOn.setText("Play C#");
        buttonCSharpOn.setOnAction(event -> midiChannels[5].noteOn(61, 600));

        Button buttonCSharpOff = new Button();
        buttonCSharpOff.setText("Stop C#");
        buttonCSharpOff.setOnAction(event -> midiChannels[5].noteOff(61));

        cSharpBox.setAlignment(Pos.CENTER);
        cSharpBox.getChildren().addAll(buttonCSharpOn, buttonCSharpOff);

        HBox hbox = new HBox(50);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(cBox, cSharpBox);

        hbox.getChildren().addAll(vbox);

        primaryStage.setScene(new Scene(hbox, 600, 300));
        primaryStage.show();
    }
}
