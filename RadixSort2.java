import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math.*;
import java.util.Random;

public class RadixSort2 
{
	static int PlaceValue;
	static int maxFloatingPoints = 0;
	static int opCounter = 0;
	
	@SuppressWarnings("unchecked")
	static ArrayList<Integer>[] array1 = new ArrayList[10];
	@SuppressWarnings("unchecked")
	static ArrayList<Integer>[] array2 = new ArrayList[10];
	
	static float[] generateRandomArray(int size, int maxValue) 
    {
        float[] array = new float[size];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++) 
        {
            array[i] = random.nextFloat() * 1000;
        }
        return array;
    }
	
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
	
	public static void main(String args[]) throws IOException
	{
		File csvWriter = new File("FloatingResults.csv");
    	try {
            if (csvWriter.exists()) {
                csvWriter.delete(); // Delete the existing CSV file
                csvWriter.createNewFile(); // Create a new empty CSV file
            } 
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    	
        FileWriter fileWriter = new FileWriter(csvWriter, true);
    	fileWriter.append("Number of inputs");
    	fileWriter.append(",");
    	fileWriter.append("Number of operations");
    	fileWriter.append("\n");
    	
		int size = 0; 
        for (size = 2; size<=50; size ++){
			float[] input = generateRandomArray(size, 1000);
			System.out.print("Input Array: ");
	        for (int i = 0; i < input.length; i++) 
	        {
	            System.out.printf("%.5f%n",input[i]);
	        }
	        System.out.println();
	        
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
						System.out.printf("%.5f%n",array2[i].get(j)/(Math.pow(10,maxFloatingPoints)));
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
						System.out.printf("%.5f%n",array1[i].get(j)/(Math.pow(10,maxFloatingPoints)));
						opCounter = opCounter + 3;
					}
					opCounter = opCounter + 3;
	        	}
	        
			}
			System.out.println("");
			System.out.println("Operation: " + opCounter + "\n");
			
			fileWriter.write(String.valueOf(size));
		    fileWriter.write(",");
		    fileWriter.write(String.valueOf(opCounter));
		    fileWriter.write("\n");
		}
        fileWriter.flush();
		fileWriter.close();
	}
}






