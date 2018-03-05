package njj.androidutillib;

import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by niejianjian on 2018/3/5.
 */

public class CreateDimensUtil {

    /**
     * 生成需要的dimens.xml文件
     *
     * @param length     需要生成的dimen最大数值，一般指定屏幕宽高的最大值即可
     * @param density    指定屏幕密度，根据px和density来计算需要的dp值，或者sp值
     * @param targetFile 目标存储文件
     */
    public static void createDeimensXml(int length, float density, File targetFile) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element resources = document.createElement("resources");
            for (int i = 1; i <= length; i++) {
                Element dimen = document.createElement("dimen");
                float f = (float) i / density;
                BigDecimal b = new BigDecimal(f);
                float dpValue = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                dimen.setAttribute("name", "dimen_" + i + "px");
                dimen.setTextContent(dpValue + "dp");
                resources.appendChild(dimen);
            }

            for (int i = 1; i <= 100; i++) {
                Element dimen = document.createElement("dimen");
                float f = (float) i / density;
                DecimalFormat fnum = new DecimalFormat("##0.00"); // 保留两位小数
                String dpValue = fnum.format(f);
                dimen.setAttribute("name", "tvSize_" + i + "px");
                dimen.setTextContent(dpValue + "sp");
                resources.appendChild(dimen);
            }

            document.appendChild(resources);

            // 创建TransformerFactory对象
            TransformerFactory tff = TransformerFactory.newInstance();
            // 创建Transformer对象
            Transformer tf = tff.newTransformer();
            // 设置输出数据时换行
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            File file;
            if (targetFile == null) {
                file = new File(Environment.getExternalStorageDirectory() + File.separator + "dimens.xml");
            } else {
                file = targetFile;
            }
            if (!file.exists()) file.delete();
            // 使用Transformer的transform()方法将DOM树转换成XML
            tf.transform(new DOMSource(document), new StreamResult(file));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
