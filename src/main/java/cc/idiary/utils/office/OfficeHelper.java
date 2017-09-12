package cc.idiary.utils.office;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * for test
 */
@Deprecated
public class OfficeHelper {

    /**
     * 打开文件
     *
     * @param documents
     * @param inputDocPath
     * @return
     */
    private Dispatch open(Dispatch documents, String inputDocPath) {
        return Dispatch.call(documents, "Open", inputDocPath).toDispatch();
    }

    /**
     * 选定内容
     *
     * @param word
     * @return
     */
    private Dispatch select(ActiveXComponent word) {
        return word.getProperty("Selection").toDispatch();
    }

    /**
     * 把插入点移动到文件首位置
     *
     * @param selection
     */
    private void moveStart(Dispatch selection) {
        Dispatch.call(selection, "HomeKey", new Variant(6));
    }

    /**
     * 从选定内容或插入点开始查找文本
     *
     * @param selection  选定内容
     * @param toFindText 要查找的文本
     * @return true：查找到并选中该文本；false：未查找到文本。
     */
    private boolean find(Dispatch selection, String toFindText) {
        //从selection所在位置开始查询
        Dispatch find = Dispatch.call(selection, "Find").toDispatch();
        //设置要查找的内容
        Dispatch.put(find, "Text", toFindText);
        //向前查找
        Dispatch.put(find, "Forward", "True");
        //设置格式
        Dispatch.put(find, "format", "True");
        //大小写匹配
        Dispatch.put(find, "MatchCase", "True");
        //全字匹配
        Dispatch.put(find, "MatchWholeWord", "True");
        //查找并选中
        return Dispatch.call(find, "Execute").getBoolean();
    }

    /**
     * 把选定内容替换为设定文本
     *
     * @param selection
     * @param newText
     */
    private void replace(Dispatch selection, String newText) {
        Dispatch.put(selection, "Text", newText);
    }

    /**
     * 全局替换
     *
     * @param selection
     * @param oldText
     * @param replaceObj
     */
    private void replaceAll(Dispatch selection, String oldText, Object replaceObj) {
        moveStart(selection);
        String newText = (String) replaceObj;
        while (find(selection, oldText)) {
            replace(selection, newText);
            Dispatch.call(selection, "MoveRight");
        }
    }

    /**
     * 打印
     *
     * @param document
     */
    private void print(Dispatch document) {
        Dispatch.call(document, "PrintOut");
    }

    /**
     * 保存文件
     *
     * @param word
     * @param outputPath
     */
    private void save(ActiveXComponent word, String outputPath) {
        Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(), "FileSaveAs", outputPath);
    }

    /**
     * 关闭文件
     *
     * @param doc
     */
    private void close(Dispatch doc) {
        Dispatch.call(doc, "Close", new Variant(true));
    }

    /**
     * 保存打印doc文档
     *
     * @param inputDocPath
     * @param outPutDocPath
     * @param data
     * @param isPrint
     */
    public void saveDoc(String inputDocPath, String outPutDocPath, HashMap data, boolean isPrint) {
        //初始化com的线程
        ComThread.InitSTA();
        //word运行程序对象
        ActiveXComponent word = new ActiveXComponent("Word.Application");
        //文档对象
        Dispatch wordObject = (Dispatch) word.getObject();
        //设置属性  Variant(true)表示word应用程序可见
        Dispatch.put((Dispatch) wordObject, "Visible", new Variant(false));
        //word所有文档
        Dispatch documents = word.getProperty("Documents").toDispatch();
        //打开文档
        Dispatch document = this.open(documents, inputDocPath);
        Dispatch selection = this.select(word);
        Iterator keys = data.keySet().iterator();
        String oldText;
        Object newValue;
        while (keys.hasNext()) {
            oldText = (String) keys.next();
            newValue = data.get(oldText);
            this.replaceAll(selection, oldText, newValue);
        }
        //是否打印
        if (isPrint) {
            this.print(document);
        }
        this.save(word, outPutDocPath);
        this.close(document);
        word.invoke("Quit", new Variant[0]);
        //关闭com的线程
        ComThread.Release();
    }


    static final int wdDoNotSaveChanges = 0;// 不保存待定的更改。
    static final int wdFormatPDF = 17;// word转PDF 格式

    public static void main(String[] args) throws IOException {
        String filePath = "F:\\tendyron\\智能高速下载器\\doc\\智能高速下载器APP更新手册_v1.0.docx";
        String filePath2 = "F:\\智能高速下载器APP更新手册_v1.0.pdf";
        String source1 = "F:\\tendyron\\智能高速下载器\\doc\\智能高速下载器APP更新手册_v1.0.docx";
        String target1 = "F:\\智能高速下载器APP更新手册_v1.0.pdf";
        word2pdf(source1, target1);
    }

    public static boolean word2pdf(String source, String target) {
        ComThread.InitSTA();

        System.out.println("Word转PDF开始启动...");
        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            System.out.println("打开文档：" + source);
            Dispatch doc = Dispatch.call(docs, "Open", source, false, true).toDispatch();
            System.out.println("转换文档到PDF：" + target);
            File tofile = new File(target);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(doc, "SaveAs", target, wdFormatPDF);
            Dispatch.call(doc, "Close", false);
            long end = System.currentTimeMillis();
            System.out.println("转换完成，用时：" + (end - start) + "ms");
            return true;
        } catch (Exception e) {
            System.out.println("Word转PDF出错：" + e.getMessage());
            return false;
        } finally {
            if (app != null) {
                app.invoke("Quit", wdDoNotSaveChanges);
            }
            ComThread.Release();
        }

    }
}
