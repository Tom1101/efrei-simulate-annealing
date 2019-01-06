import java.util.List;

class randomPlaces {
    private int rand_place_bras;
    private int place_before_jump;
    private List<String> tools;
    private List<Integer> placeNumbers;
    private int operationNumber;
    private List<String> durations;
    private int unit_time;
    private int cout_res;
    private int firstPlace;

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
            //System.out.println("Place Random: " + rand_place_bras);
            //System.out.println(i + 1 + " Tool Place: " + placeNumbers.get(Integer.parseInt(tools.get(i))));
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

    // Calculation Total Cost of Left Neighboring Place
    private void voisinsGauche(int times, double T) {
        for (int i = 1; i <= times; i++) {
            setRand_place_bras(this.place_before_jump);
            setCout_res(0);
            this.rand_place_bras = this.rand_place_bras - i;
            calculationCout();
            System.out.println("Total Cout place -" + i + ": " + cout_res + ", Probability: " + calculProbability(this.firstPlace, cout_res, T));
        }
    }

    // Calculation Total Cost of Right Neighboring Place
    private void voisinsDroite(int times, double T) {
        for (int i = 1; i <= times; i++) {
            setRand_place_bras(this.place_before_jump);
            setCout_res(0);
            this.rand_place_bras = this.rand_place_bras + i;
            calculationCout();
            System.out.println("Total Cout place +" + i + ": " + cout_res + ", Probability: " + calculProbability(this.firstPlace, cout_res, T));
        }
    }

    /**
     * Calculation the acceptance probability
     * if resul > 1 then resul = 1.00
     * else resul = resul
     * @param cold
     * @param cnew
     * @param T
     * @return double
     */
    private double calculProbability(int cold, int cnew, double T) {
        double ap;
        ap = Math.exp((double)(cnew - cold) / T);
        if (Math.round(ap * 100) / 100.00 > 1.00) ap = 1.00;
        else ap = Math.round(ap * 100) / 100.000;
        return ap;
    }

    // Show out the first random places
    private void firstPlaces() {
        calculationCout();
        System.out.println("Total Cout first place: " + cout_res);
        this.firstPlace = cout_res;
    }

    // Solution
    void solution(int n, double T) {
        System.out.println("Temperature: " + T);
        this.firstPlaces();
        this.voisinsGauche(n, T);
        this.voisinsDroite(n, T);
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
}
