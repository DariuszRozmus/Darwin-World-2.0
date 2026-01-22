package darwin;

import darwin.config.PreliminaryData;
import darwin.model.Vector2d;
import darwin.model.map.WorldMap;

import darwin.config.PreliminaryData;
import darwin.model.Vector2d;
import darwin.model.Simulation;
import darwin.model.map.WorldMap;

public class World {
    public static void main() {

        PreliminaryData data = new PreliminaryData(6,6,
                new Vector2d(2,2),new Vector2d(4,4),
                false, 0, false, 0,
                false, 5,
                5,5,
                3,10,
                30,10, 2,20,
                4,2, 6);

        WorldMap worldMap = new WorldMap(data);
//    Simulation simulation = new Simulation(worldMap, data);
//    simulation.run();
//    Simulation simulation = new Simulation(worldMap, data);
//    simulation.run();
}}
