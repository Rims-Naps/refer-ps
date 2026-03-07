package com.ruse.world.entity.impl.bot;


import com.ruse.model.Position;
import com.ruse.net.PlayerSession;
import com.ruse.world.World;
import com.ruse.world.entity.impl.bot.script.BotScript;
import com.ruse.world.entity.impl.bot.script.BotScriptRepository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BotManager {

    /**
     * A hash collection of bot names.
     */
    private static final Set<String> BOT_NAMES = new HashSet<>();

    /**
     * Gets the bot names.
     *
     * @return The bot names.
     */
    public static Set<String> getBotNames() {
        return BOT_NAMES;
    }

    /**
     * A hash collection of the currently active scripts.
     */
    private static final Set<BotScript> ACTIVE_SCRIPTS = ConcurrentHashMap.newKeySet();

    public static void addBot(String script, String username, Position position) {
        BotPlayer plr = new BotPlayer(username, position, new PlayerSession(null));
        startScript(plr, script.toLowerCase() + "script");
        BOT_NAMES.add(username);
    }

    /**
     * Processes the active scripts.
     */
    public static void process() {
        for (Iterator<BotScript> it = ACTIVE_SCRIPTS.iterator(); it.hasNext();) {
            BotScript script = it.next();

            try {
                if (World.getPlayerByName(script.getPlayer().getName()) == null) {
                    //System.out.println("n");
                    continue;
                }

				/*if (System.currentTimeMillis() >= script.getPlayer().getSessionEnd()) {
					script.stop();
					script.getPlayer().logout(true);
				}*/

                if (script.stopped()) {
                    script.onStop();
                    it.remove();
                    continue;
                }

                if (System.currentTimeMillis() - script.getLastAction() < script.getActionDelay()) {
                    continue;
                }

                script.execute();
            } catch (Exception e) {
                System.out.println("Error processing script: " + script.getClass().getName() + " for " + script.getPlayer().getName() + ".");
                e.printStackTrace();
            }
        }
    }
    public static void killBots() {
        for (Iterator<BotScript> it = ACTIVE_SCRIPTS.iterator(); it.hasNext();) {
            BotScript script = it.next();
            try {
                System.out.println("killing!");
                script.stop();
                if(World.getPlayerByName(script.getPlayer().getName()) == null)
                    World.getPlayers().remove(script.getPlayer());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Executes the specified {@link BotScript} for the specified
     * {@link BotPlayer}.
     */
    public static void startScript(BotPlayer player, String name) {
        try {
            BotScript script = BotScriptRepository.getScripts().get(name).getClass().newInstance();
            player.setActiveScript(script);
            script.setPlayer(player);
            script.initialize();
            ACTIVE_SCRIPTS.add(script);
        } catch (Exception e) {
            System.out.println("Error starting script: " + name + " for " + player.getName() + ".");
            e.printStackTrace();
        }
    }

    /**
     * Stops the specified {@link BotScript}.
     *
     * @param script
     *            The script.
     */
    public static void stopScript(BotScript script) {
        ACTIVE_SCRIPTS.remove(script);
    }

    public static final String[] names = new String[]{
            "Get clapped",
            "Romcik",
            "Bany",
            "Joao10",
            "Gamerdudz",
            "Berzerk",
            "Geer bank",
            "Chudos",
            "Nevic",
            "Zara23",
            "Maluco",
            "rindek",
            "the muerte",
            "cona1",
            "dropitlow",
            "inferno anz",
            "nathan aex",
            "grinch",
            "host win",
            "sr gamble",
            "imperall",
            "epic bunny",
            "sora",
            "jawa123",
            "scalett",
            "trolled",
            "tameer",
            "thisgamesuz",
            "Pk Banana",
            "pkbagon",
            "toversletje",
            "aleverv1",
            "leon",
            "thekingmoon",
            "starlin",
            "skull",
            "xx rangelord",
            "anaconda0512",
            "the pvm baws",
            "clue",
            "runbeudie",
            "matadorers",
            "grimdeath",
            "ziggy",
            "lemme",
            "naca2004",
            "grands",
            "keemstar",
            "syooc",
            "kiing",
            "zumer",
            "raikas",
            "optic",
            "metol",
            "amor123",
            "brunobastos",
            "desginer",
            "bowserr",
            "moosey",
            "dead pk",
            "national",
            "noragamil",
            "pure frost",
            "dynamit pure",
            "playerName",
            "mariaclara",
            "babygoat",
            "tygod",
            "ichaotic",
            "flay",
            "im king yes",
            "brendans",
            "swang",
            "loot from",
            "extactionx",
            "the ola man1",
            "legal",
            "purex1",
            "epo inc",
            "kevin66",
            "sparky pker",
            "agent pure",
            "iron chf",
            "zeios",
            "super 8",
            "infenro",
            "pepson",
            "b e n a n",
            "looking",
            "killing12",
            "majoo",
            "creative pk",
            "pildo",
            "instasell",
            "coldwar",
            "prefect",
            "oceana",
            "rosk",
            "lolz tk",
            "wet milfs",
            "revs",
            "juker",
            "orangez",
            "mage booko",
            "turaemot",
            "sweet15",
            "into pk",
            "triple xxx",
            "tobias fate",
            "soled skill",
            "fiercapx",
            "sou br",
            "a fox",
            "valent"



    };

}
