package darwin.model.elements;

import darwin.model.Vector2d;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.UUID;

public interface WorldElement extends Comparable<WorldElement> {

    Vector2d getPosition();

    UUID getUUID();

    int compareTo(WorldElement worldElement);

//    Text toText();

    Color getColor();

    String getSymbol();
}
