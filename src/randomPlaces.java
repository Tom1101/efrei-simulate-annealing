import java.util.Collections;
import java.util.List;
import java.util.Random;

class randomPlaces {
    private int rand_place_bras;
    private int place_before_jump;
    private List<String> tools;
    private List<Integer> placeNumbers;
    private int operationNumber;
    private List<String> durations;
    private int unit_time;
    private int cout_res;
    private int Cold;
    private int Cnew;

    // Construction
    randomPlaces(int rand_place_bras, List<String> tools, List<Integer> placeNumbers, int operationNumber, List<String> durations, int unit_time) {
        this.rand_place_bras = rand_place_bras;
        this.tools = tools;
        this.durations = durations;
        this.operationNumber = operationNumber;
        this.placeNumbers = placeNumbers;
        this.unit_time = unit_time;
        this.place_before_jump = rand_place_bras;
    }

    // Calculation Total Cost
    private int calculationCout() {
        for (int i = 0; i < operationNumber; i++) {
            int cout;
            if (i == 0) {
                int cal_cout = Math.abs(rand_place_bras - placeNumbers.get(Integer.parseInt(tools.get(i)))) * unit_time;
                cout = (cal_cout > 0) ? cal_cout : 0;
                cout_res += cout;
            } else {
                int cal_cout = Math.abs(rand_place_bras - placeNumbers.get(Integer.parseInt(tools.get(i)))) * unit_time - Integer.parseInt(durations.get(i - 1));
                cout = (cal_cout > 0) ? cal_cout : 0;
                cout_res += cout;
            }
            rand_place_bras -= (rand_place_bras - placeNumbers.get(Integer.parseInt(tools.get(i))));
        }
        return cout_res;
    }

    /**
     * Function for finding the random neighboring solution
     * @return int
     */
    private int randomNeighboring() {
        setRand_place_bras(this.place_before_jump);
        setCout_res(0);
        int e1 = getRandomNumberInRange(1, 5);
        int e2 = getRandomNumberInRange(1, 5);
        while (e1 == e2) {
            e2 = getRandomNumberInRange(1, 5);
        }
        Collections.swap(placeNumbers, e1, e2);
        System.out.println("Switching the position of tool " +e1+" to the position of tool "+e2);
        return calculationCout();
    }


    /**
     * Calculation the acceptance probability
     * if resul > 1 then resul = 1.00
     * else resul = resul
     *
     * @param cold
     * @param cnew
     * @param T
     * @return double
     */
    private double calculProbability(int cold, int cnew, double T) {
        double ap;
        ap = Math.exp((double) (cnew - cold) / (T));
        if (Math.round(ap * 100) / 100.00 > 1.00) ap = 1.00;
        else ap = Math.round(ap * 100) / 100.00;
        return ap;
    }

    // Show out the first random places
    private void firstPlaces() {
        calculationCout();
        System.out.println("Total Cost of first random place: " + cout_res);
        this.Cold = cout_res;
    }

    // Solution
    void solution(int n, double T) {
        //Show out Temperature and First Random Place
        System.out.println("Temperature: " + T);
        this.firstPlaces();
        System.out.println();
        // Loop for number of iteration -> n
        for (int i = 0; i < n; i++) {
            this.Cnew = this.randomNeighboring();
            // If Cnew < Cold -> Move to the new solution
            if (this.Cnew < this.Cold) {
                this.Cold = this.Cnew;
                double alpha = 0.9;
                //Decreased T at Alpha
                T *= alpha;
                System.out.println("Total Cost of neighboring place : " + this.Cnew + ", Temperature: " + Math.round(T * 100) / 100.00);
                System.out.println();
            } else { // Else Cnew > Cold -> Maybe move to the new solution
                // Calculation the acceptance probability
                double ap = calculProbability(this.Cold, this.Cnew, T);
                // Generate a random double number
                double randomNumber = Math.random();
                //If the acceptance probability > randomNumber -> Move to the new solution
                if( ap > randomNumber) {
                    this.Cold = this.Cnew;
                    double alpha = 0.9;
                    T *= alpha;
                    System.out.println("Total Cost of neighboring place: " + this.Cnew + ", Probability: " + ap+ ", Temperature: " + Math.round(T * 100) / 100.00);
                    System.out.println();
                } else { //Rest in this solution
                    double alpha = 0.9;
                    T *= alpha;
                    System.out.println("Total Cost of neighboring place: " + this.Cold + ", Probability: " + ap+ ", Temperature: " + Math.round(T * 100) / 100.00);
                    System.out.println();
                }
            }
        }
    }

    public int getCout_res() {
        return cout_res;
    }

    public void setCout_res(int cout_res) {
        this.cout_res = cout_res;
    }

    public int getRand_place_bras() {
        return rand_place_bras;
    }

    public void setRand_place_bras(int rand_place_bras) {
        this.rand_place_bras = rand_place_bras;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
