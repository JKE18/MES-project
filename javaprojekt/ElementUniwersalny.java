import java.util.function.BiFunction;
import java.util.function.Function;
public class ElementUniwersalny {
    double[][] ksi;
    double[][] eta;
    int number;
    Function<Double, Double>[] f_ksi = new Function[4];
    Function<Double, Double>[] f_eta = new Function[4];
    BiFunction<Double, Double, Double>[] f_ksieta = new BiFunction[4];
    Gauss g;
    Surface[] sur;
    double[][] ksieta;
    ElementUniwersalny(int number) {
        this.number = number;
        ksi = new double[number * number][4];
        eta = new double[number * number][4];
        f();
        g = new Gauss(number);
        sur = new Surface[4];
        for (int i = 0; i < 4; i++){
            sur[i] = new Surface(number);
        }
        ksieta = new double[number * number][4];
    }
    // funkcje ksztaltu
    void f() {
        f_ksi[0] = ksi -> -0.25 * (1 - ksi);
        f_ksi[1] = ksi -> 0.25 * (1 - ksi);
        f_ksi[2] = ksi -> 0.25 * (1 + ksi);
        f_ksi[3] = ksi -> -0.25 * (1 + ksi);

        f_eta[0] = eta -> -0.25 * (1 - eta);
        f_eta[1] = eta -> -0.25 * (1 + eta);
        f_eta[2] = eta -> 0.25 * (1 + eta);
        f_eta[3] = eta -> 0.25 * (1 - eta);

        f_ksieta[0] = (ksi, eta) -> 0.25 * (1 - ksi)*(1 - eta);
        f_ksieta[1] = (ksi, eta) -> 0.25 * (1 + ksi)*(1 - eta);
        f_ksieta[2] = (ksi, eta) -> 0.25 * (1 + ksi)*(1 + eta);
        f_ksieta[3] = (ksi, eta) -> 0.25 * (1 - ksi)*(1 + eta);
    }
    // tablica wartosci funkcji ksztaltu w punktach calkowania
    void tabKsiEta(int number) {
        if (number == 2) {
            double p1 = (-1.)*Math.sqrt(1./3.);
            double p2 = Math.sqrt(1./3.);
            double[] p = {p1, p2};

            for (int j = 0; j < number * number; j++) {
                for (int i = 0; i < 4; i++) {
                    eta[j][i] = f_eta[i].apply(p[j % number]);
                    if(j == 0){
                        ksi[j][i] = f_ksi[i].apply(p[0]);
                        ksieta[j][i] = f_ksieta[i].apply(p[0] , p[0]);
                    }
                    else if(j == 1){
                        ksi[j][i] = f_ksi[i].apply(p[0]);
                        ksieta[j][i] = f_ksieta[i].apply(p[1] , p[0]);
                    }
                    else if(j == 2) {
                        ksi[j][i] = f_ksi[i].apply(p[1]);
                        ksieta[j][i] = f_ksieta[i].apply(p[1] , p[1]);
                    }
                    else {
                        ksi[j][i] = f_ksi[i].apply(p[1]);
                        ksieta[j][i] = f_ksieta[i].apply(p[0] , p[1]);
                    }
                }
            }
            System.out.println("ksi");
            print(ksi);
            System.out.println("eta");
            print(eta);
            System.out.println("ksieta");
            print(ksieta);

        }

        if (number == 3) {
            double p1 = (-1.)*Math.sqrt(3./5);
            double p2 = 0.;
            double p3 = Math.sqrt(3./5.);
            double[] p = {p1, p2, p3};
            for (int j = 0; j < number * number; j++) {
                for (int i = 0; i < 4; i++) {
                    eta[j][i] = f_eta[i].apply(p[j % number]);
                    if (j < 3) {
                        ksi[j][i] = f_ksi[i].apply(p[0]);
                        ksieta[j][i] = f_ksieta[i].apply(p[j % number] , p[0]);
                    }
                    else if (j < 6) {
                        ksi[j][i] = f_ksi[i].apply(p[1]);
                        ksieta[j][i] = f_ksieta[i].apply(p[j % number] , p[1]);
                    }
                    else {
                        ksi[j][i] = f_ksi[i].apply(p[2]);
                        ksieta[j][i] = f_ksieta[i].apply(p[j % number] , p[2]);
                    }
                }

            }
            System.out.println("ksi");
            print(ksi);
            System.out.println("eta");
            print(eta);
            System.out.println("ksieta");
            print(ksieta);

        }
        if (number == 4) {
            double p1 = (-1.) * Math.sqrt(3./7. + 2./7. * Math.sqrt(6./5.));
            double p2 = (-1.) * Math.sqrt(3./7. - 2./7. * Math.sqrt(6./5.));
            double p3 = Math.sqrt(3./7. - 2./7. * Math.sqrt(6./5.));
            double p4 = Math.sqrt(3./7. + 2./7. * Math.sqrt(6./5.));
            double[] p = {p1, p2, p3, p4};
            for (int j = 0; j < number * number; j++) {
                for (int i = 0; i < 4; i++) {
                    eta[j][i] = f_eta[i].apply(p[j % number]);
                   if (j < 4) {
                       ksi[j][i] = f_ksi[i].apply(p1);
                       ksieta[j][i] = f_ksieta[i].apply(p[j % number] , p[0]);
                   }
                    else if (j < 8) {
                       ksi[j][i] = f_ksi[i].apply(p2);
                       ksieta[j][i] = f_ksieta[i].apply(p[j % number] , p[1]);
                   }
                    else if (j < 12) {
                       ksi[j][i] = f_ksi[i].apply(p3);
                       ksieta[j][i] = f_ksieta[i].apply(p[j % number] , p[2]);
                   }
                    else {
                       ksi[j][i] = f_ksi[i].apply(p4);
                       ksieta[j][i] = f_ksieta[i].apply(p[j % number] , p[3]);
                   }

                }

            }
            System.out.println("ksi");
            print(ksi);
            System.out.println("eta");
           print(eta);
            System.out.println("ksieta");
            print(ksieta);

        }
    }

    //obliczanie warotsci danych funkcji ksztaltu w pkt calkowania na scianach elementu
    void surfaces(){
        for(int j = 0; j < number; j++){
            for(int i = 0; i < 4; i++){
                sur[0].N[j][i] = f_ksieta[i].apply(g.nodes.get(j), -1.);
                sur[1].N[j][i] = f_ksieta[i].apply(1., g.nodes.get(j));
                sur[2].N[j][i] = f_ksieta[i].apply(g.nodes.get((number - 1) - j) , 1.);
                sur[3].N[j][i] = f_ksieta[i].apply(-1., g.nodes.get((number - 1) - j) );

            }
        }
    }



    void print(double[][] tab) {
        for (int i = 0; i < number * number; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%.6f" + " ", tab[i][j]);
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
    }
    /*
    void print2(double[][][] tab) {
        for(int h = 0; h < number * number; h++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.printf("%.6f" + " ", tab[h][i][j]);
                }
                System.out.println();
            }
        }
        System.out.println("----------------------------------------------------");
    }
*/
}



