package präsentation;

public class index {

    public static void main(String[] args) throws InterruptedException {
        Tree tree = new Tree(1000);

        tree.createNode("A");
        tree.createNode("B");
        tree.createNode("C");
        tree.createNode("D");
        tree.createNode("E");
        tree.createNode("F");
        tree.createNode("G");

        tree.connectNode(0,1);
        tree.connectNode(0,2);
        tree.connectNode(1,4);
        tree.connectNode(1,3);
        tree.connectNode(2,4);
        tree.connectNode(2,3);
        tree.connectNode(5,0);
        tree.connectNode(6,5);
        tree.connectNode(6,3);

        tree.setStart(0);
        tree.setZiel(4);


        tree.visualize();

        while (true) {
            tree.reset();
            tree.tiefenSuche();
        }

    }
}

