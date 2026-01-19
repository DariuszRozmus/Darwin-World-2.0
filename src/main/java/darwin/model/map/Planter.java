package darwin.model.map;

import darwin.model.Vector2d;
import darwin.model.elements.Grass;

import java.util.*;

public class Planter {
    private final Random random = new Random(12345);

    public void plant(int plant, int energy, WorldMap worldMap){
        Set< Vector2d> jungleSet = worldMap.getJunglePositionsSet();
        Set< Vector2d> savannaSet = worldMap.getSavannaPositionsSet();

        List<Vector2d> jungleList = new ArrayList<>(jungleSet);
        List<Vector2d> savannaList = new ArrayList<>(savannaSet);

        int junglePlant = (plant*8)/10;
        int savannaPlant = plant - junglePlant;
        System.out.println("Jungle plants to plant: " + junglePlant);
        System.out.println("Savanna plants to plant: " + savannaPlant);


        Collections.shuffle(jungleList, random);
        Collections.shuffle(savannaList, random);

        jungleList.stream().limit(junglePlant)
                .map(vector2d -> new Grass(vector2d, energy))
                .forEach(worldMap::placePlant);

        savannaList.stream().limit(savannaPlant)
                .map(vector2d -> new Grass(vector2d, energy))
                .forEach(worldMap::placePlant);

    }

}
