import controller.MidiPlayerController;

import javax.sound.midi.MidiUnavailableException;

public class Main
{

    public Main() throws MidiUnavailableException
    {
        MidiPlayerController midiPlayerController = new MidiPlayerController();
    }

    public static void main(String[] args) throws MidiUnavailableException
    {
        new Main();
    }
}
