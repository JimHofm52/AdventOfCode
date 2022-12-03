package type;

/**
 * Type for Rock, Paper, Sissors
 * <p> rpsOpp A, B & C is Rock, Paper & Scissors
 * <p> rpsYou X, Y & Z is Rock, Paper & Scissors (part 1) or
 * <p> rpsYou X, Y & Z is Lose, Tie & Win (part 2)
 */
public class T_Rps {
    public char rpsOpp;
    public char rpsYou;

    public T_Rps(char opp, char you){
        this.rpsOpp = opp;
        this.rpsYou = you;
    }
}
