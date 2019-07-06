package controller;

import model.BPMObserver;
import model.BeatObserver;
import model.HeartAdapter;
import model.HeartModelInterface;
import view.DJView;

/**
 * Created by sarath on 7/5/19.
 */
public class HeartController implements ControllerInterface {

    DJView view;
    HeartModelInterface model;

    public HeartController(HeartModelInterface model) {
        this.model = model;
        view = new DJView(this,new HeartAdapter(model));
        model.registerObserver((BeatObserver)view);
        model.registerObserver((BPMObserver) view);

        view.createView();
        view.createControls();

        view.disableStartMenuItem();
        view.disableStopMenuItem();
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void increaseBPM() {

    }

    @Override
    public void decreaseBPM() {

    }

    @Override
    public void setBPM(int bpm) {

    }
}
