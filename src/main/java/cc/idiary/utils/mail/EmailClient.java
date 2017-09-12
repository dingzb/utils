package cc.idiary.utils.mail;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceCompositeResolver;
import org.apache.commons.mail.resolver.DataSourceFileResolver;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.InternetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

/**
 * http://blog.csdn.net/dadiyang/article/details/52352632
 */
public class EmailClient {

    private static final Logger logger = LoggerFactory.getLogger(EmailClient.class);
    private EmailAccount account;

    public EmailClient(EmailAccount account) {
        this.account = account;

    }

    /**
     * @param subject
     * @param content
     * @param address
     * @return
     * @see #send(String, String, Collection, Collection, Collection)
     */
    public String send(String subject, String content, InternetAddress address) {
        return send(subject, content, Collections.singletonList(address));
    }

    /**
     * @param subject
     * @param content
     * @param to
     * @return
     * @see #send(String, String, Collection, Collection, Collection)
     */
    public String send(String subject, String content, Collection<InternetAddress> to) {
        return send(subject, content, to, null, null);
    }

    /**
     * @param subject
     * @param content
     * @param to
     * @param cc
     * @return
     * @see #send(String, String, Collection, Collection, Collection)
     */
    public String send(String subject, String content, Collection<InternetAddress> to, Collection<InternetAddress> cc) {
        return send(subject, content, to, cc, null);
    }

    /**
     * @param subject   subject of email
     * @param content   content of email with html.
     * @param to        email address
     * @param cc        email address
     * @param bcc       email address
     * @return  {@link Email#send()}
     */
    public String send(String subject, String content, Collection<InternetAddress> to, Collection<InternetAddress> cc, Collection<InternetAddress> bcc) {
        ImageHtmlEmail email = new ImageHtmlEmail();
        email.setHostName(account.getHostName());
        email.setAuthentication(account.getUsername(), account.getPassword());
        email.setCharset("utf8");
        try {
            email.setDataSourceResolver(new DataSourceCompositeResolver(new DataSourceResolver[]{
                    new DataSourceFileResolver(),
                    new DataSourceUrlResolver(new URL("http://"))
            }));
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        email.setSubject(subject);
        try {
            email.setFrom(account.getEmail(), account.getName(), "UTF-8");
            email.setHtmlMsg(content);
            email.setTo(to);
            if (cc != null) {
                email.setCc(cc);
            }
            if (bcc != null) {
                email.setBcc(bcc);
            }
            String id = email.send();
            logger.debug("send email {}.", subject);
            return id;
        } catch (EmailException e) {
            logger.error("Email exception.", e);
        }
        return null;
    }
}
