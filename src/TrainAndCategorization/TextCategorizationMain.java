package TrainAndCategorization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import CorpusSpiderAndPreProcess.InputOutput;
import CorpusSpiderAndPreProcess.PreProcess;
import SVMNecessaryPart.SVMPredict;
import SVMNecessaryPart.SVMScale;
import TermPresentAndLdaExtend.TermRepresent;


public class TextCategorizationMain  {
	private static double unhappy=0;
	private static double happy=0;
	private static double indiffient=0;
	public static void main(String[] args) throws IOException{
		ArrayList<Integer> list =new ArrayList<Integer>();
	//1����������ı����ļ�
	String dir="10crossStockData/10crossStockData1/";
	String CorpusFile=dir+"test/test.txt";
	InputOutput rw=new InputOutput();
	String[] inputCorpus=rw.readInput(CorpusFile);
	
	
	//2Ԥ����
	PreProcess p=new PreProcess();
	String[] docs=p.preProcessMain(inputCorpus);
	String trainFileSeg=CorpusFile.substring(0,CorpusFile.lastIndexOf("."))+"Segment.txt";		
	rw.writeOutput(docs, trainFileSeg);
	System.out.println("Ԥ�������");
	//3�ı���ʾ
	//���������ʵ��ļ�
	String termDicFile=dir+"termDic.txt";
	String[] terms=rw.readInput(termDicFile);
	
	//���������ʵ�����ı���ʾ
	TermRepresent tr=new TermRepresent();		
	String[] trDocs=tr.TermRepresentMain(docs,terms);
	
	//���ı���ʾд���ļ�
	String trFile=dir+"testSetTR.txt";
	rw.writeOutput(trDocs, trFile);
	
	
	//4scale����
	//2ʹ��range�ļ���ѵ�������й�һ������
	String scaleFile=trFile+".scale";
	String rangeFile=dir+"spiderSegmentSegmentTR.txt.range";
	String argv[]={"-t",scaleFile,"-r",rangeFile,trFile};
	SVMScale s = new SVMScale();
	s.run(argv);
	
	
	//5 �ı�����
	//���������ֱ��Ǿ���Scale����Ĵ�����������ļ�����ŷ�����ģ�͵��ļ����洢���������ļ�
	//String scaleFile="10crossdatairis_libsvm_scale/3/testSet.txt";
	//String modelFile="10crossdatairis_libsvm_scale/3/InitTrainSet.txt.model";
	
	String modelFile=dir+"spiderSegmentSegmentTR.txt.scale.model";
	String predictFile=scaleFile+"predict.txt";
	String argv1[]={scaleFile,modelFile,predictFile};
	
	//String modelFile="Data/InitTrainSetSegmentTR.txt.model";
	//String predictFile=trFile+".pred0ict";
	//String argv1[]={trFile,modelFile,predictFile};
	SVMPredict predict = new SVMPredict();		
	ArrayList<Integer> list1 =new ArrayList<Integer>(predict.run(argv1));
	System.out.println(list1);
	for(Integer index:list1){
		if(index==0)
			indiffient++;
		else if(index==1)
			happy++;
		else if(index==2)
			unhappy++;
	}
	System.out.println("����������ռ�����ٷֱ�");
	System.out.println("indiffient"+": "+indiffient/list1.size());
	System.out.println("happy"+": "+happy/list1.size());
	System.out.println("unhappy"+": "+unhappy/list1.size());
	System.out.println("result(1-2/1+2):"+(happy-unhappy)/(happy+unhappy)*100+"%");
	
}
	
}
