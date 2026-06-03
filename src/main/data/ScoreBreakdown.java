package main.data;

public class ScoreBreakdown {

    public int fu;
    public int strength;
    public int han;
    public double multiplier;

    public ScoreBreakdown() {
        this.fu = 0;
        this.strength = 0;
        this.han = 0;
        this.multiplier = 1;
    }

    public static ScoreBreakdown cross(ScoreBreakdown a, ScoreBreakdown b) {
        ScoreBreakdown res = new ScoreBreakdown();
        res.fu = a.fu + b.fu;
        res.strength = a.strength + b.strength;
        res.han = a.han + b.han;
        res.multiplier = a.multiplier * b.multiplier;
        return res;
    }
}
