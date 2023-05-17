//write results into CSV file
//import necessary libraries
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
	
		
	//this method is to generate a random array elements
	static float[] generateRandomArray(int size, int maxValue) 
    {
		float[] array = new float[size];
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < size; i++) 
		{
		    array[i] = random.nextFloat() * 1000;	//assign a random value
		}
		return array;
    }
	
	//radix sort algorithm for floating point
	static void sort(ArrayList<Integer>[]fromArray, ArrayList<Integer>[]toArray, int maxDigits, int counter) 
	{
	    opCounter++;
		if(counter<maxDigits)	//if the current counter is less than the max number of digits (1 comparison)
		{
	       	for(int i=0; i<10; i++) 	//iterate over each ArrayList in the fromArray (1 assignment, 1 comparison, 1 arithmetic)
			{
				opCounter = opCounter + 3;
				for(int j=0; j<fromArray[i].size(); j++) 	//iterate over each element in the current ArrayList (1 assignment, 1 comparison, 1 arithmetic)
				{
					opCounter = opCounter + 3;
					opCounter++;
			    	if(counter==(maxDigits-1))	//if it is the last digits (1 comparison)
					{
						PlaceValue = fromArray[i].get(j)/((int)Math.pow(10, counter));	//calculate the place value (1 assignment, 1 arithmetic)
						opCounter = opCounter + 2;
					}
				    	else
					{
						PlaceValue = fromArray[i].get(j)/((int)Math.pow(10, counter))%10;	//calculate the place value before the last digit (1 assignment, 2 arithmetic)
						opCounter = opCounter + 3;
					}
					toArray[PlaceValue].add(fromArray[i].get(j));	//add the element to the corresponding ArrayList in the toArray
				}
				fromArray[i].clear();	//clear the current ArrayList in the fromArray
			}
		sort(toArray,fromArray, maxDigits, counter+1);	//call method sort (1 calling)
		opCounter++; 
		}
	}
	
	//method to find the max number of digits after the floating point in the array elements
	public static void maxFloatingPlaces(float[] arr)
	{
	 	String numStr = "";
		int floatingPlaces = 0;
		for(int i=0; i<arr.length; i++) 	//(1 comparison, 1 assignment, 1 arithmetic)
		{
			numStr = String.valueOf(arr[i]);	//change the floating point become string then store it into the numStr (1 assignment)
			
			if (numStr.contains(".")) 
			{
				floatingPlaces = numStr.length() - numStr.indexOf(".") - 1;	//use the numStr to minus away the integer and store the remaining as the number of floating points (1 assignment, 2 arithmetic)
			}
			
			if (floatingPlaces > maxFloatingPoints) 	//if floating points larger than max floating points (1 comparison)
			{
				maxFloatingPoints = floatingPlaces;	//assign the current floatingPlaces to maxFloatingPoints (1 assignment)
			}
	  	}
	}
	
	//method to find the largest number of digits in the array
	public static int findLargest(float[] arr) 
	{
      	int max = Integer.MIN_VALUE;	//assign the min number to max (1 assignment)
        int maxDigits = 0; //(1 assignment)
        for (int i = 0; i < arr.length; i++)	//(1 assignment, 1 comparison, 1 arithmetic)
		{
			//assign the current array element to max if it is larger than the current max (1 comparison)
			if ((int)arr[i] > max) 
			{
				max = (int)arr[i];	//(1 assignment)
				maxDigits = (int)Math.log10(max) + 1;	//(1 assignment, 1 arithmetic)
            }
        }
    	return maxDigits;	//(1 return)
    }
	
	public static void main(String args[]) throws IOException
	{
		//create 2 arrays to hold the digits during sorting
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] array1 = new ArrayList[10];
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] array2 = new ArrayList[10];
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
    	
		for (int size = 2; size<=300; size = size + 5)
		{
			float[] input = generateRandomArray(size, 1000);	//generate random array elements
			
			//display the array elements
			System.out.print("Input Array: ");
	        for (int i = 0; i < input.length; i++) 
			{
			    System.out.printf("%.5f%n",input[i]); //print only 5 decimal points
			}
			System.out.println();
	        
		 	maxFloatingPlaces(input);	//call the method to find the max floating point for the input	(calling method)
			
			for (int i = 0; i < 10; i++) 
			{
				array1[i] = new ArrayList<Integer>();
				array2[i] = new ArrayList<Integer>();
			}
			
			//use array1 as buckets, put the elements from input array into array1 based on their smallest place value
			for(int i=0; i<input.length; i++)	//(1 assignment, 1 comparison, 1 arithmetic)
			{
				input[i] = (float)(input[i]*Math.pow(10,maxFloatingPoints)); //convert the floating points to integer for sorting (1 assignment)
				PlaceValue = (int)(input[i] % 10);	//(1 assignment)
				array1[PlaceValue].add((int)input[i]);
				opCounter = opCounter + 5;
			}
			
			int maxDigits = findLargest(input);	//(1 assignment, 1 calling method)
			//sort the array elements
			sort(array1,array2,maxDigits,1);	//(1 arithmetic)
			opCounter++;
			
			//if the number of digit in the number is even number then it will get from array 2
			if(maxDigits%2==0)	//(1 comparison)
			{
				for(int i=0; i<10; i++)	//(1 assignment, 1 comparison, 1 arithmetic)
				{
					for(int j=0; j<array2[i].size(); j++)	//(1 assignment, 1 comparison, 1 arithmetic) 
					{
						System.out.printf("%.5f%n",array2[i].get(j)/(Math.pow(10,maxFloatingPoints)));	//(1 arithmetic)
					}
	            }
			}
			//if the number of digit in the number is odd number then it will get from array 1
			else
			{
				for(int i=0; i<10; i++)	//(1 assignment, 1 comparison, 1 arithmetic)
				{
					for(int j=0; j<array1[i].size(); j++)	//(1 assignment, 1 comparison, 1 arithmetic)
					{
						System.out.printf("%.5f%n",array1[i].get(j)/(Math.pow(10,maxFloatingPoints))); //(1 arithmetic)
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

			opCounter = 0;
		}
        fileWriter.flush();
    	fileWriter.close();
	}
}
