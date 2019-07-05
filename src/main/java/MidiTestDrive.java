import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MidiTestDrive {
    
    public static void main(String[] args) {
        try {
            Sequence sequence = MidiSystem.getSequence(new File("midiaudiofile"));
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
