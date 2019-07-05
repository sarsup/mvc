package model;

public interface BeatModelInterface {
    
    void initialize();
    void on();
    void off();
    void setBPM(int bpm);
    
    int getBPM();
    void registerBPMObserver(BPMObserver bpmObserver);
    void removeBPMObserver(BPMObserver bpmObserver);
    void registerBeatObserver(BeatObserver beatObserver);
    void removeBeatObserver(BeatObserver beatObserver);
    
}
