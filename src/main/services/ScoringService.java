package main.services;

import engine.commands.*;
import engine.core.GameService;
import engine.events.*;
import main.JDCommandType;
import main.JDEventType;
import main.JDGameContext;
import main.analysis.Analyzer;
import main.analysis.Calculator;
import main.data.*;
import main.yaku.Yaku;

import java.util.*;

public class ScoringService extends GameService<JDCommandType, JDGameContext> {

    public ScoringService(CommandBus<JDCommandType, JDGameContext> cmdBus) {
        cmdBus.register(JDCommandType.TSUMO, this);

        this.addResponsibility(JDCommandType.TSUMO, 
            (cmd, ctx) -> {
                List<WinContext> winContexts = Analyzer.winningContexts(ctx.handCount, ctx.tsumoTile);

                for (WinContext wCTX : winContexts) {
                    wCTX.prevelantWind = ctx.prevWind;
                    wCTX.seatWind = ctx.seatWind;
                    wCTX.playerStrength = ctx.strength;
                    wCTX.hand = ctx.hand;
                }

                for (Yaku yaku : Yaku.values()) {
                    for (WinContext wCTX : winContexts) {
                        if (yaku.test(wCTX)) wCTX.addYaku(yaku);
                    }
                }

                for (WinContext wCTX : winContexts) {
                    wCTX.baseScoreBreakdown = Calculator.getBaseScoreBreakdown(wCTX);
                }

                WinContext winContext = Calculator.findBestWinContext(winContexts);

                ctx.eventBus.emit(new Event<>(JDEventType.WINCONTEXT_ANALYZED, winContext));

                Score score = Calculator.finalEvaluation(winContext);
                ctx.currentScore.add(score);

                ctx.eventBus.emit(new Event<>(JDEventType.SCORED, winContext));
                return true;

            }
        );
    }
}
