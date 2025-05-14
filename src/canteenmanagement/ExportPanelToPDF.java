package canteenmanagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.swing.JPanel;

public class ExportPanelToPDF {
    public BufferedImage getPanelImage(JPanel panel) {
        int width = panel.getWidth();
        int height = panel.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        panel.paint(g2d);
        g2d.dispose();
        return image;
    }
    
    public void exportPanelToPDF(JPanel panel, String filePath) {
        try {
            BufferedImage panelImage = getPanelImage(panel);
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Image image = Image.getInstance(panelImage, null);
            image.scaleToFit(document.getPageSize().getWidth() - 50, document.getPageSize().getHeight() - 50);
            image.setAlignment(Image.ALIGN_CENTER);
            document.add(image);
            document.close();
            System.out.println("PDF created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
