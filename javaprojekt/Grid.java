import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class Grid {
    Scanner scanner;
    Element[] elements;
    Node[] nodes;
    int nrsiatki;

    Grid(int elementsNumber, int nodesNumber, String filePath, int nrsiatki) {
        elements = new Element[elementsNumber];
        nodes = new Node[nodesNumber];
        this.nrsiatki= nrsiatki;

        File file = new File(filePath);
        try{
            this.scanner = new Scanner(file);
        }
        catch (IOException exception) {
            exception.getMessage();
        }
    }

    void setNodes() {
        while (!(scanner.next().equals("*Node"))) {
            scanner.nextLine();
        }

        for(int i=0; i< nodes.length; i++) {
            Node node = new Node();
            scanner.next();
            //wyłuskanie wsp
            node.x = Double.parseDouble(scanner.next().
                    replace(",",""));
            node.y = Double.parseDouble(scanner.next().
                    replace(",",""));
            nodes[i] = node;
        }
    }

    void setElements() {
        scanner.next();
        scanner.next();
        for(int i=0; i< elements.length; i++) {
            Element element = new Element();
            element.ID = new int[4];
            if(nrsiatki==3 || nrsiatki==4)
                scanner.next();
            for(int j=0; j<4; j++) {
                element.ID[j] = Integer.parseInt(scanner.next().replace(",", ""));
            }
            elements[i] = element;
        }
    }
    void setBC(){
        scanner.next();
        while(scanner.hasNext()){
            nodes[Integer.parseInt(scanner.next().
                    replace(",","")) - 1].BC = 1;
        }
    }

    void printNodes() {
        for(int i=0; i< nodes.length; i++) {
            System.out.println("Node "+ i + " x="+ nodes[i].x + " y="+ nodes[i].y + " , BC=" + nodes[i].BC);

        }
    }

    void printElements() {
        for(int i=0; i< elements.length; i++) {
            System.out.println("Element " + i + ": " + elements[i].ID[0] +
                    " " + elements[i].ID[1]+ " " + elements[i].ID[2]+ " " +
                    elements[i].ID[3]);

        }
    }

}
