package main.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.datastructures.Pair;
import main.data.HandCount;
import main.data.HandCountStack;
import main.data.Meld;
import main.data.MeldType;
import main.data.TilePrimitive;
import main.data.WaitType;
import main.data.WinContext;
import main.gameobjects.tiles.Tile;



public class Analyzer {

    public static Map<TilePrimitive, List<TilePrimitive>> discardToTenpai(
        HandCountStack hand) {

        Map<TilePrimitive, List<TilePrimitive>> res = new HashMap<
            TilePrimitive,
            List<TilePrimitive>>();

        for (TilePrimitive tile : hand.getUniqueTiles()) {
            hand.decrease(tile);
            List<TilePrimitive> waits = winningTiles(hand);
            if (!waits.isEmpty()) res.put(tile, waits);
            hand.undo();
        }
        return res;
    }

    public static boolean isTenpai(HandCountStack hand) {
        return !winningTiles(hand).isEmpty();
    }

    public static List<TilePrimitive> winningTiles(HandCountStack hand) {
        List<TilePrimitive> res = new ArrayList<>();
        for(int i = 0; i < 34; i++) {
            hand.addNormalTile(i);
            if (isWinningHand(hand)) res.add(new TilePrimitive(i));
            hand.undo();
        }
        return res;
    }


    private static boolean isKokuShiMuSou(HandCount hand) {
        boolean pairFound = false;
        int[] indexes = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        for (int index : indexes) {
            int count = hand.count(index);
            if (count == 0) return false;
            if (count > 2) return false;
            if (hand.count(index) == 2 && pairFound) return false;
            if (hand.count(index) == 2) pairFound = true;
        }
        return pairFound;
    }

    private static boolean isChiiToitsu(HandCount hand) {
        for (int i = 0; i < 34; i++) {
            if (hand.count(i) != 2 || hand.count(i) != 0) return false;
        }
        return true;
    }

    //TODO NOTE* THERE IS ALOT OF REPEATED WORK, SAY using a triplet makes 
    //it so that the hand does not win and is thus ignored in the prior 
    //triplet check, after finding a sequence that triplet check will run
    //again.
    //
    //TODO Ideas for avoiding this extra work, seperate the find sequences
    //to a seperate recursive function that doesnt call hasFourMelds but
    //calls findSequences, 
    // 
    //RELIZATION I still believe idea 1 will have repeated work however
    //i.e. 1 triplet doesnt work the next triplet still will check the first
    //triplet
    //
    //TODO Idea 2. instead we cache failed sequences and failed triplets in a 
    //set, then pass that as a argument to the recursive function. then we can 
    //add a check if its in the failed set and not do a recursive call
    //
    //IMPLEMENTED IDEA 2 needs testing TODO implement idea 2 to gather windctx
    private static boolean hasFourMelds(
        HandCountStack hand, 
        int assumedMelds, 
        Pair<Set<Integer>, Set<Integer>> failedSets
    ) {
        if (assumedMelds == 4) return true;

        Set<Integer> failedTrips = failedSets.a;
        Set<Integer> failedSequences = failedSets.b;

        //find triples
        for (int i = 0; i < 34; i++) {
            if (failedTrips.contains(i)) continue;
            if (hand.count(i) >= 3) {
                hand.decrease(i, 3);
                if (hasFourMelds(hand, assumedMelds + 1, failedSets)) {
                    hand.undo(3);
                    return true;
                } else{
                    failedTrips.add(i);
                    hand.undo(3);
                }
            }
        }

        //find sequences
        for (int i = 0; i < 25; i++) {
            if (failedSequences.contains(i)) continue;
            if (i % 9 < 7 && 
                hand.count(i) >= 1 && 
                hand.count(i + 1) >= 1 &&
                hand.count(i + 2) >= 1) {
                    hand.decrease(i, 1);
                    hand.decrease(i + 1, 1);
                    hand.decrease(i + 2, 1);
                    if (hasFourMelds(hand, assumedMelds + 1, failedSets)) {
                        hand.undo(3);
                        return true;
                    } else {
                        failedSequences.add(i);
                        hand.undo(3);
                    }
            }
        }
        return false;
    }

