package darwin.model.map;

import darwin.model.elements.Animal;
import darwin.model.elements.Gene;

import darwin.model.elements.Animal;
import darwin.model.elements.Gene;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class Breeder {

    private final int minEnergyToReproduce;
    private final int energyDivisor;
    private final int minNewGeneLength;
    private final int maxNewGeneLength;
    private final Random random = new Random(12345);
    private final RandomAnimalGenerator randomAnimalGenerator = new RandomAnimalGenerator();

    public Breeder(int minEnergyToReproduce, int energyDivisor, int minNewGeneLength, int maxNewGeneLength){
        this.minEnergyToReproduce = minEnergyToReproduce;
        this.energyDivisor = energyDivisor;
        this.minNewGeneLength = minNewGeneLength;
        this.maxNewGeneLength = maxNewGeneLength;
    }

    public void breedBest(List<Animal> animalList, int simulationDay, WorldMap worldMap){
        if (animalList.size() < 2) {
            return;
        }
        animalList.sort(Animal::compareTo);
        Animal animal1 = animalList.removeLast();
        Animal animal2 = animalList.removeLast();

        if (animal1.getEnergy() < minEnergyToReproduce ||
                animal2.getEnergy() < minEnergyToReproduce ||
                animal1.getSpecie() != animal2.getSpecie() ||
                animal1.getEnergy() / energyDivisor <= 0 ||
                animal2.getEnergy() / energyDivisor <= 0) {
            return;
        }
        int energy1 = animal1.getEnergy() / energyDivisor;
        animal1.decreaseEnergy(energy1);
        Queue<Gene> genes1 = animal1.getGene();

        int energy2 = animal2.getEnergy() / energyDivisor;
        animal2.decreaseEnergy(energy2);
        Queue<Gene> genes2 = animal2.getGene();

        int totalEnergy = energy1 + energy2;
        int share1 = (int) ceil((double) (energy1 / totalEnergy) * genes1.size());
        int share2 = (int) ceil((double) (energy2 / totalEnergy) * genes2.size());

        Queue<Gene> newGene1 = genes1.stream()
                .limit(share1).collect(Collectors.toCollection(ArrayDeque::new));
        Queue<Gene> newGene2 = genes2.stream()
                .limit(share2).collect(Collectors.toCollection(ArrayDeque::new));

        Queue<Gene> newGene = new ArrayDeque<>();
        newGene.addAll(newGene1);
        newGene.addAll(newGene2);
        newGene.addAll(randomAnimalGenerator
                .getGeneQueue(random.nextInt(minNewGeneLength, maxNewGeneLength + 1)));

        int newEnergy = energy1 + energy2;
        Animal animal =
                new Animal(simulationDay, newEnergy,  animal1.getSpecie(), newGene,
                        animal1.getDirection(), animal1.getPosition(), worldMap);
        animal1.increaseChildrenCount();
        animal2.increaseChildrenCount();
        worldMap.addToMap(animal);
    }
}
