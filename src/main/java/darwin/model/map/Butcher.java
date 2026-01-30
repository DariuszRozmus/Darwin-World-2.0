package darwin.model.map;

import darwin.model.elements.Animal;
import darwin.model.elements.Species;

import java.util.List;
import java.util.Optional;

public class Butcher {

    public void killAndFeed(List<Animal> animalList) {
        Optional<Animal> bestPredator = animalList.stream()
                .filter(animal -> animal.getSpecie().equals(Species.CARNIVORE)).min(Animal::compareTo);
        if (bestPredator.isPresent()) {
            Animal predator = bestPredator.get();
            Optional<Animal> prey = animalList.stream()
                    .filter(animal -> animal.getSpecie().equals(Species.HERBIVORE))
                    .max(Animal::compareTo);
            if (prey.isPresent()){
                Animal victim = prey.get();
                int energyGain = victim.getEnergy();
                predator.increaseEnergy(energyGain);
                victim.decreaseEnergy(energyGain);
            }
        }
    }
}
