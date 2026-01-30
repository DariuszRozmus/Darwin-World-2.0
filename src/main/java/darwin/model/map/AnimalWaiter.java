package darwin.model.map;

import darwin.model.elements.Animal;
import darwin.model.elements.Plant;
import darwin.model.elements.Species;

import java.util.List;
import java.util.Optional;

public class AnimalWaiter {

    public boolean canEat(List<Animal> animalList){
       return animalList.stream().anyMatch(Animal::isHasMoved);
    }

    public void feedBest(Plant plant, List<Animal> animalList, WorldMap worldMap){
        Optional<Animal> bestAnimal = animalList.stream()
                .filter(animal -> animal.getSpecie().equals(Species.HERBIVORE)).min(Animal::compareTo);
        bestAnimal.ifPresent(animal -> {animal.increaseEnergy(plant.getEnergy());
        worldMap.removePlant(plant);});
    }
}
