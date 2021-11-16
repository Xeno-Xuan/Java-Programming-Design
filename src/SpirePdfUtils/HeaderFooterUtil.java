package SpirePdfUtils;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.automaticfields.PdfCompositeField;
import com.spire.pdf.automaticfields.PdfPageCountField;
import com.spire.pdf.automaticfields.PdfPageNumberField;
import com.spire.pdf.graphics.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

/**
 * @Author ：Xuan
 * @Date ：Created in 2021/11/16 9:52
 * @Description：A util for drawing header& footer in Spire PDF
 * @Modified By：
 * @Version: 1.0.0$
 */
public class HeaderFooterUtil {
    public static void drawFooter(PdfDocument doc) {
        //获取页面大小
        Dimension2D pageSize = doc.getPages().get(0).getSize();
        //定义两个float变量
        float x = 90;
        float y = (float) pageSize.getHeight() - 54;

        for (int i = 0; i < doc.getPages().getCount(); i++) {
            PdfPen pen = new PdfPen(PdfBrushes.getGray(), 0.5f);
            //doc.getPages().get(0).getCanvas().drawLine(pen, x, y, pageSize.getWidth() - x, y);
            //添加文本到页脚处
            PdfTrueTypeFont font = new PdfTrueTypeFont(new Font("黑体", Font.PLAIN, 8), true);
            //PdfStringFormat format = new PdfStringFormat(PdfTextAlignment.Left);

            //String footerText = "这里可以在页脚添加自己想要添加的内容";
            //doc.getPages().get(i).getCanvas().drawString(footerText, font, PdfBrushes.getBlack(), x, y, format);

            //添加页码
            PdfPageNumberField number = new PdfPageNumberField();
            PdfPageCountField count = new PdfPageCountField();
            PdfCompositeField compositeField = new PdfCompositeField(font, PdfBrushes.getBlack(), "第{0}页 共{1}页", number, count);
            compositeField.setStringFormat(new PdfStringFormat(PdfTextAlignment.Right, PdfVerticalAlignment.Top));
            Dimension2D fontSize = font.measureString(compositeField.getText());
            compositeField.setBounds(new Rectangle2D.Float((float) (pageSize.getWidth() - x - fontSize.getWidth()),y, (float) fontSize.getWidth(), (float) fontSize.getHeight()));
            compositeField.draw(doc.getPages().get(i).getCanvas());
        }
    }
    public static void drawHeader(PdfDocument doc) {
        //获取页面尺寸
        Dimension2D pageSize = doc.getPages().get(0).getSize();
        //定义两个float变量
        float x = 90;
        float y = 20;
        for (int i = 0; i < doc.getPages().getCount(); i++) {
            //添加图片到指定位置
            PdfImage headerImage = PdfImage.fromFile("C:\\Users\\long\\Desktop\\logo.png");
            float width = headerImage.getWidth() / 2;
            float height = headerImage.getHeight() / 2;
            doc.getPages().get(i).getCanvas().drawImage(headerImage, x, y, width, height);
            //添加横线至图片下
            PdfPen pen = new PdfPen(PdfBrushes.getGray(), 0.5f);
            doc.getPages().get(i).getCanvas().drawLine(pen, x, y + height + 1, pageSize.getWidth() - x, y + height + 1);
        }
    }
}
