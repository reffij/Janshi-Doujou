package main.gameobjects.heroes;

import main.JDCommandType;
import main.JDEventType;
import main.data.*;
import main.gameobjects.pools.tilepools.Hand;
import main.gameobjects.tiles.Tile;
import main.gameobjects.tradingcards.AnimalCard;
import main.gameobjects.tradingcards.ElementalCard;
import main.gameobjects.tradingcards.TradingCard;
import main.yaku.Yaku;
import engine.commands.Command;
import engine.commands.CommandType;
import engine.datastructures.*;
import engine.events.*;

import java.util.function.*;
import java.util.List;
import java.util.ArrayList;

public class HeroFactory {

    private static Hero createHero(
        String name, 
        int cost, 
        int rarity,
        Consumer<Hero> behaviorRegistrar
    ) {
        return createHero(name, cost, rarity, ScaledInt.fromInt(0),  behaviorRegistrar);
    }

    private static Hero createHero(
        String name, 
        int cost, 
        int rarity,
        int value,
        Consumer<Hero> behaviorRegistrar
    ) {
        return createHero(name, cost, rarity, ScaledInt.fromInt(value), behaviorRegistrar);
    }

    private static Hero createHero(
        String name, 
        int cost, 
        int rarity,
        ScaledInt value,
        Consumer<Hero> behaviorRegistrar
    ) {
            Hero res = new Hero(name, cost, rarity, value);
            behaviorRegistrar.accept(res);
            return res;
    }

    private static class TileCounter implements Visitor<Tile> {

        int count;
        Predicate<Tile> predicate;

        TileCounter(Predicate<Tile> predicate) {
            this.predicate = predicate;
            this.count = 0;
        }

        @Override
        public boolean visit(Tile item) {
            if (predicate.test(item)) count++;
            return true;
        }
        
    }

