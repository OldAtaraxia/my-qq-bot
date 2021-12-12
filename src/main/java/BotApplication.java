import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.DeviceInfo;
import component.ResponseComponent;

import java.io.File;

public class BotApplication {
    private static final ResponseComponent RESPONSESERVICE = new ResponseComponent();
    public static void main(String[] args) {
        long qq = Long.parseLong(args[0]);
        String password = args[1];
        long masterqq = Long.parseLong(args[2]);
        File file = new File("device.json");
        Bot bot = BotFactory.INSTANCE.newBot(qq, password, new BotConfiguration() {{
            noNetworkLog();
            noBotLog();
            setProtocol(MiraiProtocol.ANDROID_PAD);
            enableContactCache();
            setDeviceInfo(bot -> DeviceInfo.from(file));
        }});
        bot.login();
        System.out.println("Bot started");
        RESPONSESERVICE.listenerRegister(bot, masterqq);
    }

}
