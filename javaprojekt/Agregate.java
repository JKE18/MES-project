import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import static org.apache.commons.math3.linear.MatrixUtils.createRealMatrix;
import static org.apache.commons.math3.linear.MatrixUtils.createRealVector;

public class Agregate {
    int nofNodes;
    double[][] HG;
    double[] PG;
    double[][] CG;
    double dTau;

    Agregate(GlobalData gd){
        nofNodes = gd.nodesNumber;
        HG = new double[nofNodes][nofNodes];
        PG = new double[nofNodes];
        CG = new double[nofNodes][nofNodes];
        dTau = gd.simulationStepTime;

    }

    void agregate2d(Element e){
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                HG[e.ID[j] - 1][e.ID[i] - 1] += e.tabHplusHBC[j][i];
                CG[e.ID[j] - 1][e.ID[i] - 1] += e.tabC[j][i];
            }
        }
    }
    void agregate1d(Element e){
        for(int i = 0; i < 4; i++){
            PG[e.ID[i] - 1] += e.wekP[i];
        }
    }

    double[] temperatureCalculate(double[] T0){

        double[][] Lew = new double[nofNodes][nofNodes];
        double[] Praw = new double[nofNodes];
        double[][] Htemp = new double[nofNodes][nofNodes];
        double[][] Ctemp = new double[nofNodes][nofNodes];
        double[] Ptemp = new double[nofNodes];

        for(int i = 0; i < nofNodes; i++){
            for(int j = 0; j < nofNodes; j++){
                Htemp[i][j] = HG[i][j];
                Ctemp[i][j] = CG[i][j];
            }
            Ptemp[i] = PG[i];
        }

        for(int i = 0; i < nofNodes; i++){
            for(int j = 0; j < nofNodes; j++){
                Ctemp[i][j] = Ctemp[i][j]/dTau;
                Praw[i] += Ctemp[i][j] * T0[j];
                Lew[i][j] = Ctemp[i][j] + Htemp[i][j];
            }
            Praw[i] += Ptemp[i];
        }
        return CalculateMatrix(Lew, Praw);
    }

    //lu decomposition - metoda rowziazywania rownan liniowych
    double[] CalculateMatrix(double[][] A, double[] B){
        RealMatrix RM = createRealMatrix(A);
        RealVector RV = createRealVector(B);
        DecompositionSolver solver = new LUDecomposition(RM).getSolver();
        RealVector result = solver.solve(RV);
        return result.toArray();
    }
}