    public static List<Hero> allHeros() {
        List<Hero> res = new ArrayList<>();

        //ONSCORE +1 strength per bamboo tile
        res.add(
            createHero("Panda", 4, 0, hero -> {
                    hero.putBehavior(JDEventType.WINCONTEXT_ANALYZED, 
                    (ctx, event) -> {
                        WinContext wCTX = event.getData(WinContext.class);
                        Hand hand = wCTX.hand;
                        TileCounter bambooCounter = new TileCounter((tile) -> tile.getSuit() == TileSuit.SOU || tile.getModification() == TileModification.WILD);
                        hand.accept(bambooCounter);
                        ScoreBreakdown sb = new ScoreBreakdown();
                        sb.strength += bambooCounter.count;

                        wCTX.addHeroModifiers(hero, sb);
                    });
                }));
        //ONSCORE +1 strength per MAN tile
        res.add(
            createHero("Man-man", 4, 0, hero -> {
                    hero.putBehavior(JDEventType.WINCONTEXT_ANALYZED, 
                    (ctx, event) -> {
                        WinContext wCTX = event.getData(WinContext.class);
                        Hand hand = wCTX.hand;
                        TileCounter manCounter = new TileCounter((tile) -> tile.getSuit() == TileSuit.MAN || tile.getModification() == TileModification.WILD);
                        hand.accept(manCounter);
                        ScoreBreakdown sb = new ScoreBreakdown();
                        sb.strength += manCounter.count;

                        wCTX.addHeroModifiers(hero, sb);
                    });
                }));
        //ONSCORE +1 strength per PIN tile
        res.add(
            createHero("Rock Collector", 4, 0, hero -> {
                    hero.putBehavior(JDEventType.WINCONTEXT_ANALYZED, 
                    (ctx, event) -> {
                        WinContext wCTX = event.getData(WinContext.class);
                        Hand hand = wCTX.hand;
                        TileCounter manCounter = new TileCounter((tile) -> tile.getSuit() == TileSuit.PIN || tile.getModification() == TileModification.WILD);
                        hand.accept(manCounter);
                        ScoreBreakdown sb = new ScoreBreakdown();
                        sb.strength += manCounter.count;

                        wCTX.addHeroModifiers(hero, sb);
                    });
                }));       
        //ONPLAYPHASE ENTER +3 tiles of seatwind
        res.add(
            createHero("TODO NAME", 10, 2, hero -> {
                hero.putBehavior(JDEventType.PLAY_PHASE_ENTERED, 
                    (ctx, event) -> {
                        Tile tile = new Tile(ctx.seatWind.getInt());
                        ctx.cmdBus.dispatch(new Command<>(JDCommandType.DRAW_TILE, tile));
                        ctx.cmdBus.dispatch(new Command<>(JDCommandType.DRAW_TILE, tile));
                        ctx.cmdBus.dispatch(new Command<>(JDCommandType.DRAW_TILE, tile));
                    });
            })
        );
        //ONSCORE +3 money if yaku used includes pinfu, everytime pinfu is played this card gets +3 gold 
        res.add(
            createHero("Pinfu Merchant", 5, 0, ScaledInt.fromInt(3), hero -> {
                hero.putBehavior(JDEventType.SCORED, 
                    (ctx, event) -> {
                        if (event.getData(WinContext.class).yaku.contains(Yaku.PINFU)) {
                            hero.value.add(3);
                            ctx.cmdBus.dispatch(new Command<>(JDCommandType.INCREASE_MONEY, hero.value.toInt()));
                        }
                    }
                );
            })
        );
        //ONSCORE this card gain x0.1 per 7 pairs hand played, currently x1.0
        res.add(
            createHero("Pear Lover", 7, 1, ScaledInt.fromDouble(1.0), hero -> {
                hero.putBehavior(JDEventType.WINCONTEXT_ANALYZED, 
                    (ctx, event) -> {
                        WinContext wCTX = event.getData(WinContext.class);
                        if (wCTX.isChiiToitsu) {
                            hero.value.add(0.1);
                            ScoreBreakdown sb = new ScoreBreakdown();
                            sb.multiplier = hero.value.toDouble();
                        }
                    }
                );
            })
        );
        //ONTILEDESTROYED every tile destroyed gain 1 random elemental card
        /* 
        res.add(
            createHero("??", 6, 1, hero -> {
                hero.putBehavior(EventType.TILE_DESTROYED, 
                    (ctx, event) -> {
                        Tile tile = event.getData(Tile.class);
                        ctx.cmdBus.dispatch(new Command<>(ADD_TRADING_CARD, ));
                    }
                );
            })
        );

        //ONSCORE +12 strength on score
        res.add(
            createHero("Body Builder", 5, 0, hero -> {
                hero.putBehavior(EventType.WINCONTEXT_ANALYZED, 
                    (ctx, event) -> {
                        WinContext wCTX = event.getData(WinContext.class);
                        ScoreBreakdown sb = new ScoreBreakdown();
                        sb.strength = 12;

                        wCTX.addHeroModifiers(hero, sb);
                    }
                );
            })
        );

        //card provide +0 gold at end of round, increases every turn not discards dora resets etc
        res.add(
            createHero("StarCross lover", 3, 0, 0, hero -> {
                hero.putBehavior(EventType.TILE_DISCARDED,
                    (ctx, event) -> {
                        Tile tile = event.getData(Tile.class);

                    }
                );
            })
        );

        //when discarding dora
        res.add(
            createHero("Heart breaker", 3, 0, null)
        );
        */

        res.add(
            createHero("", 4, 0, hero -> {
                hero.putBehavior(JDEventType.WINCONTEXT_ANALYZED, 
                    (ctx, event) -> {
                        WinContext wCTX = event.getData(WinContext.class);
                        TileCounter jihaiCounter = new TileCounter(
                            (tile) -> {
                                return tile.isHonor();
                            }
                        );
                        wCTX.hand.accept(jihaiCounter);
                        ScoreBreakdown sb = new ScoreBreakdown();
                        sb.strength = jihaiCounter.count;
                        wCTX.addHeroModifiers(hero, sb);
                    }
                );
            }
            )
        );

        res.add(
            createHero("", 4, 0, hero -> {
                hero.putBehavior(JDEventType.WINCONTEXT_ANALYZED, 
                    (ctx, event) -> {
                        WinContext wCTX = event.getData(WinContext.class);
                        TileCounter simpleCounter = new TileCounter(
                            (tile) -> {
                                return tile.isSimple();
                            }
                        );
                        wCTX.hand.accept(simpleCounter);
                        ScoreBreakdown sb = new ScoreBreakdown();
                        sb.strength = simpleCounter.count;
                        wCTX.addHeroModifiers(hero, sb);
                    }
                );
            }
            )
        );

        res.add(
            createHero("", 10, 3, hero -> {
                hero.putBehavior(JDEventType.PLAY_PHASE_ENTERED, 
                    (ctx, event) -> {
                        for (int i = 0; i < 3; i++) ctx.cmdBus.dispatch(new Command<JDCommandType, Tile>(JDCommandType.DRAW_NEW_TILE, new Tile(1, TileSuit.SANGEN)));
                        for (int i = 0; i < 3; i++) ctx.cmdBus.dispatch(new Command<JDCommandType, Tile>(JDCommandType.DRAW_NEW_TILE, new Tile(2, TileSuit.SANGEN)));
                        for (int i = 0; i < 3; i++) ctx.cmdBus.dispatch(new Command<JDCommandType, Tile>(JDCommandType.DRAW_NEW_TILE, new Tile(3, TileSuit.SANGEN)));
                    }
                );
            })
        );

        res.add(
            createHero("rarity 0 1", 0, 0, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 0 2", 0, 0, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 0 3", 0, 0, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 0 4", 0, 0, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 0 5", 0, 0, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 0 6", 0, 0, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 1 1", 0, 1, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 1 2", 0, 1, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 1 3", 0, 1, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 1 4", 0, 1, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 1 5", 0, 1, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 1 6", 0, 1, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 2 1", 0, 2, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 2 2", 0, 2, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 2 3", 0, 2, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 2 4", 0, 2, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 2 5", 0, 2, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 2 6", 0, 2, hero -> {
                
            })
        );

