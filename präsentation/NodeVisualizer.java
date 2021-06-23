package präsentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

final public class NodeVisualizer implements ActionListener{
    JFrame frame;
    JButton button1;
    JButton button2;
    JPanel top;
    präsentation.NodeVisualizer.DrawPanel drawPanel;
    Tree tree;
    boolean flag = false;
    boolean nextPls = false;
    int i = 0;

    Color slightBlack = new Color(0, 0, 0,  200);


    public void go(Tree tree) {
        frame = new JFrame("NodeVisualizer");
        button1 = new JButton("remesh");
        button2 = new JButton("next");
        drawPanel = new präsentation.NodeVisualizer.DrawPanel();
        top = new JPanel();
        top.add(button1);
        top.add(button2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2560,1440);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setBackground(Color.BLACK);
        frame.setLocationByPlatform(true);

        drawPanel.setBackground(Color.BLACK);
        button1.setBackground(Color.WHITE);
        button1.addActionListener(this);
        button2.setBackground(Color.WHITE);
        button2.addActionListener(this);

        frame.getContentPane().add(BorderLayout.NORTH, top);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);


        frame.setVisible(true);
        this.tree = tree;

        for (int i=0; i<tree.nodes.size(); i++) {
            tree.nodes.get(i).randomX = Math.random();
        }
        for (int i=0; i<tree.nodes.size(); i++) {
            tree.nodes.get(i).randomY = Math.random();
        }
    }

    NodeVisualizer (Tree tree) {
        this.tree = tree;
    }

    class DrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(slightBlack);
            g.fillRect(0, 0, (int) this.getWidth(), (int) this.getHeight());
            for (int i = 0; i < tree.nodes.size(); i++) {

                g.setColor(Color.WHITE);
                int X = (int) (tree.nodes.get(i).randomX * this.getWidth() * 0.8 + this.getWidth() * 0.1);
                int Y = (int) (tree.nodes.get(i).randomY * this.getHeight() * 0.8 + this.getHeight() * 0.1);

                if (tree.nodes.get(i).nodes != null) {
                    for (int k = 0; k < tree.nodes.get(i).nodes.size(); k++) {
                        g.drawLine(X, Y, (int) (tree.nodes.get(i).nodes.get(k).randomX * this.getWidth() * 0.8 + this.getWidth() * 0.1),
                                (int) (tree.nodes.get(i).nodes.get(k).randomY * this.getHeight() * 0.8 + this.getHeight() * 0.1));
                    }
                }
            }


            for (int i = 0; i < tree.nodes.size(); i++) {

                int X = (int) (tree.nodes.get(i).randomX * this.getWidth() * 0.8 + this.getWidth() * 0.1);
                int Y = (int) (tree.nodes.get(i).randomY * this.getHeight() * 0.8 + this.getHeight() * 0.1);

                if (tree.nodes.get(i).jetzt) {
                    g.setColor(Color.GREEN);
                } else if (tree.nodes.get(i).offen) {
                    g.setColor(Color.GRAY);
                } else if (tree.nodes.get(i).besucht) {
                    g.setColor(Color.BLACK);
                } else if (tree.nodes.get(i).ziel) {
                    g.setColor(Color.RED);
                } else if (tree.nodes.get(i).gefunden) {
                    g.setColor(Color.magenta);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillArc(X-60, Y-60,120,120,0,360);

                if (tree.nodes.get(i).jetzt || tree.nodes.get(i).besucht) {
                    g.setColor(Color.white);
                } else {
                    g.setColor(Color.black);
                }
                g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                g.drawString(tree.nodes.get(i).data,X-20,Y+20);
            }
        }
    }

    public void refresh(Tree tree) {
        this.tree = tree;
        frame.repaint();
    }

    public void refresh() {
        for (int counter = 0; counter<999999; counter++) {
            flag = false;
            float epsilon =(float) 0.000001;

            for (int i = 0; i < tree.nodes.size(); i++) {
                tree.nodes.get(i).randomX = Math.random();
                tree.nodes.get(i).randomY = Math.random();
                tree.nodes.get(i).lines.clear();
            }

            for (int i = 0; i < tree.nodes.size(); i++) {
                for (int k = 0; k < tree.nodes.size(); k++) {
                    if (i == k) {
                    } else {
                        float x1 = (float) tree.nodes.get(i).randomX;
                        float y1 = (float) tree.nodes.get(i).randomY;
                        float x2 = (float) tree.nodes.get(k).randomX;
                        float y2 = (float) tree.nodes.get(k).randomY;

                        double distance = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
                        if (distance < 0.3 && distance != 0.0) {
                            flag = true;
                        }
                    }
                }
            }

            for (int i = 0; i < tree.nodes.size(); i++) {
                for (int k = 0; k < tree.nodes.get(i).nodes.size(); k++) {
                    float x1 = (float) tree.nodes.get(i).randomX;
                    float y1 = (float) tree.nodes.get(i).randomY;
                    float x2 = (float) tree.nodes.get(i).nodes.get(k).randomX;
                    float y2 = (float) tree.nodes.get(i).nodes.get(k).randomY;

                    tree.nodes.get(i).lines.add(new Line2D.Float(x1, y1, x2, y2));
                }
            }

            for (int i = 0; i < tree.nodes.size(); i++) {
                for (int q = 0; q < tree.nodes.get(i).lines.size(); q++) {
                    for (int k = 0; k < tree.nodes.get(i).nodes.size(); k++) {
                        for (int w = 0; w <tree.nodes.get(i).nodes.get(k).lines.size(); w++) {
                            boolean holyshitwhy = (
                                    (Math.abs(tree.nodes.get(i).lines.get(q).getX1() - tree.nodes.get(i).nodes.get(k).lines.get(w).getX1()) < epsilon) ||
                                            (Math.abs(tree.nodes.get(i).lines.get(q).getX1() - tree.nodes.get(i).nodes.get(k).lines.get(w).getX2()) < epsilon) ||
                                            (Math.abs(tree.nodes.get(i).lines.get(q).getX2() - tree.nodes.get(i).nodes.get(k).lines.get(w).getX1()) < epsilon) ||
                                            (Math.abs(tree.nodes.get(i).lines.get(q).getX2() - tree.nodes.get(i).nodes.get(k).lines.get(w).getX2()) < epsilon) ||

                                            (Math.abs(tree.nodes.get(i).lines.get(q).getY1() - tree.nodes.get(i).nodes.get(k).lines.get(w).getY1()) < epsilon) ||
                                            (Math.abs(tree.nodes.get(i).lines.get(q).getY1() - tree.nodes.get(i).nodes.get(k).lines.get(w).getY2()) < epsilon) ||
                                            (Math.abs(tree.nodes.get(i).lines.get(q).getY2() - tree.nodes.get(i).nodes.get(k).lines.get(w).getY1()) < epsilon) ||
                                            (Math.abs(tree.nodes.get(i).lines.get(q).getY2() - tree.nodes.get(i).nodes.get(k).lines.get(w).getY2()) < epsilon)

                            );
                            if ((!holyshitwhy) && tree.nodes.get(i).lines.get(q).intersectsLine(tree.nodes.get(i).nodes.get(k).lines.get(w))) {
                                flag = true;
                            }
                        }
                    }
                }
            }
            if (!flag) {
                counter = 999999;
            }
        }
        frame.repaint();
    }

    public void waitTillNext() throws InterruptedException {
        i = 0;
        while (i<999999999 && (!nextPls)){
            i++;
            TimeUnit.MILLISECONDS.sleep(1);
        }

        System.out.println(i +" "+ nextPls);
        nextPls = false;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button1){
            refresh();
        }
        if(e.getSource() == button2){
            nextPls = true;
            System.out.println(nextPls);
        }
    }
}