package component;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import utils.RssPushTask;

import java.util.Timer;


public class RssPushComponent {
    public static void init(Bot bot, Friend master, String masterBiliUid, String rooturl) {
        Timer timer = new Timer();
        timer.schedule(new RssPushTask(bot, master, masterBiliUid, rooturl), 0, 1000 * 60 * 30);
    }
}
