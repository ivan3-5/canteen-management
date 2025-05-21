package canteenmanagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javax.swing.JTextArea;

public class ExportTextToPDF {

    public void exportTextAreaToPDF(JTextArea textArea, String filePath) {
        try {
            String content = textArea.getText();

            Font pdfFont = FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL);
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            if (!content.trim().isEmpty()) {
                for (String line : content.split("\n")) {
                    document.add(new Paragraph(line, pdfFont));
                }
            } else {
                document.add(new Paragraph("No content to display.", pdfFont));
            }

            document.close();
            System.out.println("PDF created successfully in Downloads folder.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}