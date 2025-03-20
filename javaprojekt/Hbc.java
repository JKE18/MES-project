public class Hbc {
    int number;
    ElementUniwersalny EU;
    double alfa;
    double L;
    double[] detJ; // dlugosc boku elementu/dlugosc uniwersalna elementu
    Node[] nodes;
    double[][][] temp;
    Gauss gauss;
    double[][] HBC;
    double[][] p;
    double Tot;
    double[] P;


    Hbc(int number, ElementUniwersalny EU, GlobalData gd, Node[] nodes){
        this.number = number;
        this.EU = EU;
        alfa = gd.alfa;
        this.nodes = nodes;
        L = 0;
        detJ = new double[4];
        temp = new double[4][4][4];
        this.gauss = new Gauss(number);
        HBC = new double[4][4];
        p = new double[4][4];
        Tot = gd.tot;
        P = new double[4];
    }
    void DETJ(){
        for(int i = 0; i < 3; i++){
            L = Math.sqrt(Math.pow((nodes[i+1].x - nodes[i].x), 2) + Math.pow((nodes[i+1].y - nodes[i].y), 2));
            detJ[i] = L/2;
        }
        L = Math.sqrt(Math.pow((nodes[0].x - nodes[3].x), 2) + Math.pow((nodes[0].y - nodes[3].y), 2));
        detJ[3] = L/2;
    }
    void calculateAllHBC(){
        for(int k = 0; k < number; k++) {
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    temp[0][j][i] += alfa * EU.sur[0].N[k][j] * EU.sur[0].N[k][i] * gauss.weights.get(k) * detJ[0];
                    temp[1][j][i] += alfa * EU.sur[1].N[k][j] * EU.sur[1].N[k][i] * gauss.weights.get(k) * detJ[1];
                    temp[2][j][i] += alfa * EU.sur[2].N[k][j] * EU.sur[2].N[k][i] * gauss.weights.get(k) * detJ[2];
                    temp[3][j][i] += alfa * EU.sur[3].N[k][j] * EU.sur[3].N[k][i] * gauss.weights.get(k) * detJ[3];

                }
                p[0][j] += alfa * EU.sur[0].N[k][j] * Tot * gauss.weights.get(k) * detJ[0];
                p[1][j] += alfa * EU.sur[1].N[k][j] * Tot * gauss.weights.get(k) * detJ[1];
                p[2][j] += alfa * EU.sur[2].N[k][j] * Tot * gauss.weights.get(k) * detJ[2];
                p[3][j] += alfa * EU.sur[3].N[k][j] * Tot * gauss.weights.get(k) * detJ[3];

                //System.out.println(EU.sur[0].N[k][j]);
            }
        }
    }

    void calculateHBC(){
        if(nodes[0].BC == 1 && nodes[1].BC == 1){
            for(int j = 0; j < 4; j++){
                for(int i = 0; i < 4; i++){
                    HBC[j][i] += temp[0][j][i];
                }
                P[j] += p[0][j];
            }
        }
        if(nodes[1].BC == 1 && nodes[2].BC == 1){
            for(int j = 0; j < 4; j++){
                for(int i = 0; i < 4; i++){
                    HBC[j][i] += temp[1][j][i];
                }
                P[j] += p[1][j];
            }
        }
        if(nodes[2].BC == 1 && nodes[3].BC == 1){
            for(int j = 0; j < 4; j++){
                for(int i = 0; i < 4; i++){
                    HBC[j][i] += temp[2][j][i];
                }
                P[j] += p[2][j];
            }
        }
        if(nodes[3].BC == 1 && nodes[0].BC == 1) {
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    HBC[j][i] += temp[3][j][i];
                }
                P[j] += p[3][j];
            }
        }
       // print(HBC);
        //System.out.println(P[0]+" "+P[1]+" "+P[2]+" "+P[3]);
    }

    void compute(){
        DETJ();
        calculateAllHBC();
        calculateHBC();
    }
    double[][] resultHBC(){
        return HBC;
    }
    double[] resultP(){
        return P;
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
}


