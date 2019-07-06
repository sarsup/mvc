import controller.ControllerInterface;
import controller.HeartController;
import model.HeartModel;


/**
 * Created by sarath on 7/5/19.
 */
public class HeartTestDrive {

    public static void main(String[] args) {
        HeartModel heartModel = new HeartModel();
        ControllerInterface controllerInterface = new HeartController(heartModel);
    }
}
