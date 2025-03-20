import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class GlobalData {
    Scanner scanner;
    public int simulationTime,simulationStepTime,
            conductivity,alfa,tot,initialTemp,
            density,specificHeat,nodesNumber,elementsNumber;

    GlobalData(String filePath)
    {
        File fileP = new File(filePath);
        try{
            this.scanner = new Scanner(fileP);
        }
        catch (IOException exception) {
            exception.getMessage();
        }
    }
    public void setGlobalData()
    {
        scanner.next();
        this.simulationTime = Integer.parseInt(scanner.next());
        scanner.next();
        this.simulationStepTime = Integer.parseInt(scanner.next());
        scanner.next();
        this.conductivity = Integer.parseInt(scanner.next());
        scanner.next();
        this.alfa = Integer.parseInt(scanner.next());
        scanner.next();
        this.tot = Integer.parseInt(scanner.next());
        scanner.next();
        this.initialTemp = Integer.parseInt(scanner.next());
        scanner.next();
        this.density = Integer.parseInt(scanner.next());
        scanner.next();
        this.specificHeat= Integer.parseInt(scanner.next());
        scanner.next();
        scanner.next();
        this.nodesNumber= Integer.parseInt(scanner.next());
        scanner.next();
        scanner.next();
        this.elementsNumber= Integer.parseInt(scanner.next());

    }


    public void printGlobalData()
    {
        System.out.println("simulationTime: " + simulationTime);
        System.out.println("simulationStepTime: " + simulationStepTime);
        System.out.println("conductivity: " + conductivity);
        System.out.println("alfa: " + alfa);
        System.out.println("tot: " + tot);
        System.out.println("initialTemp: " + initialTemp);
        System.out.println("density: " + density);
        System.out.println("specificHeat: " + specificHeat);
        System.out.println("nodesNumber: " + nodesNumber);
        System.out.println("elementsNumber: " + elementsNumber);
    }

}
