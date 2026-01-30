package darwin;

import darwin.view.SimulationApp;
import javafx.application.Application;

public class WorldGUI {
    public static void main(String[] args){
        System.out.println("check 1");
        Application.launch(SimulationApp.class, args);
        System.out.println("check 2");
    }
}

