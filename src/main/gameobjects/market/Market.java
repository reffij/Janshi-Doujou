package main.gameobjects.market;

import java.util.*;

import main.gameobjects.heroes.Hero;
import main.gameobjects.heroes.HeroFactory;
import main.gameobjects.tiles.Tile;
import main.gameobjects.tradingcards.TradingCard;
import main.gameobjects.tradingcards.TradingCardFactory;


public class Market {

    Random rng;
    LootTable<Item> heroTable;
    LootTable<Item> tradingCardTable;
    LootTable<Item> tileTable;
    LootTable<Item> packTable;

    Map<LootTableType, LootTable<Item>> lootTables;
    LootTable<LootTableType> typeTable;

    ReservedItemSet reserved;

    List<Item> offers;
    int offerCapacity;
    int rerollPrice, initialRerollPrice;
    
    public Market(long seed) {
        this.rng = new Random(seed);

        this.initialRerollPrice = 5;
        this.rerollPrice = this.initialRerollPrice;

        this.offers = new ArrayList<>();
        this.offerCapacity = 6;

        this.reserved = new ReservedItemSet();

        double[] rates = {.8, .155, .0425, .0025};
        this.heroTable = new LootTable<Item>(rates, seed);
        this.tradingCardTable = new LootTable<Item>(rates, seed);
        double[] hundyRate = {1.0};
        this.tileTable = new LootTable<>(hundyRate, seed);
        this.packTable = new LootTable<Item>(hundyRate, seed);
        this.lootTables = new HashMap<>();

        lootTables.put(LootTableType.HERO, heroTable);
        lootTables.put(LootTableType.TRADING_CARD, tradingCardTable);
        lootTables.put(LootTableType.TILE, tileTable);
        lootTables.put(LootTableType.PACK, packTable);

        double[] typeRates = {0.5, 0.25, 0.0, 0.25};
        this.typeTable = new LootTable<>(typeRates, seed);
        this.typeTable.addItem(LootTableType.HERO, 0);
        this.typeTable.addItem(LootTableType.TRADING_CARD, 1);
        this.typeTable.addItem(LootTableType.TILE, 2);
        this.typeTable.addItem(LootTableType.PACK, 3);

        this.addAll();
    }

    private void addAll() {
        List<Hero> heros = HeroFactory.allHeros();
        for (Hero hero : heros) {
            new HeroItem(hero).addToLootTable(lootTables);
        }
        List<TradingCard> tdCards = TradingCardFactory.allTradingCards();
        for (TradingCard tdCard : tdCards) {
            new TradingCardItem(tdCard).addToLootTable(lootTables);
        }
        for (int i = 0; i < 34; i++) {
            new TileItem(new Tile(i)).addToLootTable(lootTables);
        }
        for (LootTableType ltType : LootTableType.values()) {
            if (ltType == LootTableType.PACK) continue;
            new Pack(this.lootTables.get(ltType), 3, 4, this.reserved).addToLootTable(lootTables);
            new Pack(this.lootTables.get(ltType), 6, 8, this.reserved).addToLootTable(lootTables);
        }
    }

    private void fillOffers() {

        int attemps = 0;
        int maxAttemps = 500;

        while (!this.isFull()) {
            LootTableType ltType = this.typeTable.getRandomItem();
            Item item = this.lootTables.get(ltType).getRandomItem();
            if (!this.reserved.isReserved(item) || attemps++ > maxAttemps) {
                Item offerItem = item.onAddToOffers();
                this.offers.add(offerItem);
                this.reserved.add(offerItem);
            }
        }
    }

    private boolean isFull() {
        return (this.offers.size() >= this.offerCapacity);
    }

    private void clearOffers() {
        while (!this.offers.isEmpty()) {
            Item item = this.offers.remove(offers.size() - 1);
            this.reserved.remove(item);
        }
    }

    public void reroll() {
        this.rerollPrice++;
        this.clearOffers();
        this.fillOffers();
    }

    public void closeShop() {
        this.clearOffers();
        this.rerollPrice = this.initialRerollPrice;
    }

    public void openShop() {
        this.fillOffers();
    }

    public Item getOffer(int index) {
        return this.offers.get(index);
    }

    public Item purchaseOffer(int index) {
        if (index < 0 || index >= offers.size()) throw new IndexOutOfBoundsException("index out of bounds");
        Item item = offers.remove(index);
        this.reserved.remove(item);
        return item;
    }

    public int getRerollPrice() {
        return this.rerollPrice;
    }

    public String toString() {
        return offers.toString();
    }

    public void allowDuplicates() {
        this.reserved.setCheck(false);
    }

    public void disallowDuplicates() {
        this.reserved.setCheck(true);
    }
}