import component.RssPushComponent;
import component.SearchComponent;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.DeviceInfo;
import component.ResponseComponent;

import java.io.File;

public class BotApplication {
    public static void main(String[] args) {
        long qq = Long.parseLong(args[0]);
        String password = args[1];
        long masterqq = Long.parseLong(args[2]);
        String rssRootUrl = args[3];
        String biliId = args[4];
        File file = new File("device.json");
        Bot bot = BotFactory.INSTANCE.newBot(qq, password, new BotConfiguration() {{
            noNetworkLog();
            noBotLog();
            setProtocol(MiraiProtocol.ANDROID_PAD);
            // enableContactCache();
            setDeviceInfo(bot -> DeviceInfo.from(file));
        }});
        bot.login();
        Friend friend = bot.getFriend(masterqq);
        if(friend != null) {
            friend.sendMessage("狗修金萨玛我上线了瞄");
        }
        System.out.println("Bot started");
        ResponseComponent.listenerRegister(bot, masterqq);
        SearchComponent.registerBiliSearchComponent(bot, rssRootUrl);
        RssPushComponent.init(bot, friend, biliId, rssRootUrl);

    }

}
