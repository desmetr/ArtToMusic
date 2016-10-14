package threadTest;

import javax.sound.midi.*;

/**
 * Created by rafael on 14.10.16.
 */
public class Thread2 implements Runnable
{
    Synthesizer synth = MidiSystem.getSynthesizer();
    final MidiChannel[] midiChannels = synth.getChannels();
    Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();

    public Thread2() throws MidiUnavailableException
    {
    }

    @Override
    public void run()
    {
        try {
            synth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        synth.loadInstrument(instruments[5]);   // Load piano sound.

        midiChannels[5].noteOn(67, 600);        // Play G.
    }

}
