
//write results into CSV file
//import necessary libraries
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.Random;

public class RadixSort 
{
    // declare variables
    static int PlaceValue; // variable to store the current place value during sorting
    static int opCounter = 0; // counter to track the number of primitive operations

    // this method is to generate a random array elements
    static int[] generateRandomArray(int size, int maxValue) 
    {
        int[] array = new int[size];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++) 
        {
            array[i] = random.nextInt(maxValue); // assign a random value for each array element
        }
        return array;
    }

    // radix sort algorithm
    static void sort(ArrayList<Integer>[] fromArray, ArrayList<Integer>[] toArray, int maxDigits, int counter) 
    {
        opCounter++;
        if (counter < maxDigits) // if the current counter is less than the max number of digits ( 1 comparison )
        {
            for (int i = 0; i < 10; i++) // iterate over each ArrayList in the fromArray ( 1 assignment, 1 comparison, 1 arithmetic)
            {
                opCounter = opCounter + 3;
                for (int j = 0; j < fromArray[i].size(); j++) // iterate over each element in the current ArrayList ( 1 assignment, 1 comparison, 1 arithmetic)
                {
                    opCounter = opCounter + 3;
                    opCounter++;
                    if (counter == (maxDigits - 1)) // if it is the last digits (1 comparison)
                    {
                        PlaceValue = fromArray[i].get(j) / ((int) Math.pow(10, counter)); // calculate the place value ( 1 assignment, 1 arithmetic )
                        opCounter = opCounter + 2; // operation counter for assignment and division
                    } else 
                    {
                        PlaceValue = fromArray[i].get(j) / ((int) Math.pow(10, counter)) % 10; // calculate the place value before the last digit ( 1 assignment, 2 arithmetic )
                        opCounter = opCounter + 3;
                    }
                    toArray[PlaceValue].add(fromArray[i].get(j)); // add the element to the corresponding ArrayList in the toArray
                }
                fromArray[i].clear(); // clear the current ArrayList in the fromArray
            }
            sort(toArray, fromArray, maxDigits, counter + 1); // ( 1 calling method )
            opCounter++;
        }
    }

    // method to find the largest number of digits in the array
    public static int findLargest(int[] arr) 
    {
        int max = Integer.MIN_VALUE; // assign the min number to max
        int maxDigits = 0;
        for (int i = 0; i < arr.length; i++) 
        {
            // assign the current array element to max if it is larger than the current max
            if (arr[i] > max) 
            {
                max = arr[i];
                maxDigits = (int) Math.log10(max) + 1;
            }
        }
        return maxDigits;
    }

    public static void main(String args[]) throws IOException 
    {
        Scanner sc = new Scanner (System.in);

        // create 2 arrays to hold the digits during sorting
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] array1 = new ArrayList[10];
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] array2 = new ArrayList[10];

        // create a new csv file to store the results there are no incrementation of opCounter in the file processing section as it is not counted as the algorithm of sorting
        File csvWriter = new File("results.csv");
        FileWriter fileWriter = new FileWriter(csvWriter, true);

        try 
        {
            if (!csvWriter.exists()) 
            {
                csvWriter.createNewFile(); // Create a new empty CSV file
				fileWriter.append("Number of inputs");
				fileWriter.append(",");
				fileWriter.append("Number of operations");
				fileWriter.append("\n");
            }
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred: " + e.getMessage());
        }

        System.out.print("How many number you want to sort: ");
        int size = sc.nextInt();
        System.out.println();
    
        int[] input = generateRandomArray(size, 1000); // generate random array elements

        // display the array elements
        System.out.print("Input Array: \t");
        for (int i = 0; i < input.length; i++) 
        {
            System.out.print(input[i] + " ");
        }

        for (int i = 0; i < 10; i++) 
        {
            array1[i] = new ArrayList<Integer>();
            array2[i] = new ArrayList<Integer>();
        }

        // use array1 as buckets, put the elements from input array into array1 based on their smallest place value
        for (int i = 0; i < input.length; i++) 
        {
            PlaceValue = input[i] % 10;
            array1[PlaceValue].add(input[i]);
            opCounter = opCounter + 5;
        }

        int maxDigits = findLargest(input);
        // sort the array elements
        sort(array1, array2, maxDigits, 1);
        opCounter++;

        System.out.println();
        System.out.print("\nAfter Sorting: \t");
        // if the number of digit in the number is even number then it will get from array 2  
        if (maxDigits % 2 == 0) 
        {
            for (int i = 0; i < 10; i++) 
            {
                for (int j = 0; j < array2[i].size(); j++) 
                {
                    System.out.print(array2[i].get(j) + " ");
                }
            }
        }
        // if the number of digit in the number is odd number then it will get from array 1
        else 
        {
            for (int i = 0; i < 10; i++) 
            {
                for (int j = 0; j < array1[i].size(); j++) 
                {
                    System.out.print(array1[i].get(j) + " ");
                }
            }

        }
        System.out.println();
        // display the number of operations of the code
        System.out.println("\nNo of input: \t" + size);
        System.out.println("Operation: \t" + opCounter);
        System.out.println();
        System.out.println();

        // write the number of inputs and number of operations of each iteration into
        // the csv file to generate graph later
        fileWriter.write(String.valueOf(size));
        fileWriter.write(",");
        fileWriter.write(String.valueOf(opCounter));
        fileWriter.write("\n");
    
        fileWriter.flush();
        fileWriter.close();

        sc.close();
    }

}
