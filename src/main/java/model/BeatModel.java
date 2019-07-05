package model;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class BeatModel implements BeatModelInterface, MetaEventListener {
    
    private Sequencer sequencer;
    private ArrayList<BeatObserver> beatObservers = new ArrayList<>();
    private ArrayList<BPMObserver> bpmObservers = new ArrayList<>();
    private Sequence sequence;
    private Track track;
    
    private int bpm = 90;
    
    private void setupMidi(){
    
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
            sequence = new Sequence(Sequence.PPQ,4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(getBPM());
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            e.printStackTrace();
        }
    
    }
    
    private void buildTrackAndStart(){
    
        int[] trackList = {35, 0, 46, 0};
    
        sequence.deleteTrack(null);
        track = sequence.createTrack();
    
        makeTracks(trackList);
        track.add(makeEvent(192,9,1,0,4));
        try {
            sequencer.setSequence(sequence);
        } catch(Exception e) {
            e.printStackTrace();
        }
    
    }
    
    public void makeTracks(int[] list) {
        
        for (int i = 0; i < list.length; i++) {
            int key = list[i];
            
            if (key != 0) {
                track.add(makeEvent(144,9,key, 100, i));
                track.add(makeEvent(128,9,key, 100, i+1));
            }
        }
    }
    
    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return event;
    }
    
    private void notifyBPMObservers(){
        Iterator iterator = bpmObservers.iterator();
        while (iterator.hasNext()){
            BPMObserver observer = (BPMObserver) iterator.next();
            observer.updateBPM();
        }
    }
    
    private void notifyBeatObservers(){
        Iterator iterator = beatObservers.iterator();
        while (iterator.hasNext()){
            BeatObserver observer = (BeatObserver) iterator.next();
            observer.updateBeat();
        }
    }
    
    private void beatEvent(){
        notifyBeatObservers();
    }
    
    @Override
    public void meta(MetaMessage message) {
    
        if (message.getType() == 47) {
            beatEvent();
            sequencer.start();
            setBPM(getBPM());
        }
    
    }
    
    @Override
    public void initialize() {
        setupMidi();
        buildTrackAndStart();
    }
    
    @Override
    public void on() {
    
        sequencer.start();
        setBPM(90);
        notifyBPMObservers();
        notifyBeatObservers();
    
    }
    
    @Override
    public void off() {
        
        setBPM(0);
        sequencer.stop();
        notifyBPMObservers();
        notifyBeatObservers();
    
    }
    
    @Override
    public void setBPM(int bpm) {
        this.bpm = bpm;
        sequencer.setTempoInBPM(getBPM());
        notifyBPMObservers();
        
    }
    
    @Override
    public int getBPM() {
        return bpm;
    }
    
    @Override
    public void registerBeatObserver(BeatObserver beatObserver) {
        beatObservers.add(beatObserver);
    }
    
    @Override
    public void removeBeatObserver(BeatObserver beatObserver) {
        beatObservers.remove(beatObserver);
    }
    
    @Override
    public void registerBPMObserver(BPMObserver bpmObserver) {
        bpmObservers.add(bpmObserver);
    }
    
    @Override
    public void removeBPMObserver(BPMObserver bpmObserver) {
        bpmObservers.remove(bpmObserver);
    }
}
