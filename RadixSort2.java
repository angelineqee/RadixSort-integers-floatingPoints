//write results into CSV file
//import neccessary libraries
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math.*;
import java.util.Random;

public class RadixSort2 
{
	//declare variables
	static int PlaceValue;	//variable to store the current placa value during sorting
	static int maxFloatingPoints = 0;	//variable to store the max number of floating points
	static int opCounter = 0;	//counter to track the number of primitive operations
	
	//create 2 arrays to hold the digits during sorting
	@SuppressWarnings("unchecked")
	static ArrayList<Integer>[] array1 = new ArrayList[10];
	opCounter++;
	@SuppressWarnings("unchecked")
	static ArrayList<Integer>[] array2 = new ArrayList[10];
	opCounter++;
	
	//this method is to generate a random array elements
	static float[] generateRandomArray(int size, int maxValue) 
    	{
		float[] array = new float[size];
		opCounter++;
		Random random = new Random(System.currentTimeMillis());
		opCounter++;
		for (int i = 0; i < size; i++) 
		{
			opCounter = opCounter + 4;
		    	array[i] = random.nextFloat() * 1000;	//assign a random value
			opCounter = opCounter + 2;
		}
		opCounter++;
		return array;
    	}
	
	//radix sort algorothm for floating point
	static void sort(ArrayList<Integer>[]fromArray, ArrayList<Integer>[]toArray, int maxDigits, int counter) 
	{
	    	opCounter++;
		if(counter<maxDigits)	//if the current counter is less than the max number of digits
		{
	       	for(int i=0; i<10; i++) 	//iterate over each ArrayList in the fromArray
			{
				opCounter = opCounter + 4;
				for(int j=0; j<fromArray[i].size(); j++) 	//iterate over each element in the current ArrayList
				{
					opCounter = opCounter + 4;
					opCounter++;
			    		if(counter==(maxDigits-1))	//if it is the last digits
					{
	                			PlaceValue = fromArray[i].get(j)/((int)Math.pow(10, counter));	//calculate the place value
	                			opCounter = opCounter + 4;
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
		sort(toArray,fromArray, maxDigits, counter+1);
		opCounter++; 
		}
	}
	
	//method to find the max number of floating point in the array elements
	public static void maxFloatingPlaces(float[] arr)
	{
	 	String numStr = "";
       		int floatingPlaces = 0;
       		opCounter++;
	    	for(int i=0; i<arr.length; i++)
		{
			opCounter = opCounter+ 4;
			numStr = String.valueOf(arr[i]);	//change the floating point become string then store it into the numStr
	      	    	opCounter++;
			
			if (numStr.contains(".")) 
			{
            			floatingPlaces = numStr.length() - numStr.indexOf(".") - 1;	//use the numStr to minus away the integer and store the remaining as the number of floating points
            			opCounter = opCounter + 5;
            		}
			
               		opCounter++;
            		if (floatingPlaces > maxFloatingPoints) 	//if floating points larger than max floating points 
		    	{
                		maxFloatingPoints = floatingPlaces;	//assign the current floatingPlaces to maxFloatingPoints
                		opCounter++;
            		}
	  	}
	}
	
	//method to find the largest number of digits in the array
	public static int findLargest(float[] arr) 
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
			if ((int)arr[i] > max) 
			{
               			max = (int)arr[i];
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
		File csvWriter = new File("FloatingResults.csv");
    		try 
		{
            		if (csvWriter.exists()) 
			{
               			csvWriter.delete(); // Delete the existing CSV file
                		csvWriter.createNewFile(); // Create a new empty CSV file
            		} 
        	} 
		catch (IOException e) 
		{
            		System.out.println("An error occurred: " + e.getMessage());
        	}
    	
		FileWriter fileWriter = new FileWriter(csvWriter, true);
		fileWriter.append("Number of inputs");
		fileWriter.append(",");
		fileWriter.append("Number of operations");
		fileWriter.append("\n");
    	
        	for (size = 2; size<=50; size ++)
		{
			opCounter = opCounter + 4;
			float[] input = generateRandomArray(size, 1000);	//generate random array elements
			opCounter = opCounter+ 2;
			
			//display the array elements
			System.out.print("Input Array: ");
	        	for (int i = 0; i < input.length; i++) 
			{
			    System.out.printf("%.5f%n",input[i]);
			}
			System.out.println();
	        
		 	maxFloatingPlaces(input);	//call the method to find the max floating point for the input
		 	opCounter++;
			
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
				opCounter = opCounter+ 4;
				input[i] = (float)(input[i]*Math.pow(10,maxFloatingPoints));
				PlaceValue = (int)(input[i] % 10);
				array1[PlaceValue].add((int)input[i]);
				opCounter = opCounter + 9;
			}
			
			int maxDigits = findLargest(input);
			opCounter = opCounter + 2;
			//sort the array elements
			sort(array1,array2,maxDigits,1);
			opCounter++;
			
			opCounter = opCounter + 2;
			if(maxDigits%2==0)
			{
				for(int i=0; i<10; i++) 
				{
					opCounter = opCounter+ 4;
					for(int j=0; j<array2[i].size(); j++) 
					{
						opCounter = opCounter + 4;
						System.out.printf("%.5f%n",array2[i].get(j)/(Math.pow(10,maxFloatingPoints)));
						opCounter = opCounter + 3;
\					}
	            		}
			}
			else
			{
				for(int i=0; i<10; i++) 
				{
					opCounter= opCounter + 4;
					for(int j=0; j<array1[i].size(); j++) 
					{
						opCounter = opCounter + 4;
						System.out.printf("%.5f%n",array1[i].get(j)/(Math.pow(10,maxFloatingPoints)));
						opCounter = opCounter + 3;
					}
	        		}
	        
			}
			System.out.println("");
			//diaplay the number of operations of the code
			System.out.println("Operation: " + opCounter + "\n");
			
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






