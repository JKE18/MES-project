import java.util.ArrayList;
public class Gauss {

    ArrayList<Double> nodes = new ArrayList<>();
    ArrayList<Double> weights = new ArrayList<>();


    Gauss(int number){
        if(number == 2){
            nodes.add((-1.)*Math.sqrt(1./3.));
            nodes.add(Math.sqrt(1./3.));
            weights.add(1.);
            weights.add(1.);

//         System.out.println(nodes);
//         System.out.println(weights);


        } else if (number == 3) {
            nodes.add((-1.)*Math.sqrt(3./5));
            nodes.add(0.);
            nodes.add(Math.sqrt(3./5.));
            weights.add(5./9.);
            weights.add(8./9.);
            weights.add(5./9.);

//            System.out.println(nodes);
//            System.out.println(weights);

        } else if (number == 4) {
            nodes.add(Math.sqrt((3./7. + 2./7. * Math.sqrt(6./5.)))*(-1.0));
            nodes.add(Math.sqrt((3./7. - 2./7. * Math.sqrt(6./5.)))*(-1.0));
            nodes.add(Math.sqrt
                    (3./7. - 2./7. * Math.sqrt(6./5.)));
            nodes.add(Math.sqrt
                    (3./7. + 2./7. * Math.sqrt(6./5.)));
            weights.add((18. - Math.sqrt(30.))/36.);
            weights.add((18. + Math.sqrt(30.))/36.);
            weights.add((18. + Math.sqrt(30.))/36.);
            weights.add((18. - Math.sqrt(30.))/36.);

           //System.out.println(nodes);
          // System.out.println(weights);
            
        }
    }

}
