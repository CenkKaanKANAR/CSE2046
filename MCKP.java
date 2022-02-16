import java.io.File; 
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*; 

public class MCKP {

	public static void main(String[] args) throws Exception {
	
		File file = new File("test2.txt");
		Scanner reader = new Scanner(file);
        int m = reader.nextInt();
        int n = reader.nextInt();
        int values[] = new int[n];
        int weightsOfKnapcaks[]= new int [m];
        int weightsOfItems[][]=new int[m][n];
        int i=0;
		while(reader.hasNextInt()) {
          while(i<n) { 
        	 values[i]=reader.nextInt();
        	 i++;
          }
          i=0;
          while(i<m) {
        	  weightsOfKnapcaks[i]=reader.nextInt();
        	  i++;        			  
          }
          for(i=0;i<m;i++) {
        	  for(int j=0;j<n;j++) {
        		  weightsOfItems[i][j]=reader.nextInt();
              }  
          }
	   }
      
				
		
		float ratios [][]= new float [m][n];
		int indexes [] = new int [m*(n/10) + 3*m];
		
		for(i=0;i<m;i++) {
      	    for(int j=0;j<n;j++) {
      		   ratios[i][j]=(float)values[j]/weightsOfItems[i][j];
            }
        }		
	    int k=0;
	    for(i=0;i<m;i++){
	        for(int j=0; j<n/10;j++){
	            indexes[k]=indexOfMinElement(ratios[i]);
	            ratios[i][indexes[k]]=Float.POSITIVE_INFINITY;
	            k++;
	      }
	    }
	    
	      
	    
	    
	    
	  for(i=0;i<m*(n/10)+3*m;i++) {
	    	values[indexes[i]]=0;
	    }  
	    
	
	  
	  float sum [] = new float[n];
      //double sum2[] = new double[n]; 
      //float f = 1;
	  for(i=0;i<n;i++) {
    	   for(int j=0;j<m;j++) {
    		  if(values[i] != 0) {
    			  sum[i]+=(float)values[i]/weightsOfItems[j][i];
    			  //f *=(float)values[i]/weightsOfItems[j][i];
    			  //sum[i]+=f;
    		  }
    		}
       }             
        
       for(i=0;i<n;i++) {
    	   System.out.println(sum[i]);
       }
       
       
       
       //????????????????????????????????????????????
 	  float meanVals[] = new float[n];
 	  meanVals = getMeanValue(weightsOfItems,n,m);
 	  float stdDevs[] = new float[n];
 	  stdDevs = getStdDev( weightsOfItems, n,  m, meanVals);
 	  
 	 boolean check = true;
 	 float rate =0; 
 	 for(i=0;i<n;i++) {
    	   for(int j=0;j<m;j++) {
    		   rate =(float)values[i]/weightsOfItems[j][i];
    		   check = checkConfidence(weightsOfItems[j][i],meanVals[i],stdDevs[i],rate);
    		   if(check==false) {
             	   sum[i] = 0;
                }
    	   }
            
 	  }
       
 	  for(int z =0 ;z<n ; z++) {
 		  System.out.println("mean values "+ z +" " +meanVals[z]);
 	  }
      
 	 for(int z =0 ;z<n ; z++) {
		  System.out.println("std devs "+ z +" " +stdDevs[z]);
	  }
 	  
 	  
     for(i=0;i<n;i++) {
  	   System.out.println("new "+sum[i]);
     } 
    
       

       
       
       int index;
       boolean control=true;
       int totalValue=0;
       int totalWeights[] = new int [m];
       int status [] = new int [n];
       FileWriter writer = new FileWriter("output.txt");
         
       while(true) {
           
    	   index=indexOfMaxElement(sum);
           
           for(i=0;i<m;i++) {
        	   totalWeights[i]+=weightsOfItems[i][index];
        	   if (weightsOfKnapcaks[i]-totalWeights[i]<0) {
        		   control=false;
        		   break;
        	   }      
           }
           
          if(!control) {
        	 break;
          }
          
          totalValue+=values[index];
          status[index]=1;
          sum[index]=0;
      }
       
      System.out.println(totalValue);
      /*for(i = 0;i<n;i++)
    	  System.out.println(status[i]);*/
       
       writer.write(totalValue + "\n");
       for(i = 0;i<n;i++) 
     	  writer.write(status[i] + "\n");
      
       writer.close();
       reader.close();
	  
}
	
	static int indexOfMaxElement(float arr []) {
		
		float max = arr[0];
		int index=0;
		for (int i = 0; i < arr.length; i++) {
			if (max < arr[i]) { 
				max = arr[i];
				index = i;
			}
		}
		return index;
	}
	
   static int indexOfMaxElement1(int arr []) {
		
		int max = arr[0];
		int index=0;
		for (int i = 0; i < arr.length; i++) {
			if (max < arr[i]) { 
				max = arr[i];
				index = i;
			}
		}
		return index;
	}
		
    static int indexOfMinElement(float arr []) {
		
		float min = arr[0];
		int index=0;
		for (int i = 0; i < arr.length; i++) {
			if (min > arr[i]) { 
				min = arr[i];
				index = i;
			}
		}
		return index;
	}


	public static float[] getMeanValue(int weightsOfItems[][],int n, int m) {
		float meanVal = 0;
		float[] meanValues = new float[n];
		for(int i=0;i<n;i++) {
	    	   for(int j=0;j<m;j++) {
	    		  meanVal+=(float)weightsOfItems[j][i];
	    	   }
	       meanVal =(float)meanVal/m;
	       meanValues[i] = meanVal;
		}
		
		return meanValues;
	
	
	}

	
	public static float[] getStdDev(int weightsOfItems[][],int n, int m,float meanVals[]) {
		float stdDevVal = 0;
		float[] stdDevs = new float[n];
		for(int i=0;i<n;i++) {
	    	   for(int j=0;j<m;j++) {
	    		  stdDevVal+=(weightsOfItems[j][i]-meanVals[i])*(weightsOfItems[j][i]-meanVals[i]);
	    		  }
	    	   stdDevVal=(float)stdDevVal/(m-1);
	    	   stdDevVal=(float)Math.pow((double)stdDevVal,0.5);
	    	   stdDevs[i] = stdDevVal;
		}
		
			return stdDevs;
	}
	
	static boolean checkConfidence(int weight,float meanVal,float stdDev,float rate) {
		
		boolean result = true;
		double x = 4;
		double y = 2;
		float upperLvl = meanVal + (float)x*stdDev;
	    float lowerLvl = meanVal - (float)y*stdDev;
	    if((weight>upperLvl || weight<lowerLvl || (rate < 0.20 && rate > 0.14)  ) && weight!=0 ) {
	    	result = false;
	    }
	    return result;
	}
	
	
	
	
	

}




