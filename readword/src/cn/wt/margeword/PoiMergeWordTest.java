package cn.wt.margeword;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;


//poi�ϲ����word����
public class PoiMergeWordTest {
	public static void main (String[] args) throws Exception {
		
		File f=new File("C:\\Users\\thinkpad\\Desktop\\��������ϲ�\\111");
		OutputStream dest = new FileOutputStream("C:\\Users\\thinkpad\\Desktop\\��������ϲ�\\�����ļ�.docx");
		File[] listFiles = f.listFiles();
		
		//����һ��ȡ��
		OPCPackage src1Package = OPCPackage.open(new FileInputStream(listFiles[0]));
		XWPFDocument src1Document = new XWPFDocument(src1Package);
		CTBody src1Body = src1Document.getDocument().getBody();
		String srcString = src1Body.xmlText();
		
		//��ȡ��һ������
		String prefix = srcString.substring(0,srcString.indexOf(">")+1);
		String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
		String sufix = srcString.substring( srcString.lastIndexOf("<") );
	    //ƴ����������
		StringBuffer otherPart=new StringBuffer();
		XWPFDocument otherDocument = null;
		for(int i=1;i<listFiles.length;i++){
			OPCPackage otherPackage = OPCPackage.open(new FileInputStream(listFiles[i]));
			otherDocument = new XWPFDocument(otherPackage); 
			CTBody  otherBody =  otherDocument.getDocument().getBody();
			XmlOptions optionsOuter = new XmlOptions();
			optionsOuter.setSaveOuter();
			String appendString = otherBody.xmlText(optionsOuter);
			String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
			otherPart.append(addPart);
		}
		CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+otherPart.toString()+sufix);
		src1Body.set(makeBody);
		src1Document.write(dest);
		
		src1Document.close();
		otherDocument.close();
		
		
		}

}
