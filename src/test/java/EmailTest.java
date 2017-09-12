import cc.idiary.utils.mail.EmailAccount;
import cc.idiary.utils.mail.EmailClient;
import org.junit.Test;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

public class EmailTest {
    @Test
    public void sendTest(){
        EmailAccount emailAccount = new EmailAccount();
        emailAccount.setEmail("tiny_ding@126.com");
        emailAccount.setName("Dingzb");
        emailAccount.setHostName("smtp.126.com");
        emailAccount.setUsername("tiny_ding");
        emailAccount.setPassword("19860113");
        EmailClient emailClient = new EmailClient(emailAccount);
        String content = "<h1>Hello.</h1>";
        InternetAddress to = new InternetAddress();
        to.setAddress("dzb9898@qq.com");
        try {
            to.setPersonal("丁子彪", "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        emailClient.send("测试", content, to);
    }
}
