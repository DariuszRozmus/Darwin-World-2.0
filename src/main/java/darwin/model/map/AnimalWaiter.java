package darwin.model.map;

import darwin.model.elements.Animal;
import darwin.model.map.exceptions.NoAnimalsException;

import java.util.List;
import java.util.Optional;

public class AnimalWaiter {

    public boolean canEat(List<Animal> animalList){
       return animalList.stream().anyMatch(Animal::isHasMoved);
    }

    public void feedBest(int energy, List<Animal> animalList){
        Optional<Animal> animal = animalList.stream().min(Animal::compareTo);
        if(animal.isEmpty()){
            throw new NoAnimalsException("No animals!");
        }
        animal.ifPresent(a -> a.increaseEnergy(energy));
    }
}
