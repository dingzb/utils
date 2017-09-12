package cc.idiary.utils.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * 利用 freemarker 对大量文字中的变量填充
 */
public class ContentTemplate {
    private static final Logger logger = LoggerFactory.getLogger(ContentTemplate.class);
    private String source;
    private Map<String, Object> data;
    private Template t = null;

    private void init(String sourceCode, Map<String, Object> data) {
        this.source = sourceCode;
        this.data = data;
        Configuration cfg = new Configuration(new Version("2.3.0"));
        try {
            t = new Template("", sourceCode, cfg);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public ContentTemplate(String sourceCode, Map<String, Object> data) {
        init(sourceCode, data);
    }

    /**
     * source of file type
     *
     * @param sourceFile
     * @param encode     default utf8
     * @param data       params
     */
    public ContentTemplate(File sourceFile, String encode, Map<String, Object> data) {
        if (encode == null || encode.isEmpty()) {
            encode = "utf-8";
        }
        StringBuilder str = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), encode));
            char[] chars = new char[1024];
            int len = 0;
            while ((len = br.read(chars)) != -1) {
                str.append(new String(chars, 0, len));
            }
            init(str.toString(), data);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public String toString() {
        if (data == null) {
            return source;
        }
        Writer writer = new StringWriter();
        try {
            t.process(data, writer);
        } catch (TemplateException | IOException e) {
            logger.error("", e);
        }
        return writer.toString();
    }

    public void toFile(String file) {
        File f = new File(file);
        if (f.exists()) {
            f.delete();
        }
        try {
            Writer writer = new FileWriter(f);
            t.process(data, writer);
        } catch (TemplateException | IOException e) {
            logger.error("", e);
        }
    }
}
