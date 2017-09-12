package cc.idiary.utils.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * for test.
 */
@Deprecated
public class MailClient {

    private static Logger logger = LoggerFactory.getLogger(MailClient.class);

    private String host;
    private String username;
    private String password;
    private String email;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void main(String[] args) {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.126.com");
        email.setAuthentication("tiny_ding", "19860113");
        email.setCharset("utf-8");
        try {
            email.addTo("dzb9898@qq.com", "Me", "utf-8");
        } catch (EmailException e) {
            e.printStackTrace();
        }
        try {
            email.setFrom("tiny_ding@126.com", "Me too", "utf-8");
        } catch (EmailException e) {
            e.printStackTrace();
        }
        email.setSubject("这是一个html邮件");
        // 设置html内容，实际使用时可以从文本读入写好的html代码
        try {
            email.setHtmlMsg(getHtml());
        } catch (EmailException e) {
            e.printStackTrace();
        }
        try {
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    public static String getHtml() {
        Configuration cfg = new Configuration(new Version("2.3.0"));
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", "丁子彪");
        data.put("date", "2017年10月1日");
        data.put("url", "http://www.baidu.com");
        Writer writer = new StringWriter();

        try {
            Template t = new Template("a", new FileReader(MailClient.class.getClassLoader().getResource("content.html").getPath()), cfg);
            try {
                t.process(data, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
