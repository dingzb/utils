package cc.idiary.utils.office;

import com.jacob.com.Dispatch;

public class WordApp extends OfficeApp {
    /*
    http://hu437.iteye.com/blog/844350
    0	    Doc
    1	    Dot
    2-5	    Txt
    6	    Rtf
    7	    Txt
    8、10	htm
    11	    Xml
    12、16	Docx
    13	    Docm
    14	    Dotx
    15	    Dotm
    17	    Pdf
     */

    private static final int wdFormatPDF = 17;  //pdf
    private static final int wdFormatXML = 11;  //xml
    private static final int wdFormatDOCX16 = 16;  //docx
    private static final int wdFormatDOCX12 = 12;  //docx


    public WordApp() {
        super(OfficeType.MS_WORD07);
    }

    @Override
    protected Dispatch getProperty() {
        return app.getProperty("Documents").toDispatch();
    }

    @Override
    protected int fileTypeCode(String type) {
        if (type == null){
            return wdFormatDOCX16;
        }
        switch (type) {
            case "pdf":
                return wdFormatPDF;
            case "xml":
                return wdFormatXML;
            case "docx":
                return wdFormatDOCX12;
            default:
                return wdFormatDOCX16;
        }
    }
}
