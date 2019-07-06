package model;

/**
 * Created by sarath on 7/5/19.
 */
public interface HeartModelInterface {

    public int getHeartRate();

    public void registerObserver(BeatObserver o);
    public void registerObserver(BPMObserver o);

    public void removeObserver(BeatObserver o);
    public void removeObserver(BPMObserver o);
}
