package präsentation;

import SortierAlgorithmen.Visualizer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Tree {
    ArrayList<Node> nodes = new ArrayList<Node>();
    ArrayList<Node> besucht = new ArrayList<Node>();
    ArrayList<Node> offenQueue = new ArrayList<Node>();
    ArrayList<Node> offenStack = new ArrayList<Node>();
    Node jetzt;
    Node zielNode;
    int speed = 0;
    int counter = 0;
    int start;
    int ziel;
    NodeVisualizer v;

    public void setStart(int start) {
        this.start = start;
    }

    public void setZiel(int ziel) {
        this.ziel = ziel;
    }

    public void connectNode(int a, int b) {
        (nodes.get(a)).nodes.add(nodes.get(b));
        (nodes.get(b)).nodes.add(nodes.get(a));

    }

    public void createNode(String data) {
        nodes.add(new Node(data));
    }

    public void breitenSuche() {

    }

    public void tiefenSuche() throws InterruptedException {
        boolean gefunden = false;
        boolean schonBesucht = false;
        jetzt = nodes.get(start);
        jetzt.jetzt = true;
        zielNode = nodes.get(ziel);
        zielNode.ziel = true;
        refresh();
        TimeUnit.SECONDS.sleep(2);
        while (!gefunden) {

            for (int i=0; i<jetzt.nodes.size(); i++) {
                schonBesucht = false;
                for (int k=0; k<besucht.size(); k++) {
                    if (jetzt.nodes.get(i).equals(besucht.get(k))) {
                        schonBesucht = true;
                        break;
                    }
                }
                if (!schonBesucht) {
                    offenStack.add(jetzt.nodes.get(i));
                    jetzt.nodes.get(i).offen = true;
                    refresh();
                }
            }

            System.out.println(jetzt.data);
            besucht.add(jetzt);
            jetzt.jetzt = false;
            jetzt.besucht = true;
            jetzt = offenStack.get(offenStack.size()-1);
            jetzt.jetzt = true;
            jetzt.offen = false;
            refresh();
            offenStack.remove(offenStack.size()-1);

            if (jetzt.equals(zielNode)) {
                System.out.println("gefunden");
                gefunden = true;
            }

        }
        jetzt.jetzt = false;
        jetzt.ziel = false;
        jetzt.gefunden = true;
        refresh();
        TimeUnit.SECONDS.sleep(2);
    }

    public void reset() throws InterruptedException {
        for (int i=0; i<nodes.size(); i++) {
            nodes.get(i).besucht = false;
            nodes.get(i).offen = false;
            nodes.get(i).jetzt = false;
            nodes.get(i).gefunden = false;
        }
        besucht.clear();
        offenStack.clear();
        refresh();
    }

    public void visualize() {
        v.go(this);
    }

    public void refresh() throws InterruptedException {
        v.refresh(this);
        TimeUnit.MILLISECONDS.sleep(speed);
    }

    Tree(int speed){
        this.speed = speed;
        this.v = new NodeVisualizer(this);
    }



}
