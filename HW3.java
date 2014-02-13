import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;


public class HW3 {
	static int SAMPLE_N=10000;
	
	public static void main(String arg[]){
			
	
	String filePath="/Users/songyi/Desktop/1.txt";
	String filePathOut="/Users/songyi/Desktop/2.txt";
	
	ArrayList <String> list=lines(filePath);
	ArrayList <Double> prob=new <Double> ArrayList();
	for(int ii=0;ii<list.size();ii++){
		// System.out.println(i);
		 String i=list.get(ii);
		 int start=0;
		 int end=0;
         for(int j=1;j<i.length();j++){
        	 if (i.charAt(j-1)=='	') {
        		 end++;        		 
        		 if (start==0) start=j;
        	 }
         }    
        		 end+=start;      
        		 list.set(ii, i.substring(0,start-1));
        		 prob.add(Double.parseDouble(i.substring(end-1)));
		// System.out.println(list.get(ii));
	}
	  System.out.println(list.size());
	 
	  
	 ArrayList<String> Sample=gerSample(list,prob);
	 System.out.println(list.size()+" "+prob.size());	
		FileWriter writer;
     try {
         writer = new FileWriter(filePathOut);
         for(String i:Sample)
         writer.write(i+"\r");
         writer.flush();
         writer.close();
     } catch (IOException e) {
         e.printStackTrace();
     }
   

	  Map <String, Integer> map=wordCount(Sample);
	  System.out.println("MLE E1: "+(double)map.get("the")/10000);	
	  int singleton=0;
      for(String o:map.keySet()){
    	  if (map.get(o)==1) singleton++;  	  
      }
      System.out.println("MLE E2: "+(double)singleton/10000);	
      System.out.println("MLE E3: "+(double)0/10000);	
      int doubleton=0;
      
      for(String o:map.keySet()){
    	  if (map.get(o)==2) doubleton++;  
      }
      System.out.println("GT-e E2: "+(double)2*doubleton/10000);	
      System.out.println("GT-e E3: "+(double)singleton/10000);	
      double trueThe=0;
      double trueE2=0;
      double trueE3=0;
	  for(int i=0;i<list.size();i++){
		  	 
		  if (list.get(i).equals("the")) trueThe=prob.get(i);
		  if (map.containsKey(list.get(i))) {
			  trueE3=trueE3+prob.get(i);
		      if (map.get(list.get(i))==1) trueE2=trueE2+prob.get(i);	
		  }
	  }
	  trueE2=trueE2;
	  trueE3=1-trueE3;
	  
	  System.out.println("TRUE E1: "+(double)trueThe);	
	  System.out.println("TRUE E2: "+(double)trueE2);	
	  System.out.println("TRUE E3: "+(double)trueE3);	
	     
	  
	  ArrayList<String> Sample2=gerSample(list,prob);
	  Map <String, Integer> map2=wordCount(Sample2);
	  double s2E2=0;
	  double s2E3=0;
	  for(String i:Sample2){
		  if (!map.containsKey(i)) s2E3++;
		  else if (map.get(i)==1) s2E2++;
	  }
	  System.out.println("Sample2 E1: "+(double)map2.get("the"));
	  System.out.println("Sample2 E2: "+s2E2);
	  System.out.println("Sample2 E3: "+s2E3);
	  	
	}

	
	public static Map<String,Integer> wordCount(ArrayList<String> Sample){
		
		Map <String, Integer> map=new <String, Integer> HashMap();
		for(String i:Sample){
			if (map.get(i)==null) map.put(i,1);
			else map.put(i, map.get(i)+1);			
		}
		return map;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public static ArrayList<String> gerSample(ArrayList<String> words,ArrayList<Double> probs){
		ArrayList<String> Sample=new ArrayList<String>();
		
		for(int i=0;i<SAMPLE_N;i++){
			double w=Math.random();
		    double prob=0;
		    int j=0;
			while(prob<=w){
				prob+=probs.get(j);
				j++;
			}
			Sample.add(words.get(j-1));	
			//System.out.println(words.get(j-1));
			 
		}
		
		 return Sample;
			
	}
	
	
	
	
	
	
	
	
	
	public static ArrayList<String> lines(String filePath){
        ArrayList <String> list=new <String>ArrayList();
		 try {
			  String encoding="GBK";
		      File file=new File(filePath);
		      if(file.isFile() && file.exists()){ //判断文件是否存在
		          InputStreamReader read = new InputStreamReader(
		          new FileInputStream(file),encoding);//考虑到编码格式
		          BufferedReader bufferedReader = new BufferedReader(read);
		          String lineTxt = null;
		          while((lineTxt = bufferedReader.readLine()) != null){
		             
		              list.add(lineTxt);		          
		          }
		          read.close();
		      }else{
		          System.out.println("找不到指定的文件");
		      }
		      } catch (Exception e) {
		          System.out.println("读取文件内容出错");
		          e.printStackTrace();
		      }

		return list;
		
		
	}
	 
}
