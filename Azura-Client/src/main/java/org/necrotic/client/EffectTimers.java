package org.necrotic.client;

import org.necrotic.Configuration;
import org.necrotic.client.cache.definition.ItemDef;
import org.necrotic.client.media.Raster;
import org.necrotic.client.cache.media.Sprite;
import org.necrotic.client.gameframe.GameFrame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EffectTimers {

	// Timers
	private static List<EffectTimer> timers = new ArrayList<EffectTimer>();

	public static List<EffectTimer> getTimers() {
		return timers;
	}

	/**
	 * Adds a timer to the list.
	 *
	 * @param et
	 */
	public static void add(EffectTimer et) {

		// Check if timer already exists.. If so, simply update delay.
		for (EffectTimer timer : timers) {
			if (timer.getItemId() == et.getItemId()) {
				timer.setSeconds(et.getSecondsTimer().secondsRemaining());
				timer.setTime(et.getTime());
				return;
			}
		}

		// Add the timer since it wasn't found..
		timers.add(et);
	}


	/**
	 * Draws all of our timers onto the game screen.
	 */
	public static void draw() {
		int xDraw = 5;
		int yDraw = 30;
        if (!Configuration.DRAW_EFFECT_TIMERS)
            return;

		Iterator<EffectTimer> it = timers.iterator();
		while (it.hasNext()) {
			EffectTimer timer = it.next();
			// If the timer has finished, remove it.
			if (timer.getSecondsTimer().finished()) {
				it.remove();
			} else {
				// Otherwise draw it..
                String string;
				Sprite sprite = ItemDef.getSprite(timer.getItemId(), 1, 0, 85, true);
                if ((timer.getSecondsTimer().secondsRemaining() / 86400 ) >= 1)
                    string = formatTimeDHM(timer.getSecondsTimer().secondsRemaining());
                else if ((timer.getSecondsTimer().secondsRemaining() / 3600 ) >= 1)
                   string = formatTimeHMS(timer.getSecondsTimer().secondsRemaining());
                else
                    string = formatTimeMS(timer.getSecondsTimer().secondsRemaining());
				if (sprite != null) {
                    Client.instance.newSmallFont.drawBasicString(string, xDraw, yDraw - 1 + 32, 0xFFFFFF, 1);

                    sprite.drawSprite(xDraw + 2, yDraw - 5);

					yDraw += 39;
				}
			}
		}
	}

    private static String formatTimeMS(int seconds) {
        int remainingSeconds = seconds;
        int minutes = remainingSeconds / 60;
        int remainingSecondsAfterMinutes = remainingSeconds % 60;

        String strMinutes = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        String strSeconds = (remainingSecondsAfterMinutes < 10) ? "0" + remainingSecondsAfterMinutes : String.valueOf(remainingSecondsAfterMinutes);

        return strMinutes + ":" + strSeconds;
    }

    private static String formatTimeHMS(int seconds) {
        int hours = seconds / 3600;
        int remainingSeconds = seconds % 3600;
        int minutes = remainingSeconds / 60;
        int remainingSecondsAfterMinutes = remainingSeconds % 60;

        String strHours = (hours < 10) ? "0" + hours : String.valueOf(hours);
        String strMinutes = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        String strSeconds = (remainingSecondsAfterMinutes < 10) ? "0" + remainingSecondsAfterMinutes : String.valueOf(remainingSecondsAfterMinutes);

        return strHours + ":" + strMinutes + ":" + strSeconds;
    }

    private static String formatTimeDHM(int seconds) {
        int days = seconds / 86400; // 86400 seconds in a day
        int remainingSecondsAfterDays = seconds % 86400;
        int hours = remainingSecondsAfterDays / 3600;
        int remainingSecondsAfterHours = remainingSecondsAfterDays % 3600;
        int minutes = remainingSecondsAfterHours / 60;
        int remainingSecondsAfterMinutes = remainingSecondsAfterHours % 60;

        String strDays = (days < 10) ? "0" + days : String.valueOf(days);
        String strHours = (hours < 10) ? "0" + hours : String.valueOf(hours);
        String strMinutes = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        String strSeconds = (remainingSecondsAfterMinutes < 10) ? "0" + remainingSecondsAfterMinutes : String.valueOf(remainingSecondsAfterMinutes);

        return strDays + ":" + strHours + ":" + strMinutes + ":" + strSeconds;
    }
}
