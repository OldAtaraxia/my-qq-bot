import com.rometools.rome.io.FeedException;
import utils.RSSUtils;
import utils.Up;

import java.io.IOException;
import java.util.List;


public class RSSUtilTest {
    public static void main(String[] args) throws FeedException, IOException {
        String rooturl = "http://47.98.60.14:1200";
        String uid = "443953314";
        List<Up> list = RSSUtils.getUserFollows(rooturl, uid);
        System.out.println(list);
    }
}
