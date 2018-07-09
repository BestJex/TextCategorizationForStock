package TrainAndCategorization;


import java.io.IOException;

import CorpusSpiderAndPreProcess.InputOutput;
import CorpusSpiderAndPreProcess.MySpider;
import DFAndIGSelect.DFTermSelectLocal;
import DFAndIGSelect.IGTermSelect;
import TermPresentAndLdaExtend.TermRepresent;
import SVMNecessaryPart.*;





public class TrainClassifierMain {
	public static void main(String[] args) throws IOException{
		String dir ="10crossStockData/10crossStockData1/";
		String trainFile=dir+"spiderSegment.txt";
		//Ԥ����
		MySpider.preprocess(dir);
		InputOutput rw=new InputOutput();
		
		String[] docs=rw.readInput(trainFile);


//		3.2ʹ�þֲ�DF
		DFTermSelectLocal td=new DFTermSelectLocal();
		
		
		//1��ѵ�����Ĵʼ���ѡ��ʹ������ѡ�񷽷����������ʵ����һ��String[]��terms����
		String[] terms=td.TermDictionaryMain(docs);		
		
		//2�������ʵ�������ļ�,����ļ���ѵ���׶�����ĵ�һ��������ļ�
		String termDicFile=trainFile.substring(0,trainFile.lastIndexOf("/")+1)+"termDic.txt";
		rw.writeOutput(terms, termDicFile);
		
		/////////////////////////////////////////////////////////////////////////////////
		//�ӹ���4��ѵ������������ʾ����ѵ�����Ĵʼ���String[]��docs�����������ʵ�String[]��terms��
		//�����ı���ʾ����һ��String[]��trDocs���У�
		TermRepresent tr=new TermRepresent();
		
		//2���������ʵ���ı�������������ʾ����һ��String[](trDocs)��
		String[] trDocs=tr.TermRepresentMain(docs,terms);
				
		//3��ѵ������������ʾд�뵽�ļ�������ļ���������svmҪ������ݸ�ʽ
		String trFile=trainFile.substring(0,trainFile.lastIndexOf("."))+"SegmentTR.txt";
		rw.writeOutput(trDocs, trFile);
		
		//1����ѵ��������range�ļ�
		String rangeFile=trFile+".range";
		String argv[]={"-l","0","-s",rangeFile,trFile};
		SVMScale s = new SVMScale();
		s.run(argv);
		//2ʹ��range�ļ���ѵ�������й�һ������
		String scaleFile=trFile+".scale";
		String argv1[]={"-t",scaleFile,"-r",rangeFile,trFile};
		s.run(argv1);
		////////////////////////////////////////////////////////////////////////////////
		//�ӹ���6����������ģ��
		String modelFile=scaleFile+".model";
		String argv2[]={"-s","0","-c","1","-t","0",scaleFile,modelFile};
		SVMTrain train = new SVMTrain();		
		train.run(argv2);
	}
}
