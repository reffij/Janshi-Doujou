package main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import engine.commands.CommandBus;
import engine.events.EventBus;
import main.data.HandCountStack;
import main.data.Score;
import main.data.TilePrimitive;
import main.data.TileSuit;
import main.gameobjects.market.Market;
import main.gameobjects.player.Player;
import main.gameobjects.pools.heropools.HeroPool;
import main.gameobjects.pools.tilepools.CallPool;
import main.gameobjects.pools.tilepools.DiscardPool;
import main.gameobjects.pools.tilepools.DoraWall;
import main.gameobjects.pools.tilepools.DrawPool;
import main.gameobjects.pools.tilepools.Hand;
import main.gameobjects.pools.tilepools.TilePool;
import main.gameobjects.pools.tradingcardpools.TradingCardDiscardPool;
import main.gameobjects.pools.tradingcardpools.TradingCardDrawPool;
import main.gameobjects.pools.tradingcardpools.TradingCardHandPool;
import main.gameobjects.tiles.Tile;

public class JDGameContext {

    public CommandBus<JDCommandType, JDGameContext> cmdBus;
    public EventBus<JDGameContext> eventBus;

    public void setEventCMDbus(
        EventBus<JDGameContext> eventBus, 
        CommandBus<JDCommandType, JDGameContext> cmdBus
    ) {
        this.cmdBus = cmdBus;
        this.eventBus = eventBus;
    }

    //Player
    public int money;
    public int mp;
    public int maxMp;
    public int strength;

    public int doraSkill, drawSkill, swapSkill;
    public int tcDrawSkill, tcDrawPrice;

    //TilePools
    public Hand hand;
    public TilePool tilePool;
    public DrawPool drawPool;
    public DiscardPool discardPool;
    public DoraWall doraWall;
    public CallPool callPool;
    public boolean autoSort;

    public Tile tsumoTile;

    //Hero pool
    public HeroPool heroPool;
    
    //Trading Card Pools
    public TradingCardDrawPool tcDrawPool;
    public TradingCardDiscardPool tcDiscardPool;
    public TradingCardHandPool tcHandPool;

    //round specific
    public Score currentScore;
    public Score targetScore;

    public TilePrimitive seatWind;
    public TilePrimitive prevWind;
    public int round;
    public int rotation;
    public GamePhase phase;

    public HandCountStack handCount;
    public Market market;


    public Queue<Consumer<Tile>> onDiscardQueue;
    
    public void init(long seed) {
        this.round = 1;
        this.rotation = 1;
        this.phase = GamePhase.SELECT_PHASE;
        this.prevWind = new TilePrimitive(1, TileSuit.KAZE);
        this.seatWind = new TilePrimitive(1, TileSuit.KAZE);

        this.market = new Market(seed);

        //Tile Pools
        this.hand = new Hand();
        this.tilePool = new TilePool(seed);
        this.drawPool = new DrawPool(seed);
        this.discardPool = new DiscardPool();
        this.callPool = new CallPool();
        this.doraWall = new DoraWall();
        this.autoSort = true;
        this.handCount = new HandCountStack();
        this.tsumoTile = null;

        this.currentScore = new Score(0);
        this.targetScore = new Score(0);
    
        //heros
        this.heroPool = new HeroPool(5);

        //trading cards
        this.tcDrawPool = new TradingCardDrawPool(seed);
        this.tcHandPool = new TradingCardHandPool(4);
        this.tcDiscardPool = new TradingCardDiscardPool();

        this.onDiscardQueue = new LinkedList<>();
    }
}
