package component;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.ExternalResource;
import utils.RSSUtils;

import java.io.IOException;
import java.util.List;

public class SearchComponent {

    public static String getKeyword(String content) {
        int index = content.indexOf("/B站搜索") + 5;
        String keyword = "";
        while(content.charAt(index) != '"') {
//            if(content.charAt(index) == ' ') {
//                keyword += "%20";
//            } else {
//                keyword += content.charAt(index);
//            }
            keyword += content.charAt(index);
            index++;
        }
        return keyword;
    }

    public static void search(String rooturl, String keyword, Friend friend) throws FeedException, IOException {
        keyword = java.net.URLEncoder.encode(keyword, "UTF-8");
        String url = rooturl + "/bilibili/vsearch/" + keyword + "/:order=totalrank";
        System.out.println(url);
        List<SyndEntry> entries = RSSUtils.getRSSFeedEntries(url);
        int index = 0;
        for (SyndEntry entry : entries) {
            friend.sendMessage(entry.getTitle() + "\n" + entry.getLink());
            index++;
            if(index >= 5) break;
        }
    }

    public static void registerBiliSearchComponent(Bot bot, String rooturl) {
        Listener listener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> {
            MessageChain chain = event.getMessage();
            String json = MessageChain.serializeToJsonString(chain);
            // System.out.println("收到狗修金萨玛的消息: " + json);
            if(json.contains("/B站搜索")) {
                String keyword = getKeyword(json);
                System.out.println("搜索关键字: " + keyword);
                event.getSubject().sendMessage("检测到搜索关键词: " + keyword);
                try {
                    search(rooturl, keyword, event.getSubject());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}