                res.add(
            createHero("rarity 3 1", 0, 3, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 3 2", 0, 3, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 3 3", 0, 3, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 3 4", 0, 3, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 3 5", 0, 3, hero -> {
                
            })
        );

        res.add(
            createHero("rarity 3 6", 0, 3, hero -> {
                
            })
        );


        //if you discard dora +5 mana
        res.add(
            createHero("", 3, 1, hero -> {
                hero.putBehavior(JDEventType.TILE_DISCARDED, 
                    (ctx, event) -> {
                        Tile tile = event.getData(Tile.class);
                        if (ctx.cmdBus.dispatch(new Command<JDCommandType, Tile>(JDCommandType.CHECK_DORA, tile))) {
                            ctx.cmdBus.dispatch(new Command<JDCommandType, Integer>(JDCommandType.INCREASE_MP, 5));
                        }
                    }
                );
            })
        );

        //+4 tccard handsize -1 each round
        res.add(
            createHero("", 3, 0, 4, hero -> {
                hero.putBehavior(JDEventType.PLAY_PHASE_EXITED,
                    (ctx, event) -> {
                        hero.value.subtract(ScaledInt.fromInt(1));
                    }
                );
                hero.setOnBuy((ctx) -> {ctx.tcHandPool.increaseCapacity(hero.value.toInt());});
                hero.setOnSell((ctx) -> {ctx.tcHandPool.decreaseCapacity(hero.value.toInt());});
            })
        );

        //all tiles treated as jihai

        //adds four horses at start of each round
        res.add(
            createHero("Four Horsemen of the Apocalypse", 3, 0, hero -> {
                hero.putBehavior(JDEventType.PLAY_PHASE_ENTERED,
                    (ctx, event) -> {
                        for (int i = 0; i < 4; i++) {
                            ctx.cmdBus.dispatch(
                                new Command<JDCommandType,TradingCard>(
                                    JDCommandType.ADD_TRADING_CARD, 
                                    AnimalCard.HORSE.getTradingCard()
                                )
                            );
                        }
                    }
                );
            })
        );

        //before your hand is dealt, add 1 random tile with 1 random color
        //how to handle rng?

        //when scorring 1/2 chance x2

        //every card that cost less than 3 money is free.

        //this card provides +6 maxMP, +3 maxMP per pure full-flush played
        res.add(
            createHero("", 5, 2, 6, hero -> {
                hero.setOnBuy(
                    (ctx) -> {
                        ctx.cmdBus.dispatch(new Command<JDCommandType,Integer>(
                            JDCommandType.INCREASE_MAX_MP, hero.value.toInt()
                        ));
                    }
                );
                hero.setOnSell(
                    (ctx) -> {
                        ctx.cmdBus.dispatch(new Command<JDCommandType,Integer>(
                            JDCommandType.DECREASE_MAX_MP, hero.value.toInt()
                        ));
                    }
                );
                hero.putBehavior(JDEventType.SCORED, 
                    (ctx, event) -> {
                        WinContext wCTX = event.getData(WinContext.class);
                        boolean condition = false; //condition if wCTX has flush in yaku
                        if (condition) {
                            hero.value.add(3);
                            ctx.cmdBus.dispatch(
                                new Command<JDCommandType,Integer>(JDCommandType.INCREASE_MAX_MP, 3)
                            );
                        }
                    }
                );
            })
        );

        //5x onscore -1 maxMP per tanyao played

        //this card gains +10 strength per tanyao scored resets when scoring non-tanyao hand, currently +0
        res.add(
            createHero("Big Baby", 4, 0, 0, hero -> {
                hero.putBehavior(JDEventType.WINCONTEXT_ANALYZED, 
                    (ctx, event) -> {
                        WinContext wCTX = event.getData(WinContext.class);
                        ScoreBreakdown sb = new ScoreBreakdown();
                        sb.strength += hero.value.toInt();
                        wCTX.addHeroModifiers(hero, sb);
                });
                hero.putBehavior(JDEventType.SCORED, 
                    (ctx, event) -> {
                        //TODO check if hand is tanyao, if it is add 10 to value else set value = 0
                    }
                );

            })
        );

        //+5 mana every rotation
        //no boss effects.
        //copies the ability of the hero to its right

        //Nepo??:
        //dora indicates to itself:
        //rarity: rare
        res.add(
            createHero("Father Nepotism", 10, 2, hero -> {
                hero.setOnBuy(
                    (ctx) -> {
                        ctx.cmdBus.dispatch(new Command<JDCommandType,Integer>(JDCommandType.SET_DORA_OFFSET, 0));
                    }
                );
                hero.setOnSell(
                    (ctx) -> {
                        ctx.cmdBus.dispatch(new Command<JDCommandType,Integer>(JDCommandType.SET_DORA_OFFSET, 1));
                    }
                );
            })
        );

        res.add(
            createHero("Truck-kun", 8, 1, hero -> {

            })
        );

        return res;
    }
}
