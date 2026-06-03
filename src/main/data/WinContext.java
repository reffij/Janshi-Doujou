package main.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.gameobjects.heroes.Hero;
import main.gameobjects.pools.tilepools.Hand;
import main.gameobjects.tiles.Tile;
import main.yaku.Yaku;

import java.util.HashMap;


public class WinContext {
    public Tile winningTile;
    public TilePrimitive pair;
    public List<Meld> melds;
    public Meld winningMeld;
    public WaitType waitType;

    public HandCountStack handCountStack;
    public Hand hand;

    public boolean isFirstTurn;
    public boolean isLastTurn;
    public boolean isAfterKan;

    public TilePrimitive seatWind;
    public TilePrimitive prevelantWind;

    public boolean isKokuShiMuSou;
    public boolean isChiiToitsu;

    public int playerStrength;
    
    public List<TilePrimitive> doraIndicators;

    public List<Yaku> yaku;

    public Map<Hero, ScoreBreakdown> HeroModifiers;

    public ScoreBreakdown baseScoreBreakdown;
    public ScoreBreakdown finalScoreBreakdown;

    public Score finalScore;
    public int red;

    public WinContext() {

    }

    public WinContext(Tile winningTile, List<Meld> melds, Meld winningMeld, WaitType waitType, TilePrimitive pair) {
        this.winningTile = winningTile;
        this.melds = melds;
        this.winningMeld = winningMeld;
        this.waitType = waitType;
        this.pair = pair;
        this.yaku = new ArrayList<>();
        this.HeroModifiers = new HashMap<>();
        this.baseScoreBreakdown = new ScoreBreakdown();
        this.finalScoreBreakdown = new ScoreBreakdown();
    }

    public static WinContext KokuShiMuSou(Tile winningTile, TilePrimitive pair) {
        WinContext res = new WinContext();
        res.winningTile = winningTile;

        res.isKokuShiMuSou = true;
        res.isChiiToitsu = false;

        res.pair = pair;
        res.melds = null;

        res.yaku = new ArrayList<>();
        
        res.HeroModifiers = new HashMap<>();

        
        res.baseScoreBreakdown = new ScoreBreakdown();
        res.finalScoreBreakdown = new ScoreBreakdown();

        return res;
    }

    public static WinContext ChiiToiTsu(Tile winningTile) {
        WinContext res = new WinContext();
        res.winningTile = winningTile;

        res.isKokuShiMuSou = false;
        res.isChiiToitsu = true;

        res.pair = null;
        res.melds = null;

        res.yaku = new ArrayList<>();

        res.HeroModifiers = new HashMap<>();

        res.baseScoreBreakdown = new ScoreBreakdown();
        res.finalScoreBreakdown = new ScoreBreakdown();

        return res;
    }

    public void addYaku(Yaku yaku) {
        this.yaku.add(yaku);
    }

    public void addHeroModifiers(Hero hero, ScoreBreakdown sb) {
        this.HeroModifiers.put(hero, sb);
    }

    /* 
    public String toString() {
        String res = "";
        res += "Score: " + .toString();
        if (this.yaku == null) return res;
        int i = 0;
        for (Yaku y : this.yaku) {
            res += "(" + i +")" + y.toString() + " ";
            i++;
        }
        return res;
        
    }*/
}
