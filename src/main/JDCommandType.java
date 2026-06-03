package main;

import engine.commands.CommandParser;
import engine.commands.CommandType;
import engine.ui.node.UINode;

public enum JDCommandType implements CommandType<JDCommandType> {

    PING,
    QUIT,

    NEXT_ROUND,

    LOSE,
    OPEN_GAME, 
    OPEN_MENU, 

    //TILES
    TOGGLE_AUTO_SORT,
    DISCARD_TILE_INDEX(CCP.INT),
    INITIAL_DRAW,
    DRAW_TILE(CCP.TILE),
    DUMP_TILES,
    SET_HAND(CCP.TILEARR),
    DRAW_SPECIFIC_TILE, 
    DRAW_NEW_TILE, 
    ADD_NEW_TILE(CCP.TILEARR), //adds new tile to general tile pool
    FLIP_DORA, 


    //SCORING
    TSUMO,

    CHECK_WIN,

    //HEROS
    ADD_HERO(CCP.ERR), //STRING TO HERO
    REMOVE_HERO_INDEX(CCP.INT), 

    //TRADING CARDS
    SHUFFLE_DECK,

    DRAW_TRADING_CARDS, 
    DRAW_TRADING_CARDS_FREE, 
    ADD_TRADING_CARD, 

    //MARKET
    REROLL, 
    OPEN_PACK(CCP.ERR), 
    PURCHASE_OFFER_INDEX(CCP.INT), 
    OPEN_MARKET, 
    CLOSE_MARKET, 

    //PLAYER
    SPEND_MONEY(CCP.INT), 
    SPEND_MP(CCP.INT), 
    UPDATE_MP(CCP.INT), 
    DECREASE_MAX_MP(CCP.INT), 
    INCREASE_MAX_MP(CCP.INT), 
    INCREASE_MP(CCP.INT), 
    INCREASE_MONEY(CCP.INT), 
    INCREASE_STRENGTH(CCP.INT), 

    ENQUEUE_ON_DISCARD, 
    ROTATE_WIND, 



    USE_TRADING_CARD_INDEX(CCP.INT), 
    ADD_NEW_RANDOM_ANIMAL_CARD, DUMP_TRADING_CARDS, DISCARD_TC_INDEX, CHECK_DORA, IS_DORA, SET_DORA_OFFSET, 

    ;


    private final CommandParser<JDCommandType, ?> cmdParser;
    JDCommandType(CommandParser<JDCommandType, ?> cmdParser) {
        this.cmdParser = cmdParser;
    }

    JDCommandType() {
        this.cmdParser = CCP.VOID;
    }

    public CommandParser<JDCommandType, ?> getParser() {
        return this.cmdParser;
    }
}
