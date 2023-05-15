//write results into CSV file
//import neccessary libraries
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.Random;

public class RadixSort 
{
	//declare variables
    static int PlaceValue; 	//variable to store the current place value during sorting
    static int opCounter = 0;	//counter to track the number of primitive operations

    //create 2 arrays to hold the digits during sorting
    @SuppressWarnings("unchecked")
    static ArrayList<Integer>[] array1 = new ArrayList[10];
    opCounter++;
    @SuppressWarnings("unchecked")
    static ArrayList<Integer>[] array2 = new ArrayList[10];
    opCounter++;
    
    
	//this method is to generate a random array elements
    static int[] generateRandomArray(int size, int maxValue) 
    {
        int[] array = new int[size];	
	opCounter++;
        Random random = new Random(System.currentTimeMillis());
	opCounter++;
        for (int i = 0; i < size; i++) 
        {
		opCounter = opCounter + 4;
            	array[i] = random.nextInt(maxValue);	//assign a random value for each array element
		opCounter++;
        }
	opCounter++;
        return array;
    }
    
	//radix sort algorithm
    static void sort(ArrayList<Integer>[] fromArray, ArrayList<Integer>[] toArray, int maxDigits, int counter) 
    {
	opCounter++;
	if(counter < maxDigits)	//if the current counter is less than the max number of digits
	{
	    for(int i=0; i<10; i++) 	//iterate over each ArrayList in the fromArray
	    {
		opCounter = opCounter + 4;
		for(int j=0; j<fromArray[i].size(); j++) 	//iterate over each element in the current ArrayList
		{
		    opCounter = opCounter + 4;
		    opCounter++;
		    if(counter == (maxDigits-1))	//if it is the last digits
		    {
			PlaceValue = fromArray[i].get(j)/((int)Math.pow(10, counter));	//calculate the place value
			opCounter = opCounter + 4; //operation counter for assignment and division
		    }
		    else
		    {
			PlaceValue = fromArray[i].get(j)/((int)Math.pow(10, counter))%10;	//calculate the place value before the last digit
			opCounter = opCounter + 5;
		    }
		    toArray[PlaceValue].add(fromArray[i].get(j));	//add the element to the corresponding ArrayList in the toArray
		    opCounter = opCounter + 4;
		}
		fromArray[i].clear();	//clear the current ArrayList in the fromArray
		opCounter = opCounter + 2;
	    }
	    sort(toArray, fromArray, maxDigits, counter+1);
	    opCounter = opCounter + 1;
	}
    }
    
	//method to find the largest number of digits in the array
    public static int findLargest(int[] arr) 
    {
        int max = Integer.MIN_VALUE;	//assign the min number to max
	opCounter++;
        int maxDigits = 0;
	opCounter++;
        for (int i = 0; i < arr.length; i++) 
        {
	    opCounter = opCounter + 4;
	    opCounter++;
		//assign the current array element to max if it is larger than the current max
            if (arr[i] > max) 
            {
                max = arr[i];
                maxDigits = (int)Math.log10(max) + 1;
		opCounter = opCounter + 5;	    
            }
        }
	opCounter++;
        return maxDigits;
    }
    
    public static void main(String args[]) throws IOException  
    {
	    //create a new csv file to store the results
	    //there are no incrementation of opCounter in the file processing section as it is not counted as the algorithm of sorting
    	File csvWriter = new File("results.csv");
    	try {
            if (csvWriter.exists()) 
	    {
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
    	
        for (size = 2; size<=1000; size ++){
		opCounter = opCounter + 4;
		int[] input = generateRandomArray(size, 1000);	//generate random array elements
		opCounter = opCounter + 2;
	        
		//display the array elements
	        System.out.print("Input Array: ");
	        for (int i = 0; i < input.length; i++) 
	        {
	            System.out.print(input[i] + " ");
	        }
	        System.out.println();
	        
	        for (int i = 0; i < 10; i++) 
	        {
		    opCounter = opCounter + 4;
	            array1[i] = new ArrayList<Integer>();
	            array2[i] = new ArrayList<Integer>();
		    opCounter = opCounter + 2;
	        }
	        
		//use array1 as buckets, put the elements from input array into array1 based on their smallest place value
	        for(int i=0; i<input.length; i++) 
	        {
		    opCounter = opCounter + 4;
	            PlaceValue = input[i] % 10;
	            array1[PlaceValue].add(input[i]);
		    opCounter = opCounter + 6;
	        }
	        
		
	        int maxDigits = findLargest(input);
		opCounter = opCounter + 2;
		//sort the array elements
	        sort(array1, array2, maxDigits, 1);
		opCounter ++;
	        
		opCounter = opCounter + 2;
	        if(maxDigits % 2 == 0)
			{
			    	for(int i=0; i<10; i++) 
				opCounter = opCounter + 4;
				{
					for(int j=0; j<array2[i].size(); j++) 
				    	{
						opCounter = opCounter + 4;
						System.out.print(array2[i].get(j)+" ");
				    	}
	            		}
			}
			else
			{
				for(int i=0; i<10; i++) 
				{
					opCounter = opCounter + 4;
					for(int j=0; j<array1[i].size(); j++) 
					{
						opCounter = opCounter + 4;
						System.out.print(array1[i].get(j)+" ");
					}
	        		}
	        
			}
			System.out.println();
		//display the number of operations of the code
		    System.out.println("Operation: " +opCounter);

		//write the number of inputs and number of operations of each iteration into the csv file to generate graph later
		    fileWriter.write(String.valueOf(size));
		    fileWriter.write(",");
		    fileWriter.write(String.valueOf(opCounter));
		    fileWriter.write("\n");
		    
		}
    		fileWriter.flush();
    		fileWriter.close();
    	
	}
        
}
