import cc.idiary.utils.common.ContentTemplate;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ContentTpTest {
    @Test
    public void fileTest(){
        Map<String, Object> data = new HashMap<>();
        data.put("title", "丁子彪");
        data.put("date", "2017年10月1日");
        data.put("author", "http://www.baidu.com");
        data.put("content", "http://www.baidu.com");
        ContentTemplate ct = new ContentTemplate(new File("d:\\title.xml"), "",data);
        ct.toFile("d:\\1.xml");
        System.out.println(ct.toString());
    }
}
