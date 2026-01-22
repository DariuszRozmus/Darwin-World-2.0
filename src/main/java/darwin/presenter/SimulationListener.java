package darwin.presenter;

import darwin.model.map.WorldMap;

public interface SimulationListener {
    void onDayUpdate(int day, WorldMap worldMap);
}

