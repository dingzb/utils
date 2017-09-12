import cc.idiary.utils.office.OfficeApp;
import cc.idiary.utils.office.WordApp;
import org.junit.Test;

public class OfficeTest {

    @Test
    public void DOCX2PDFTest(){
        OfficeApp app = new WordApp();
        app.open("F:\\tendyron\\智能高速下载器\\doc\\智能高速下载设备及机构维护手册_v1.1.1.docx");
        app.saveAs("d:\\1.pdf", "pdf");
        app.exist();
    }

    @Test
    public void XML2DOCX(){
        OfficeApp app = new WordApp();
        app.open("D:\\title.xml");
        app.saveAs("d:\\2.docx", "docx");
        app.saveAs("d:\\2.pdf", "pdf");
        app.exist();
    }
}
