package darwin.model.elements;

import darwin.model.Vector2d;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Grass extends Plant{
    public Grass(Vector2d position, int energy) {
        super(position, energy);
    }

    public String toString(){
        return "*";
    }

    public String getSymbol(){
        return "*";
    }

    public Color getColor(){
        return Color.GREEN;
    }

    public Text toText() {
        Text text = new Text(toString());
        text.setFill(Color.GREEN);
        return text;
    }

    @Override
    public int compareTo(WorldElement worldElement) {
        return 0;
    }
}
