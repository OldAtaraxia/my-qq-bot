package component;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.ExternalResource;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import utils.RSSUtils;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ResponseComponent {
    private static final RSSUtils rssUtils = new RSSUtils();
    private static Friend friend;
    private static final String[] keywords = new String[] {"必应每日一图", "来一份涩图", "随机景色", "随机东方图"};
    private static final String[] urls = new String[] {"https://api.szfx.top/bing/api/", "https://tva1.sinaimg.cn/large/0072Vf1pgy1fodqgiodg1j31gs1191im.jpg", "https://cdn.ghser.com/random/bg/bg%20(131).jpg", "https://img.paulzzh.com/touhou/random"};

    public static void listenerRegister(Bot bot, long qq) {

        Listener listener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> {
            MessageChain chain = event.getMessage();
            String json = MessageChain.serializeToJsonString(chain);
            // System.out.println("收到狗修金萨玛的消息: " + json);
            if(json.contains("伤心")) {
                event.getSubject().sendMessage("主人加油, 相信自己");
                event.getSubject().sendMessage("要自信哦瞄");
            }
            int index = 0;
            for (String keyword : keywords) {
                if (json.contains(keyword)) {
                    event.getSubject().sendMessage("检测到关键字：" + keyword + "瞄");
                    Image image = null;
                    try {
                        byte[] bytes = getUrlByByte(urls[index]);
                        image = event.getSubject().uploadImage(ExternalResource.create(bytes));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   if(image != null) {
                       event.getSubject().sendMessage(image);
                   } else {
                       event.getSubject().sendMessage("图片请求错误瞄");
                   }
                }
                index++;
            }
        });
//        friend.sendMessage("listener注册完毕瞄");
        //listener.start();
    }


    public static byte[] getUrlByByte(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "keep-alive")
                .build();
        return client.newCall(request).execute().body().bytes();
    }

}
