package model;

/**
 * Created by sarath on 7/5/19.
 */
public class HeartAdapter implements BeatModelInterface {

    HeartModelInterface heartModel;

    public HeartAdapter(HeartModelInterface heartModel) {
        this.heartModel = heartModel;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void on() {

    }

    @Override
    public void off() {

    }

    @Override
    public void setBPM(int bpm) {

    }

    @Override
    public int getBPM() {
        return heartModel.getHeartRate();
    }

    @Override
    public void registerBPMObserver(BPMObserver bpmObserver) {
        heartModel.registerObserver(bpmObserver);
    }

    @Override
    public void removeBPMObserver(BPMObserver bpmObserver) {
        heartModel.removeObserver(bpmObserver);
    }

    @Override
    public void registerBeatObserver(BeatObserver beatObserver) {
        heartModel.registerObserver(beatObserver);
    }

    @Override
    public void removeBeatObserver(BeatObserver beatObserver) {
        heartModel.removeObserver(beatObserver);
    }
}
