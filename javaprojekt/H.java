public class H {
    double[] dXdKSI;
    double[] dYdKSI;
    double[] dXdETA;
    double[] dYdETA;
    double[][] jacob;
    double[][] dNdX;
    double[][] dNdY;
    double[] tabX; //= {0., 0.025, 0.025, 0.};
    double[] tabY;// = {0., 0., 0.025, 0.025};
    double[] detJ;
    double[] reverseDetJ;
    double[][] hdX;
    double[][] hdY;
    double[][] hpc;
    double[][] hpc2;
    double[][] hpc3;
    double[][] hpc4;
    double[][] H;
    int number;
    double kt;
    ElementUniwersalny EU;
    double[] waga;
    double specHeat;
    double density;
    double[][] cpc;
    double[][] cpc2;
    double[][] cpc3;
    double[][] cpc4;
    double[][] C;

    H(int number, ElementUniwersalny EU, GlobalData gd, Gauss gauss,double[] tabX, double[] tabY){
        this.number = number;
        this.EU = EU;
        this.tabX = tabX;
        this.tabY = tabY;
        dXdKSI = new double[number * number];
        dYdKSI = new double[number * number];
        dXdETA = new double[number * number];
        dYdETA = new double[number * number];
        detJ = new double[number * number];
        reverseDetJ = new double[number * number];
        jacob = new double[number * number][4];
        dNdX = new double[number * number][4];
        dNdY = new double[number * number][4];
        hdX = new double[number * number][4];
        hdY = new double[number * number][4];
        hpc = new double[number * number][4];
        hpc2 = new double[number * number][4];
        hpc3 = new double[number * number][4];
        hpc4 = new double[number * number][4];
        H = new double[4][4];
        kt = gd.conductivity;
        waga = new double[number];
        for(int i = 0; i < number; i++){
            waga[i] = gauss.weights.get(i);
        }
        specHeat = gd.specificHeat;
        density = gd.density;
        cpc = new double[number * number][4];
        cpc2 = new double[number * number][4];
        cpc3 = new double[number * number][4];
        cpc4 = new double[number * number][4];
        C = new double[4][4];

    }


    void dXYdKSIETA(){
        for (int j = 0; j < number * number; j++) {
            for (int i = 0; i < 4; i++) {
                dXdKSI[j] += EU.ksi[j][i] * tabX[i];
                dYdKSI[j] += EU.ksi[j][i] * tabY[i];
                dXdETA[j] += EU.eta[j][i] * tabX[i];
                dYdETA[j] += EU.eta[j][i] * tabY[i];
            }
        }
    }

    void jacob(){
        for (int j = 0; j < number * number; j++) {
            detJ[j] = (dXdKSI[j]*dYdETA[j])-(dYdKSI[j]*dXdETA[j]);
            reverseDetJ[j] =1/detJ[j];
            jacob[j][0] = dYdETA[j]* reverseDetJ[j];
            jacob[j][1] = -1.*dXdETA[j]* reverseDetJ[j];
            jacob[j][2] = -1.*dYdKSI[j]* reverseDetJ[j];
            jacob[j][3] = dXdKSI[j]* reverseDetJ[j];
        }
    }

    void dXdY(){
        for(int j = 0; j< number * number; j++){
            for(int i = 0; i < 4; i++){
                dNdX[j][i] = jacob[j][0] * EU.ksi[j][i] + jacob[j][2] * EU.eta[j][i];
                dNdY[j][i] = jacob[j][1] * EU.ksi[j][i] + jacob[j][3] * EU.eta[j][i];

            }
        }
    }
    void HCalculate(){
        for (int k = 0; k < number * number; k++){
            for(int j = 0; j < 4; j++){
                for(int i = 0; i < 4; i++){
                    hdX[k][i] = dNdX[k][j] * dNdX[k][i];
                    hdY[k][i]= dNdY[k][j] * dNdY[k][i];
                    hpc[k][i] = kt*(hdX[k][i] + hdY[k][i])*detJ[k];
                    H[j][i] += hpc[k][i]*waga[k % number]*waga[k / number];
                }
            }
        }
    }
    void CCalculate(){
        for(int k = 0; k < number * number; k++){
            for(int j = 0; j < 4; j++){
                for (int i =0; i < 4; i++){
                    cpc[k][i] = density * specHeat * detJ[k] * EU.ksieta[k][j] * EU.ksieta[k][i];
                    C[j][i] += cpc[k][i] * waga[k % number] * waga[k / number];
                }
            }
        }
       // print(C);
    }
    void compute(){
        dXYdKSIETA();
        jacob();
        dXdY();
        HCalculate();
        CCalculate();
    }
    double[][] resultH(){
        return H;
    }
    double[][] resultC(){
        return C;
    }
    void print(double[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%.6f" + " ", tab[i][j]);
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
    }
}
