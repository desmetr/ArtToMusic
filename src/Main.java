import controller.MidiPlayerController;

import javax.sound.midi.MidiUnavailableException;

public class Main
{
    private MidiPlayerController midiPlayerController;

    public Main() throws MidiUnavailableException
    {
        midiPlayerController = new MidiPlayerController();
    }

    public static void main(String[] args) throws MidiUnavailableException
    {
        new Main();
    }
}
