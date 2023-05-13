import java.util.ArrayList;
import java.lang.Math.*;

public class RadixSort2 
{
	static int PlaceValue;
	static int maxFloatingPoints = 0;
	static int opCounter = 0;
	
	@SuppressWarnings("unchecked")
	static ArrayList<Integer>[] array1 = new ArrayList[10];
	@SuppressWarnings("unchecked")
	static ArrayList<Integer>[] array2 = new ArrayList[10];
	
	static float[] input = {2.345f, 8.12f, 4.5678f, 6.07f, 4.1f, 1.1111f, 6.8888f, 5.3f};
	
	static void sort(ArrayList<Integer>[]fromArray, ArrayList<Integer>[]toArray, int maxDigits, int counter) 
	{
	    opCounter++;
		if(counter<maxDigits)
		{
	       	for(int i=0; i<10; i++) 
			{
				for(int j=0; j<fromArray[i].size(); j++) 
				{
				        opCounter = opCounter + 2;
			    		if(counter==(maxDigits-1))
					    {
	                			PlaceValue = fromArray[i].get(j)/((int)Math.pow(10, counter));
	                			opCounter = opCounter + 2;
	            		}
				    	else
					    {
	                			PlaceValue = fromArray[i].get(j)/((int)Math.pow(10, counter))%10;
	                			opCounter = opCounter + 2;
	            		}
				toArray[PlaceValue].add(fromArray[i].get(j));
				opCounter = opCounter + 3;
				}
			fromArray[i].clear();
			opCounter = opCounter + 3;
			}
		sort(toArray,fromArray, maxDigits, counter+1);
		opCounter = opCounter + 2; //TAKE NOTE
		}
	}
	
	public static void maxFloatingPlaces(float[] arr)
	{
	 	String numStr = "";
       	int floatingPlaces = 0;
       	opCounter++;
	    	for(int i=0; i<arr.length; i++)
		    {
	      	    numStr = String.valueOf(arr[i]);
	      	    if (numStr.contains(".")) 
			    {
            		floatingPlaces = numStr.length() - numStr.indexOf(".") - 1;
            		opCounter = opCounter + 2;
            	}
                opCounter++;
            	if (floatingPlaces > maxFloatingPoints) 
			    {
                		maxFloatingPoints = floatingPlaces;
                		opCounter++;
            	}
            	opCounter = opCounter + 4;
	   	    }
	}
	
	public static int findLargest(float[] arr) 
	{
      	int max = Integer.MIN_VALUE;
        int maxDigits = 0;
        for (int i = 0; i < arr.length; i++) 
		{
		    opCounter++;
            if ((int)arr[i] > max) 
			{
                max = (int)arr[i];
                maxDigits = (int)Math.log10(max) + 1;
                opCounter = opCounter + 3;
            }
            opCounter = opCounter + 3;
        }
        opCounter++;
    	return maxDigits;
    }
	
	public static void main(String args[]) 
	{
	 	maxFloatingPlaces(input);
	 	opCounter++;
		for (int i = 0; i < 10; i++) 
		{
			array1[i] = new ArrayList<Integer>();
			array2[i] = new ArrayList<Integer>();
			opCounter = opCounter + 5;
		}
		for(int i=0; i<input.length; i++) 
		{
			input[i] = (float)(input[i]*Math.pow(10,maxFloatingPoints));
			PlaceValue = (int)(input[i] % 10);
			array1[PlaceValue].add((int)input[i]);
			opCounter = opCounter + 6;
		}
		int maxDigits = findLargest(input);
		sort(array1,array2,maxDigits,1);
		opCounter = opCounter + 3;
		if(maxDigits%2==0)
		{
			for(int i=0; i<10; i++) 
			{
				for(int j=0; j<array2[i].size(); j++) 
				{
					System.out.print(array2[i].get(j)/(Math.pow(10,maxFloatingPoints))+" ");
					opCounter = opCounter + 3;
				}
				opCounter = opCounter + 3;
            }
		}
		else
		{
			for(int i=0; i<10; i++) 
			{
				for(int j=0; j<array1[i].size(); j++) 
				{
					System.out.print(array1[i].get(j)/(Math.pow(10,maxFloatingPoints))+" ");
					opCounter = opCounter + 3;
				}
				opCounter = opCounter + 3;
        	}
        
		}
		System.out.println("");
		System.out.println("Operation: " + opCounter);
	}
}






