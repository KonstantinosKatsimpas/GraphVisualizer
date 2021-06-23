package präsentation;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Node {
    ArrayList<Node> nodes = new ArrayList<Node>();
    ArrayList<Line2D> lines = new ArrayList<Line2D>();
    String data;
    double randomX;
    double randomY;
    boolean jetzt;
    boolean besucht;
    boolean offen;
    boolean ziel;
    boolean gefunden;

    Node(String data){
        this.data = data;
    }
}
