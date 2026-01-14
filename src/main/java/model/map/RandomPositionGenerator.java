package model.map;

import model.Vector2d;

import java.util.*;

import static java.lang.Math.min;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final List<Vector2d> positions;
    private int amount;
    private final Random random = new Random();
    private int counter = 0;

    public RandomPositionGenerator(int width, int height, int amount) {
        positions = new ArrayList<>();
        for (int x = 0; x <= width; x++) {
            for (int y = 0; y <= height; y++) {
                Vector2d position = new Vector2d(x, y);
                positions.add(position);
            }
        }
        this.amount = min(amount,(width+1)*(height+1));
    }

    public RandomPositionGenerator(int width, int height){
        this(width,height,width*height);
    }

    public RandomPositionGenerator(Set<Vector2d> positionsSet, int amount){
        this.positions = new ArrayList<>(positionsSet);
        this.amount = min(amount, positions.size());
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return amount > counter ;
            }

            @Override
            public Vector2d next() {
                int i = random.nextInt(positions.size() - counter);
                Vector2d pos = positions.get(i);
                Collections.swap(positions, i, positions.size() - counter - 1);
                counter++;
                return pos;
            }
        };
    }
}
