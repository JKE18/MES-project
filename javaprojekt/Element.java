public class Element {
    int[] ID;
    double[][] tabH;
    double[][] tabHBC;
    double[] wekP;
    double[][] tabHplusHBC;
    double[][] tabC;

    Element(){
        ID = new int[4];
        tabH = new double[4][4];
        tabHBC = new double[4][4];
        wekP = new double[4];
        tabHplusHBC = new double[4][4];
        tabC = new double[4][4];
    }

}
