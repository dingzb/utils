package cc.idiary.utils.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class PdfUtils {
    public static void html2Pdf(String htmlPath, String pdfPath) throws IOException, DocumentException {
        Document doc = new Document();
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));
        doc.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, doc, new FileInputStream(htmlPath), Charset.forName("UTF-8"));
        doc.close();
    }

    public static void main(String[] args) {
        try {
            html2Pdf("d:\\1.html", "d:\\1.pdf");
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
