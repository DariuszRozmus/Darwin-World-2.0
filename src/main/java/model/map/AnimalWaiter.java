package model.map;

import model.elements.Animal;

import java.util.List;

public class AnimalWaiter {

    public boolean canEat(List<Animal> animalList){
       return animalList.stream().anyMatch(Animal::isHasMoved);
    }

    public void feedBest(int energy, List<Animal> animalList){
//        animalList.sort();
    }
}
