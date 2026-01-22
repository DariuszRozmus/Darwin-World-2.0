package darwin.model.map;

import darwin.model.Vector2d;
import darwin.model.elements.Animal;

import java.util.*;

public class FomoTherapist {

    public void findFomoGroupsAndBind(WorldMap worldMap, int minFomoGroupSize, int fomoRay) {
        List<Animal> animalPositions = new ArrayList<>(worldMap.getAnimalLiveList());
        Set<Vector2d> list = worldMap.getAnimalsMap().keySet();

        List<Vector2d> centerList = list.stream().filter(vec2d -> {
                    int cnt = 0;
                    for (int i = -fomoRay; i <= fomoRay + 1; i++) {
                        for (int j = -fomoRay; j <= fomoRay; j++) {
                            Vector2d pos = new Vector2d(vec2d.getX() + i, vec2d.getY() + j);
                            if (worldMap.isInBounds(pos) && worldMap.isOccupiedByAnimal(pos)) {
                                cnt += 1;
                            }
                        }
                    }
                    return cnt >= minFomoGroupSize;
                }
        ).toList();

        animalPositions.stream().filter(animal -> {
                    return !animal.isFomo() && centerList.stream()
                            .noneMatch(center -> animal.getPosition().distance(center) <= fomoRay);
                })
                .forEach(animal -> {
                    Optional<Vector2d> closeCenter = centerList.stream()
                            .filter(center -> animal.getPosition().distance(center) <= 2 * fomoRay + 1 &&
                                    animal.getPosition().distance(center) > fomoRay)
                            .min(Comparator.comparingInt(
                                    center -> animal.getPosition().distance(center)
                            ));
                    closeCenter.ifPresent(center -> {
                                animal.setFomo(true);
                                animal.setFomoPosition(center);
                            }
                    );
                }
        );
    }
}

