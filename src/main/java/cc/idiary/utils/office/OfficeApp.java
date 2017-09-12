package cc.idiary.utils.office;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class OfficeApp {

    private static final Logger logger = LoggerFactory.getLogger(OfficeApp.class);
    private static final int wdDoNotSaveChanges = 0;

    protected ActiveXComponent app = null;
    protected Dispatch doc = null;    //document or workbook or ...

    public OfficeApp(OfficeType type) {
        //init com thread
        ComThread.InitSTA();
        logger.debug("Com thread init.");
        app = new ActiveXComponent(type.getValue());
        app.setProperty("Visible", false);
        logger.debug("Ms office com: {}", type.getValue());
    }

    /**
     * open document file.
     *
     * @param file
     */
    public void open(String file) {
        Dispatch docs = getProperty();
        logger.debug("Open document： {}", file);
        this.doc = Dispatch.call(docs, "Open", file, false, true).toDispatch();
    }

    protected abstract Dispatch getProperty();

    /**
     * save document file.
     */
    public void save() {
        if (doc != null) {
            Dispatch.call(doc, "Save", new Variant(true));
        }
    }

    /**
     * save as pdf
     *
     * @param target
     * @param type
     */
    public void saveAs(String target, String type) {
        File file = new File(target);
        if (file.exists()) {
            logger.debug("file {} exist, delete.", target);
            file.delete();
        }
        Dispatch.call(doc, "SaveAs", target, fileTypeCode(type));
        logger.debug("Save document to {}.", target);
    }

    protected abstract int fileTypeCode(String type);


    /**
     * close document file.
     */
    public void close() {
        if (doc != null) {
            Dispatch.call(doc, "Close", new Variant(true));
        }
    }

    /**
     * exist program
     */
    public void exist() {
        if (app != null) {
            app.invoke("Quit", wdDoNotSaveChanges);
            logger.debug("Ms office com quit.");
        }
        //close com thread
        ComThread.Release();
        logger.debug("Com thread release.");
    }
}
