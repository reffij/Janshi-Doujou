package main.analysis;

import java.util.List;

import main.data.*;
import main.gameobjects.heroes.Hero;
import main.yaku.Yaku;

public class Calculator {

    private static int fu(WinContext ctx) {
        int res = 22;
        if (ctx.isChiiToitsu) return 25;
        for (Meld meld : ctx.melds) {
            if (meld.getMeldType() == MeldType.PON) {
                if (meld.containsTerminal() || meld.containsOnlyHonors()) res += 8;
                else res += 4;
            } else if (meld.getMeldType() == MeldType.KAN) {
                if (meld.containsTerminal() || meld.containsOnlyHonors()) res += 32;
                else res += 16;
            }
        }
        if (
            ctx.pair.getInt() == ctx.seatWind.getInt()||
            ctx.pair.getInt() == ctx.prevelantWind.getInt() ||
            ctx.pair.isSangen()
        ) {
            res += 2;
        }

        //round by 10
        res = ((res + 9) / 10) * 10;
        return res;

    }

    private static int han(WinContext ctx) {
        int res = 0;
        for (Yaku yaku : ctx.yaku) {
            res += yaku.getHan();
        }
        return res;
    }

    public static Score evaluate(ScoreBreakdown sb) {
        Score res = new Score(1);
        int fu = sb.fu;
        int strength = sb.strength;
        int han = sb.han;
        double multiplier = sb.multiplier;

        res.multiply(fu);
        res.multiply(strength);
        res.multiply(multiplier);
        int i = 2 + han;
        while (i > 0) {
            res.multiply(2);
            i--;
        }

        return res;
    }

    public static ScoreBreakdown getBaseScoreBreakdown(WinContext ctx) {
        ScoreBreakdown res = new ScoreBreakdown();
        res.fu = fu(ctx);
        res.strength = ctx.playerStrength;
        res.han = han(ctx);
        res.multiplier = 1;

        return res;
    }

    public static Score finalEvaluation(WinContext ctx) {
        ScoreBreakdown fsb = new ScoreBreakdown();
        fsb = ScoreBreakdown.cross(fsb, ctx.baseScoreBreakdown);
        for (Hero hero : ctx.HeroModifiers.keySet()) {
            ScoreBreakdown sb = ctx.HeroModifiers.get(hero);
            fsb = ScoreBreakdown.cross(fsb, sb);
        }
        ctx.finalScoreBreakdown = fsb;
        Score res = evaluate(fsb);
        res.roundUpByExponent(2);
        ctx.finalScore = res;
        return res;
    }

    public static WinContext findBestWinContext(List<WinContext> ctxs) {
        if (ctxs.size() < 1) throw new Error("Must have atleast one win context");
        WinContext res = null;
        Score maxScore = new Score(1);
        for (WinContext ctx : ctxs) {
            Score newScore = evaluate(ctx.baseScoreBreakdown);
            if (maxScore.compareTo(newScore) <= 0) {
                maxScore = newScore;
                res = ctx;
            }
        }
        return res;
    }
}
