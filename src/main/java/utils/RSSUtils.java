package utils;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

public class RSSUtils {

    // 全局缓存, 缓存Up主最新发布的视频
    private static Hashtable<String, String> biliUpCache = new Hashtable<>();

    public static List<SyndEntry> getRSSFeedEntries(String url) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        return feed.getEntries();
    }

    // 获得uid用户的关注列表
    public static List<Up> getUserFollows(String rooturl, String uid) throws IOException, FeedException {
        String url = rooturl + "/bilibili/user/followings/" + uid;
        List<SyndEntry> rssFeedEntries = getRSSFeedEntries(url);
        List<Up> followList = new java.util.ArrayList<>();
        for (SyndEntry entry : rssFeedEntries) {
            String uidStr = entry.getLink().substring(entry.getLink().lastIndexOf("/") + 1);
            String name = entry.getTitle().substring(entry.getTitle().indexOf("新关注") + 3);
            followList.add(new Up(uidStr, name));
        }
        return followList;
    }

    // 获得Up主最新发布的视频
    public static Video getNewestVideo(String rooturl, String uid) throws FeedException, IOException {
        String url = rooturl + "/bilibili/user/video/" + uid;
        List<SyndEntry> rssFeedEntries = getRSSFeedEntries(url);
        if (rssFeedEntries.size() > 0) {
            SyndEntry entry = rssFeedEntries.get(0);
            return new Video(entry.getLink(), entry.getTitle(), uid);
        }
        return null;
    }

    public static void initGlobalCache() {
        biliUpCache = new Hashtable<>();
    }

    public static String getBiliUp(String uid) {
        return biliUpCache.get(uid);
    }

    public static void setBiliUp(String uid, String url) {
        biliUpCache.put(uid, url);
    }

}
