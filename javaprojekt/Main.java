import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;

public class Main {
    public static void main(String[] args) {
        String file1 = "Test1_4_4.txt";
        String file2 = "Test2_4_4_MixGrid.txt";
        String file3 = "Test3_31_31_kwadrat.txt";
        String file4 = "Test4_31_31_trapez.txt";
        String file = "";
        Scanner scanner = new Scanner(System.in);

        //ilosc punktow calkowania
        int n = 3;   //2-4
        if(n<1 || n>4) {
            System.out.println("Nie ma takiej ilosci punktow calkowania");
            System.exit(0);
        }

        //wybor siatki
        file = file1;
        //file = file2;
        //file = file3;
        //file = file4;
        int siatka = 1;



        String filee =  "C:\\Users\\Jakub\\Desktop\\cwiczenia\\lab1\\" + file;
        GlobalData gd = new GlobalData(filee); //utworzenie obiektu do przechowywania danych globalnych
        gd.setGlobalData(); //pobranie danych z pliku
        gd.printGlobalData(); //wypisanie danych globalnych
        System.out.println("----------------------------------------------------");
        Grid grid = new Grid(gd.elementsNumber,gd.nodesNumber, filee, siatka); //utworzenie obiektu do przechowywania siatki
        grid.setNodes(); //pobranie danych o węzłach z pliku
        grid.setElements(); //pobranie danych o elementach z pliku
        grid.setBC(); //pobranie danych o BC z pliku
        grid.printNodes(); //wypisanie danych o węzłach
        System.out.println("----------------------------------------------------");
        grid.printElements(); //wypisanie danych o elementach
        System.out.println("----------------------------------------------------");


        Gauss gauss = new Gauss(n);

        ElementUniwersalny element = new ElementUniwersalny(n);
        element.tabKsiEta(n);
        element.surfaces();


        int iterator = 0;
        Agregate agregacja = new Agregate(gd);

        for (Element element1 : grid.elements) {
            double[] tabX = new double[4];
            double[] tabY = new double[4];
            Node[] nodes = new Node[4];

            for (int i = 0; i < 4; i++) {
                tabX[i] = grid.nodes[element1.ID[i]-1].x;
                tabY[i] = grid.nodes[element1.ID[i]-1].y;
                //System.out.print(" x"+i+": "+tabX[i]);
                nodes[i] = grid.nodes[element1.ID[i]-1];
            }
            H h = new H(n, element , gd, gauss, tabX, tabY);
            Hbc hbc = new Hbc(n, element, gd, nodes);

            h.compute();
            element1.tabH = h.resultH();
            element1.tabC = h.resultC();


            hbc.compute();
            element1.tabHBC = hbc.resultHBC();
            element1.wekP = hbc.resultP();

            System.out.println("H dla Element: " + iterator);
            printMatrix(element1.tabH, 4);
            System.out.println("HBC dla Element: " + iterator);
            printMatrix(element1.tabHBC, 4);
            System.out.println("P dla Element: " + iterator);
            printWektor(element1.wekP, 4);
            System.out.println("C dla Element: " + iterator);
            printMatrix(element1.tabC, 4);

            for(int j = 0; j < 4; j++){
                for(int i = 0; i < 4; i++){
                    element1.tabHplusHBC[j][i] = element1.tabH[j][i] + element1.tabHBC[j][i];
                }
            }
            System.out.println("H+HBC dla Element: " + iterator);
            printMatrix(element1.tabHplusHBC, 4);
            iterator++;

            agregacja.agregate2d(element1);
            agregacja.agregate1d(element1);
        }


        double[] T0 = new double[gd.nodesNumber];
        for(int i = 0; i < gd.nodesNumber; i++){
            T0[i] = gd.initialTemp;
        }


        for (int i = 0; i< 59; i++){
            T0 = agregacja.temperatureCalculate(T0);
           double tmin = Arrays.stream(T0).min().orElse(0);
           double tmax = Arrays.stream(T0).max().orElse(0);
           System.out.println("tmin: " + tmin + ", tmax: " + tmax);
        }

        System.out.println("Macierz H globalna: ");
        printMatrix(agregacja.HG,16);

        System.out.println("Wektor P globalny:");
        printWektor(agregacja.PG, 16);

        System.out.println("Macierz C globalna: ");
        printMatrix(agregacja.CG,16);

        System.out.println("Wektor T1: ");
        printWektor(T0, 16);




        //lab2 prezentacja gauss

        double res=0;
        Gauss g = new Gauss(3);
        for (int i=0; i<3;i++){
            for(int j = 0; j<3;j++){
                res += g.weights.get(i) * g.weights.get(j) *f(g.nodes.get(i), g.nodes.get(j));
            }
        }
        System.out.println("wynik: " + res);


        double result=0;
        Gauss g1 = new Gauss(4);
        for (int i=0; i<4;i++){
            for(int j = 0; j<4;j++){
                result += g1.weights.get(i) * g1.weights.get(j) *f(g1.nodes.get(i), g1.nodes.get(j));
            }
        }

        System.out.println("wynik: " + result);
    }

    static double f(double x, double y){
        return (x*x*x*x*y*y*y) - (2*x*x) + (6*x*y*y*y) + 5;
    }


    static void printMatrix(double[][] tab, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%.3f" + " ", tab[i][j]);
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
    }

    static void printWektor(double[] tab, int n){
        for (int i = 0; i < n; i++){
            System.out.printf("%.3f" + " ", tab[i]);
        }
        System.out.println();
        System.out.println("----------------------------------------------------");




    }
}
