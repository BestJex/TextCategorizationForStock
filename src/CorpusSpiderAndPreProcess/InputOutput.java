package CorpusSpiderAndPreProcess;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputOutput {
	//�����ĵ������ĵ���ÿһ�д���һ���������ĵ�������ÿһ�з����ַ���������
	public String[] readInput(String inputFileName)
    {
        //��inputFileName�е����ݶ��뵽һ���ַ���������
		List<String> ret = new ArrayList<String>();        
        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            
            String temp;
            while ((temp = br.readLine()) != null)
            {
                ret.add(temp);
            }
            br.close();            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        String[] fileString=new String[ret.size()];
        return (String[]) ret.toArray(fileString);
    }
	public void writeOutput(String[] outputContent,String outputFileName)
    {
       //��outputContent�е�����д���ļ�outputFileName��
		File f = new File(outputFileName);
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for(int i=0;i<outputContent.length;i++)
			{
				bw.write(outputContent[i]);
				bw.newLine();				
			}
			bw.close();
		}
		catch (Exception ex)
	    {
	            ex.printStackTrace();
	    }		
    }
}
