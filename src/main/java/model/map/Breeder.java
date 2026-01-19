package model.map;

import model.elements.Animal;
import model.elements.Gene;
import model.elements.Herbivore;
import model.elements.Species;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class Breeder {

    private static final int MIN_ENERGY = 10;
    private static final int ENERGY_DIVISOR = 4;
    private static final int MIN_NEW_GENE_LENGTH = 4;
    private static final int MAX_NEW_GENE_LENGTH = 32;
    private final Random random = new Random(12345);
    private final RandomAnimalGenerator randomAnimalGenerator = new RandomAnimalGenerator();


    public void breedBest(List<Animal> animalList, int simulationDay, WorldMap worldMap){
        if (animalList.size() < 2) {
            return;
        }
        animalList.sort(Animal::compareTo);
        Animal animal1 = animalList.removeLast();
        Animal animal2 = animalList.removeLast();

        if (animal1.getEnergy() < MIN_ENERGY || animal2.getEnergy() < MIN_ENERGY){
            return;
        }
        int energy1 = animal1.getEnergy() / ENERGY_DIVISOR;
        animal1.decreaseEnergy(energy1);
        Queue<Gene> genes1 = animal1.getGene();

        int energy2 = animal2.getEnergy() / ENERGY_DIVISOR;
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
                .getGeneQueue(random.nextInt(MIN_NEW_GENE_LENGTH, MAX_NEW_GENE_LENGTH + 1)));

        int newEnergy = energy1 + energy2;
        Animal animal =
                new Herbivore(simulationDay, newEnergy, newGene,
                        animal1.getDirection(), animal1.getPosition(), worldMap);
        worldMap.addToMap(animal);
    }
}
