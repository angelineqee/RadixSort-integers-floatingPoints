//write results into CSV file
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.Random;

public class RadixSort 
{
    static int PlaceValue;
    static int opCounter = 0;
    int arrayMin = 5;
    int arrayMax = 10;
    
    
    @SuppressWarnings("unchecked")
    static ArrayList<Integer>[] array1 = new ArrayList[10];
    @SuppressWarnings("unchecked")
    static ArrayList<Integer>[] array2 = new ArrayList[10];
    
    static int[] generateRandomArray(int size, int maxValue) 
    {
        int[] array = new int[size];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++) 
        {
            array[i] = random.nextInt(maxValue);
        }
        return array;
    }
    
    static void sort(ArrayList<Integer>[] fromArray, ArrayList<Integer>[] toArray, int maxDigits, int counter) 
    {
        opCounter++;
        if(counter < maxDigits)
        {
            for(int i=0; i<10; i++) 
            {
                opCounter = opCounter + 4;
                for(int j=0; j<fromArray[i].size(); j++) 
                {   
                    opCounter = opCounter + 5;
                    if(counter == (maxDigits-1))
                    {
                        PlaceValue = fromArray[i].get(j)/((int)Math.pow(10, counter));
                        opCounter = opCounter + 4;
                    }
                    else
                    {
                        PlaceValue = fromArray[i].get(j)/((int)Math.pow(10, counter))%10;
                        opCounter = opCounter + 5;
                    }
                    toArray[PlaceValue].add(fromArray[i].get(j));
                }
                fromArray[i].clear();
            }
            sort(toArray, fromArray, maxDigits, counter+1);
            opCounter = opCounter + 2;
        }
    }
    
    public static int findLargest(int[] arr) 
    {
        int max = Integer.MIN_VALUE;
        int maxDigits = 0;
        for (int i = 0; i < arr.length; i++) 
        {
            opCounter = opCounter + 4;
            if (arr[i] > max) 
            {
                max = arr[i];
                maxDigits = (int)Math.log10(max) + 1;
                opCounter = opCounter + 3;
            }
        }
        opCounter++;
        return maxDigits;
    }
    
    public static void main(String args[]) throws IOException  
    {
    	File csvWriter = new File("results.csv");
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
        for (size = 2; size<=1000; size ++){
			int[] input = generateRandomArray(size, 1000);
	        
	        System.out.print("Input Array: ");
	        for (int i = 0; i < input.length; i++) 
	        {
	            System.out.print(input[i] + " ");
	        }
	        System.out.println();
	        
	        for (int i = 0; i < 10; i++) 
	        {
	            opCounter = opCounter + 3;
	            array1[i] = new ArrayList<Integer>();
	            array2[i] = new ArrayList<Integer>();
	            opCounter = opCounter + 4;
	        }
	        
	        for(int i=0; i<input.length; i++) 
	        {
	            opCounter = opCounter + 3;
	            PlaceValue = input[i] % 10;
	            array1[PlaceValue].add(input[i]);
	            opCounter = opCounter + 4;
	        }
	        
	        int maxDigits = findLargest(input);
	        sort(array1, array2, maxDigits, 1);
	        opCounter = opCounter + 5;
	        
	        if(maxDigits % 2 == 0)
			{
			    	for(int i=0; i<10; i++) 
				    {
				        opCounter = opCounter + 3;
					    for(int j=0; j<array2[i].size(); j++) 
					    {
					        opCounter = opCounter + 5;
						    System.out.print(array2[i].get(j)+" ");
						    opCounter = opCounter + 3;
					    }
	            	}
			}
			else
			{
				for(int i=0; i<10; i++) 
				{
				    opCounter = opCounter + 3;
					for(int j=0; j<array1[i].size(); j++) 
					{
					    opCounter = opCounter + 4;
						System.out.print(array1[i].get(j)+" ");
						opCounter = opCounter + 3;
					}
	        	}
	        
			}
			System.out.println();
		    System.out.println("Operation: " +opCounter);

		    fileWriter.write(String.valueOf(size));
		    fileWriter.write(",");
		    fileWriter.write(String.valueOf(opCounter));
		    fileWriter.write("\n");
		    
		}
    		fileWriter.flush();
    		fileWriter.close();
    	
	}
        
}
