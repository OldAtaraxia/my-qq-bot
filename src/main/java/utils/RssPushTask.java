package utils;

import com.rometools.rome.io.FeedException;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

public class RssPushTask extends TimerTask {
    private List<Up> followings;
    private Bot bot;
    private Friend master;
    private String rooturl;
    private String masterBiliUid;

    public RssPushTask(Bot bot, Friend master, String masterBiliUid, String rooturl) {
        this.bot = bot;
        this.master = master;
        this.masterBiliUid = masterBiliUid;
        this.rooturl = rooturl;
        try {
            this.followings = RSSUtils.getUserFollows(rooturl, masterBiliUid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.followings = RSSUtils.getUserFollows(rooturl, masterBiliUid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Up up : followings) {
            try {
                Video newestVideo = RSSUtils.getNewestVideo(rooturl, up.getUid());
                if(newestVideo != null && !newestVideo.equals(RSSUtils.getBiliUp(up.getUid()))) {
                    RSSUtils.setBiliUp(up.getUid(), newestVideo.getUrl());
                    master.sendMessage("您关注的up主" + up.getName() + "有新视频啦！\n" + newestVideo.getTitle() + "\n" + newestVideo.getUrl());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
