import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FmTest {

    @Test
    public void fmTest() {
        Configuration cfg = new Configuration(new Version("2.3.0"));
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", "丁子彪");
        data.put("date", "2017年10月1日");
        data.put("url", "http://www.baidu.com");
        Writer writer = null;
        try {
            writer = new FileWriter("d:/a.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Template t = new Template("a", new FileReader(this.getClass().getResource("content.html").getPath()), cfg);
            try {
                t.process(data, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf(writer.toString());
    }


    @Test
    public void fmTest2() {
        Configuration cfg = new Configuration(new Version("2.3.0"));
        Map<String, String> data = new HashMap<String, String>();
        data.put("title", "丁子彪");
        data.put("date", "2017年10月1日");
        data.put("author", "http://www.baidu.com");
        data.put("content", "http://www.baidu.com");
        Writer writer = null;
        try {
            writer = new FileWriter("d:/a.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Template t = new Template("a", new FileReader(this.getClass().getResource("title.xml").getPath()), cfg);
            try {
                t.process(data, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf(writer.toString());
    }
}
