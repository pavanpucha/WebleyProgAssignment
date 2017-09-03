import java.io.File;
import java.util.*;
import java.util.logging.FileHandler;


public class OrderDishes {

    public static boolean noDishesFound = true; // to return no dishes found if there is not one, we have this variable

    public static void main(String[] args) {

        String fileName = "Data.csv";
        Map<Double, String> storedDishesValues = new HashMap<Double, String>();
        File dataFile = new File(fileName);
        ArrayList<Double> dishesPrice = new ArrayList<>();
        // read from file
        try {
            Scanner inputStream = new Scanner(dataFile);
            System.out.println("--------MENU--------");
            while (inputStream.hasNext()) {
                String csvData = inputStream.next();
                // Here we split the item name and file using a ',' and store them in a hashmap storedDishesValues.
                // The name as string and value as a double
                String[] Values = csvData.split(",");
                storedDishesValues.put(Double.parseDouble(Values[1]), Values[0]);
                dishesPrice.add(Double.parseDouble(Values[1]));
                System.out.println(Values[0] + " -> " + Values[1]);

            }
            inputStream.close();
            //Taking the input from command line
            Scanner inputScanner = new Scanner(System.in);
            System.out.println("Enter the price: ");

            double targetValue = inputScanner.nextDouble(); // the entered price is stored in targetValue variable
            outputDishes(dishesPrice, targetValue, new ArrayList<Double>(), storedDishesValues);
            if (noDishesFound) {
                System.out.println(" there is no combination of dishes that is equal to the target price.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
   /*
   Method to return the possible outputs
   The  method takes in the dishes values, target price , an empty list(in the beginning) and a hashmap so that we can return
   the dishes name associated with the price.

   The Algorithm is an example of recursive back tracking. It generates all possible combinations, stores them in a list and if the sum of each-
   combination equals the target price it prints the values on screen.

   Firstly, the algorithm calculates the sum of the items present in the list, if the value meets required value, we print the menu
   Once we get a combination, we dont have use for it anymore. so we exit the method.

    Similarly we try all possible combinations and check for other possible dishes to meet the output criteria.



    */


    private static void outputDishes(ArrayList<Double> dishes, double targetPrice, ArrayList<Double> subsetDishes,
                                     Map<Double, String> sampleMap) {
        double sumDishes = 0.0;
        /*
        finding sum of the temporary list and if it matches the targetPrice, we return the dishes
         */
        for (double d : subsetDishes) {
            sumDishes += d;
        }

        if (sumDishes == targetPrice) {
            noDishesFound = false;
            System.out.print("you can buy ");
            for (double d : subsetDishes) {
                System.out.print(sampleMap.get(d) + " ");
            }
            System.out.print(" for the price of $" + targetPrice);
        }
        if (sumDishes >= targetPrice)
            return;
        /*
        in the loop, we have two lists the partial list is supplemented by the first element of the list
         @param : dishes that we passedand the remaining list is rest of the elements next to ith element and
         recursively call the outputDishes method
         */
        for (int i = 0; i < dishes.size(); i++) {
            ArrayList<Double> remainingDishes = new ArrayList<Double>();
            double addToPartial = dishes.get(i);
            for (int j = i + 1; j < dishes.size(); j++)
                remainingDishes.add(dishes.get(j));
            ArrayList<Double> subsetDishesPartial = new ArrayList<Double>(subsetDishes);
            subsetDishesPartial.add(addToPartial);
            outputDishes(remainingDishes, targetPrice, subsetDishesPartial, sampleMap);
        }
    }
}
