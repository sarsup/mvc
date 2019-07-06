package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by sarath on 7/5/19.
 */
public class HeartModel implements HeartModelInterface, Runnable {

    private int heartRate = 90;
    private ArrayList<BeatObserver> beatObservers;
    private ArrayList<BPMObserver> bpmObservers;

    private int time = 1000;
    private Random random;
    Thread thread;

    public HeartModel(){
        beatObservers = new ArrayList<>();
        bpmObservers = new ArrayList<>();
        random = new Random(System.currentTimeMillis());
        thread = new Thread(this);
        thread.start();

    }


    @Override
    public void run() {

        int lastRate = -1;
        for(;;){
            int change = random.nextInt(10);
            if(random.nextInt(2) == 0){
                change = 0 - change;
            }
            heartRate = 60000/(time + change);
            if(heartRate<120 && heartRate > 50){
                time += change;
                notifyBeatObservers();
                if(heartRate != lastRate){
                    lastRate = heartRate;
                    notifyBPMObservers();
                }
            }
            try {
                Thread.sleep(time);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void notifyBeatObservers(){
        //beatObservers.stream().forEach( beatObserver -> beatObserver.updateBeat());
        Iterator iterator = beatObservers.iterator();
        while (iterator.hasNext()){
            BeatObserver beatObserver = (BeatObserver)iterator.next();
            beatObserver.updateBeat();
        }

    }

    private void notifyBPMObservers(){
        //bpmObservers.stream().forEach(bpmObserver -> bpmObserver.updateBPM());
        Iterator iterator = bpmObservers.iterator();
        while (iterator.hasNext()){
            BPMObserver bpmObserver = (BPMObserver)iterator.next();
            bpmObserver.updateBPM();
        }
    }

    @Override
    public int getHeartRate() {
        return heartRate;
    }

    @Override
    public void registerObserver(BeatObserver o) {

        beatObservers.add(o);

    }

    @Override
    public void registerObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    @Override
    public void removeObserver(BeatObserver o) {
        beatObservers.remove(o);
    }

    @Override
    public void removeObserver(BPMObserver o) {
        bpmObservers.remove(o);
    }
}
