package CorpusSpiderAndPreProcess;


import java.io.IOException;
import java.io.StringReader;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

//��ģ��Ĺ����ǽ���ʼ��ѵ�����ļ���������ı����е�ÿ���ı������дʣ�ȥͣ�ôʵ�Ԥ����
public class PreProcess {

//�Գ�ʼ��ѵ����Ԥ����֮���γɵ�ѵ����������һ��String[]��
public String[] preProcessMain(String[] InputDocs)throws IOException {
	
	String[] OutputDocs=new String[InputDocs.length];
	String row="";
	String t = null;
	int i=0;
	while(i<InputDocs.length) {
		row+=InputDocs[i].substring(0, 1);	//д ���
		t=InputDocs[i].substring(2);
		IKSegmentation ikSeg = new IKSegmentation(new StringReader(t) ,true);

		Lexeme l = null;
		while( (l = ikSeg.next()) != null)
		{
			//��CJK_NORMAL��Ĵ�д��Ŀ���ļ�
			if(l.getLexemeType() == Lexeme.TYPE_CJK_NORMAL)	
			{
				//�����ڴ�����жϴ˴��Ƿ�Ϊͣ�ôʣ���������д��Ŀ���ļ���
				row+='|' + l.getLexemeText();
			}
		}
		OutputDocs[i]=row;
		i++;
		row="";
		System.out.println("1");
	}
	return OutputDocs;
}
}
