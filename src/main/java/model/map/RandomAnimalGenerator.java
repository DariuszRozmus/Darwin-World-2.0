package model.map;

import model.elements.Gene;

import java.util.*;

public class RandomAnimalGenerator {
    private Random random = new Random();
    public RandomAnimalGenerator(){}

    public Queue<Gene> getGeneQueue(int length){
        List<Gene> geneList = random
                .ints(length,0,Gene.values().length)
                .mapToObj(i -> Gene.values()[i])
                .toList();
        return new ArrayDeque<>(geneList);
    }

    public MapDirection getMapDirection(){
        return MapDirection.values()[random.nextInt(MapDirection.values().length)];
    }
}
