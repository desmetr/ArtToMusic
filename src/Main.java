import midi.PianoPlayer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        System.out.println("Start of program");
        PianoPlayer pianoPlayer = new PianoPlayer();
        pianoPlayer.main(args);
    }
}