    private static void collectMelds(
        HandCountStack hand, 
        List<Meld> current, 
        List<List<Meld>> res) {

        if (current.size() == 4) {
            res.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < 34; i++) {
            if (hand.count(i) >= 3) {
                hand.decrease(i, 3);
                current.add(new Meld(i, MeldType.PON));
                collectMelds(hand, current, res);
                current.remove(current.size() - 1);
                hand.undo(3);
            }
        }

        for (int i = 0; i < 25; i++) {
            if (i % 9 < 7 && 
                hand.count(i) >= 1 && 
                hand.count(i + 1) >= 1 &&
                hand.count(i + 2) >= 1) {
                    hand.decrease(i, 1);
                    hand.decrease(i + 1, 1);
                    hand.decrease(i + 2, 1);

                    current.add(new Meld(i, MeldType.CHI));
                    collectMelds(hand, current, res);
                    current.remove(current.size() - 1);

                    hand.undo(3);

                }
        }
    }

    private static WaitType waitType(Tile winningTile, Meld winningMeld) {
        if (winningMeld.getMeldType() == MeldType.PON) {
            return WaitType.SHANPON;
        }
        int meldStartRank = TilePrimitive.integerToRank(winningMeld.getIndex());
        int tileRank = winningTile.getRank();
        if (meldStartRank == 1 && tileRank == 3) return WaitType.PENCHAN;
        if (meldStartRank == 7 && tileRank == 7) return WaitType.PENCHAN;
        if (tileRank - meldStartRank == 1) return WaitType.KANCHAN;
        return WaitType.RYANMEN;
    }

    public static List<WinContext> winningContexts(
        HandCountStack hand, 
        Tile winningTile) {

        List<WinContext> res = new ArrayList<>();
        if (isKokuShiMuSou(hand)) {
            for (int i = 0; i < 34; i++) {
                if (hand.count(i) == 2) {
                    res.add(WinContext.KokuShiMuSou(
                        winningTile, 
                        new TilePrimitive(i)
                    ));
                }
            }
        }

        if (isChiiToitsu(hand)) res.add(WinContext.ChiiToiTsu(winningTile));

        for (int i = 0; i < 34; i++) {
            if (hand.count(i) >= 2) {
                hand.decrease(i, 2);

                List<List<Meld>> meldRes = new ArrayList<>();
                collectMelds(hand, new ArrayList<>(), meldRes);


                for (List<Meld> melds : meldRes) {
                    Set<Meld> discoveredWinningMelds = new HashSet<Meld>();
                    for (Meld meld : melds) {
                        if (
                            meld.contains(winningTile) && 
                            !discoveredWinningMelds.contains(meld)
                        ) {
                            discoveredWinningMelds.add(meld);
                            res.add(
                                new WinContext(
                                    winningTile, 
                                    melds, 
                                    meld, 
                                    waitType(winningTile, meld), 
                                    new TilePrimitive(i)
                                )
                            );
                        }
                    }
                }

                if (i == winningTile.getInt()) {
                    for (List<Meld> melds : meldRes) {
                        res.add(
                            new WinContext(
                                winningTile, 
                                melds, 
                                null, 
                                WaitType.TANKI, 
                                new TilePrimitive(i)
                            )
                        );
                    }
                }
                


                hand.undo(2);
            }
        }
        return res;
    }

    public static boolean isWinningHand(HandCountStack hand) {
        if (isKokuShiMuSou(hand)) return true;
        if (isChiiToitsu(hand)) return true;
        for (int i = 0; i < 34; i++) {
            if (hand.count(i) >= 2) {
                hand.decrease(i, 2);
                if (hasFourMelds(hand, 0, 
                    new Pair<>(new HashSet<Integer>(), new HashSet<Integer>()))
                ) {
                    hand.undo(2);
                    return true;
                }
                else {
                    hand.undo(2);
                }
            }
        }
        return false;
    }
}
