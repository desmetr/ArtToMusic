package threadTest;

import javax.sound.midi.*;

/**
 * Created by rafael on 14.10.16.
 */
public class Thread1 implements Runnable
{
    Synthesizer synth = MidiSystem.getSynthesizer();
    final MidiChannel[] midiChannels = synth.getChannels();
    Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();

    public Thread1() throws MidiUnavailableException
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

        midiChannels[5].noteOn(60, 600);        // Play C.
    }
}
