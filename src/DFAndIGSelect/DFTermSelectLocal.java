package DFAndIGSelect;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;


public class DFTermSelectLocal {
//private Dictionary wordsIndex=new Hashtable();//��1��ʹ�õ������ʵ�
//private Dictionary wordsIndex1=new Hashtable();//��2��ʹ�õ������ʵ�
	
	//2ѡ��ʹ������ѡ�񷽷����������ʵ������һ��String[]��
    //public String[] TermDictionaryMain(String[] initTerms){
    	public String[] TermDictionaryMain(String[] initTerms){	
		////////
    	//���ζ���ÿһ�����
    	System.out.println("ѵ�����Ĵ�С��"+initTerms.length);
    	String label=initTerms[0].substring(0, 1);
    	System.out.println(label);
    	List<String> allUniques = new ArrayList<String>(); 
    	//���ڴ��ÿ�����Ĳ��ظ��Ĵʸ�����ÿ������ʾ��0��1��2...
    	ArrayList<Integer> perLabelTermsNum=new ArrayList<Integer>();
    	int startTermsNum=0;//��¼ԭʼ�Ĵʸ���
    	int i=0;
    	while (i<initTerms.length)
    	{	
    		//�������ÿ���ʱ�֤���ظ��Ķ��뵽һ��list��		
			List<String> uniques = new ArrayList<String>(); 
			String r="([|])";//ÿ�д�����ĵ�ԭʼ��ʽ�ǣ����|��1|��2|...
			
    		//����ͬ����һ���࣬�������еĴ�ȥ�� ����uniques 
			System.out.println("���"+initTerms[i].substring(0, 1));
    		while (label.compareTo(initTerms[i].substring(0, 1))==0)
    		{  		    			
    				String[] terms = initTerms[i].split(r);
    				startTermsNum+=terms.length;
    				for (int j=1; j < terms.length; j++)
    					if (!uniques.contains(terms[j]))				
    						uniques.add(terms[j]) ;	    			
    			
    				if(i<initTerms.length-1)
    					i++;
    				else
    					break;
    		}
    		allUniques.addAll(uniques);//��ÿ����Ĳ��ظ��Ĵ�ͳһ���뵽һ��list�У�allUniques��
    		perLabelTermsNum.add(uniques.size());//ͬʱ��¼�������еĴʸ������Ա��ڰ����ȡ��
    		//����ȡ��һ�����Ĵ�
    		if(i<initTerms.length-1)
    			label=initTerms[i].substring(0, 1);	
    		else 
    			break;
    			
    	}
    	System.out.println("all labels:"+perLabelTermsNum.size());
    	System.out.println("startNumterms:"+startTermsNum);
    	System.out.println("endNumterms:"+allUniques.size());
    	int m=0; 
    	int start=0;//����ʵĿ�ʼλ��
    	int k=0;//�ĵ�˳��
    	
    	int[] docFreq=new int[allUniques.size()]; //��¼���дʵ��ĵ�Ƶ�ʣ�����������λ�ÿ���ȷ�������� 
    	int termIndex;//docFreq[termIndex]
    	while(m<perLabelTermsNum.size()){
    		int termsNum=perLabelTermsNum.get(m);
    		System.out.println(m+":"+termsNum);
    		//ͳ�Ƹ����еĴ����������е�DF(���ֲ�DF)
    		//���Ƚ�һ��������в��ظ��Ĵʷ���һ�������ֵ���
        	Dictionary<Object, Object> wordsIndex=new Hashtable<Object, Object>();//�ô����ݽṹ���洢�ʣ��Է����Ҵ�        	
        	for(int n=start; n <start+termsNum ; n++)			
    		{
    			AddElement(wordsIndex, allUniques.get(n), n);			
    		}
        	System.out.println("dic"+m+":"+wordsIndex.size());
        	
        	//ɨ�����������ĵ�������һ���ĵ��е�ÿ���ʣ��ڴʵ��ҵ��˴ʵ�λ�ã�DF��һ
        	while(m==Integer.parseInt(initTerms[k].substring(0, 1)))//�ǵ�ǰ����ĵ�
        	{
        		String curDoc=initTerms[k];
    			String r="([|])";//ÿ�д�����ĵ�ԭʼ��ʽ�ǣ����|��1|��2|...
    			String[] terms = curDoc.split(r);			
    			for (int j=1; j < terms.length; j++)
    			{
    				//int termIndex=GetTermIndex(terms[j]);
    				Object index=wordsIndex.get(terms[j]);
    				if (index != null) {    					
    					termIndex=(Integer)index;    				
    					docFreq[termIndex] ++;
    				}
    			}
    			if(k<initTerms.length-1)
    				k++;
    			else break;
        	}
        	
        	//������һ�����
        	start=start+termsNum;
    		m++;
    	}
    	//ȡ����ĳ����ֵ�����д��γ��µ������ʵ�
    	List<String> DFterms= new ArrayList<String>();
    	int threshold=1;//DF����ֵ
    	int firstNums=Integer.parseInt(perLabelTermsNum.get(0).toString());
    	//����һ������еĳ�����ֵ�Ĵ�ֱ�Ӽ��뵽�ʵ���
    	for (int j=0; j <firstNums; j++)
		{
			if (docFreq[j]>threshold) 
				DFterms.add(allUniques.get(j));		
		}
    	System.out.println("DFterms0:"+DFterms.size());
    	
    	//��ʣ��������еĳ�����ֵ�Ĵʲ��ظ��ļ��뵽�ʵ���
    	for (int j=firstNums; j <allUniques.size(); j++)
		{
			if (docFreq[j]>threshold) 
				if (!DFterms.contains(allUniques.get(j)))
				DFterms.add(allUniques.get(j));		
		}
    	System.out.println("DFterms:"+DFterms.size());    	
	
		String[] DFtermsDic=new String[DFterms.size()];
//		DFterms.remove("����");
//		DFterms.remove("�Ƹ�");
		return (String[]) DFterms.toArray(DFtermsDic);		
	}
    
	private Object AddElement(Dictionary<Object, Object> collection, Object key, Object newValue)
	{
		Object element=collection.get(key);
		collection.put(key, newValue);
		return element;
	}
	
}
