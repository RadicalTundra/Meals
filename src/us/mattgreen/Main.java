package us.mattgreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner keyboard;
    private Cookbook cookbook;

    public Main() throws FileNotFoundException {
        keyboard = new Scanner(System.in);
        cookbook = new Cookbook();

        FileInput indata = new FileInput("meals_data.csv");

        String line;

        System.out.println("Reading in meals information from file...");
        while ((line = indata.fileReadLine()) != null) {
            String[] fields = line.split(",");
            cookbook.addElementWithStrings(fields[0], fields[1], fields[2]);
        }

        runMenu();
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Main();
    }

    private void printMenu() {
        System.out.println("");
        System.out.println("Select Action");
        System.out.println("1. List All Items");
        System.out.println("2. List All Items by Meal");
        System.out.println("3. Search by Meal Name");
        System.out.println("4. Do Control Break");
        System.out.println("5. Exit");
        System.out.print("Please Enter your Choice: ");
    }

    private void runMenu() throws FileNotFoundException {
        boolean userContinue = true;

        while (userContinue) {
            printMenu();

            String ans = keyboard.nextLine();
            switch (ans) {
                case "1":
                    cookbook.printAllMeals();
                    break;
                case "2":
                    listByMealType();
                    break;
                case "3":
                    searchByName();
                    break;
                case "4":
                    doControlBreak();
                    break;
                case "5":
                    userContinue = false;
                    break;
            }
        }

        System.out.println("Goodbye");
        System.exit(0);
    }

    private void listByMealType() {
        // Default value pre-selected in case
        // something goes wrong w/user choice
        MealType mealType = MealType.DINNER;

        System.out.println("Which Meal Type");

        // Generate the menu using the ordinal value of the enum
        for (MealType m : MealType.values()) {
            System.out.println((m.ordinal() + 1) + ". " + m.getMeal());
        }

        System.out.print("Please Enter your Choice: ");
        String ans = keyboard.nextLine();

        try {
            int ansNum = Integer.parseInt(ans);
            if (ansNum < MealType.values().length) {
                mealType = MealType.values()[ansNum - 1];
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid Meal Type " + ans + ", defaulted to " + mealType.getMeal() + ".");
        }

        cookbook.printMealsByType(mealType);
    }

    private void searchByName() {
        keyboard.nextLine();
        System.out.print("Please Enter Value: ");
        String ans = keyboard.nextLine();
        cookbook.printByNameSearch(ans);
    }
    
    private void doControlBreak() throws FileNotFoundException{
        // sotre calories, but you don't need to store what meal type it is in an array. That's just being checked.
        File file = new File("meals_data.csv");
        Scanner sc = new Scanner(file);
        String currentMealType = "nullValue";
        String newMealType = "";
        List<Integer> calories = new ArrayList<>();
        System.out.println("\nMeal        Total  Mean  Min  Max Median");
        while(sc.hasNextLine()){
        String line = sc.nextLine();
        String[] lineArray = line.split(",");
            newMealType = (String)lineArray[0];
            //check to see if the current meal is the one brought in.
            if(newMealType.equals(currentMealType) || currentMealType.equals("nullValue")){
                calories.add(Integer.parseInt(lineArray[2]));
                if(currentMealType.equals("nullValue")){
                    currentMealType = newMealType;
                }
            }
            else{
                //Just a simple bubble sort to get everything in order.
                int totalEntries = calories.size(); 
                int totalCalories = 0;
                int median = totalEntries/2;
                for (int i = 0; i < totalEntries-1; i++) {
                    for (int j = 0; j < totalEntries-i-1; j++){
                        if (calories.get(j).compareTo(calories.get(j+1)) > 0) 
                        { 
                            int temp = calories.get(j); 
                            calories.set(j, calories.get(j+1));
                            calories.set(j+1, temp);
                        }
                    }
                }
                //adding up all the calories
                for(int i = 0; i < totalEntries; i++){
                    totalCalories += calories.get(i);
                }
                //finding da mean
                double daMean = totalCalories/(totalEntries * 1.0);
                String mean = String.format("%.2f", daMean);
                //system output
                System.out.println(currentMealType + "   " + totalCalories + "  " + mean + "  " + calories.get(0) + "  " + calories.get(totalEntries-1) + "  " + calories.get(median));
                currentMealType = newMealType;
                //clearing the calories and inputting the first one of the next category that we sucked in
                calories.clear();
                calories.add(Integer.parseInt(lineArray[2]));
            }
        }
        
    }
}
