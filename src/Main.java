import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("test1.txt");
        try {
            // scan file
            Scanner s = new Scanner(file);
            // random
            Random rand = new Random();
            // tool number
            int toolNumber = Integer.parseInt(s.next());
            // place number
            int placeNumber = Integer.parseInt(s.next());
            // operation number
            int operationNumber = Integer.parseInt(s.next());
            // unit time
            int unit_time = Integer.parseInt(s.next());
            // random bras place
            int rand_place_bras;
            // list of tools
            List<String> tools = new ArrayList<String>();
            // list of durations
            List<String> durations = new ArrayList<String>();
            // list of number of places
            List<Integer> placeNumbers = new ArrayList<Integer>();
            // list auto places numbers
            List<Integer> autoPlaces = new ArrayList<Integer>(Collections.nCopies(placeNumber, 0));
            // add variables to tools list and durations list
            while (s.hasNext()) {
                tools.add(s.next());
                durations.add(s.next());
            }
            // add place number into list of place number
            for (int i = 0; i < placeNumber; i++) {
                placeNumbers.add(i);
            }
            // Shuffle to have a random places list
            Collections.shuffle(placeNumbers);
            // add into auto places list
            for (int i = 1; i <= toolNumber; i++) {
                autoPlaces.set(placeNumbers.get(i), i);
            }
            // i is temperature, n is number of random neighboring
            rand_place_bras = rand.nextInt(placeNumber);
            // Show out the random solution
            System.out.println(autoPlaces);
            // RandomPlaces Functions
            randomPlaces randomPlaces = new randomPlaces(rand_place_bras, tools, placeNumbers, operationNumber, durations, unit_time);
            System.out.println("Random Place: " + rand_place_bras);
            // Function Solution for finding the best solution with n is Iteration and T is Temperature
            randomPlaces.solution(10, 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

