package main;

import engine.builtin.consolecommandparsers.*;

public class CCP {

    public final static CommandParserVoid<JDCommandType> VOID = 
    new CommandParserVoid<JDCommandType>();

    public final static CommandParserErr<JDCommandType> ERR = 
    new CommandParserErr<JDCommandType>();

    public final static CommandParserInt<JDCommandType> INT = 
    new CommandParserInt<JDCommandType>();

    public final static CommandParserTile<JDCommandType> TILE = 
    new CommandParserTile<JDCommandType>();

    public final static CommandParserTileArray<JDCommandType> TILEARR = 
    new CommandParserTileArray<JDCommandType>();
}
