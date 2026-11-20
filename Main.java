// 12 - Bobola

/* TEST CASES:

1. Invalid Log in
Inputs:
    Username: admin
    password: 00
output: 
    INVALID LOGIN!

2. Valid Log in
Inputs:
    Username: Admin1
    password: 0000
output
    ===========================
    |        MAIN MENU        |
    ===========================
    ...

3. Add Item > Invalid Quantity
Inputs:
    Enter your choice: 2
    Enter Item name: shirt
    Enter Quantity: -1
    Enter Price per Item: 100
Outputs:
    Invalid quantity.
    Item not added. Try again.

4. Add Item > Invalid Quantity
Inputs:
    Enter your choice: 2
    Enter Item name: shirt
    Enter Quantity: 10
    Enter Price per Item: -1
Outputs:
    Invalid price.
    Items not added. Try again.

5. Add Item > Correct
Inputs:
    Enter your choice: 2
    Enter Item name: shirt
    Enter Quantity: 10
    Enter Price per Item: 250
Outputs:
    Item Added Successfully!

5. Add Another Item
Inputs:
    Enter your choice: 2
    Enter Item name: tote bag
    Enter Quantity: 20
    Enter Price per Item: 150
Outputs:
    Item Added Successfully!

7. Checking Stock
Inputs:
    Enter your choice: 4
Outputs:
    ===========================
    - - - CLUB INVENTORY - - -
    ===========================
    * * * * * ITEM #1 * * * * *
    Item Name: shirt
    Price: PHP 250.00
    Quantity: 10
    * * * * * ITEM #2 * * * * *
    Item Name: tote bag
    Price: PHP 150.00
    Quantity: 20

8. Modifying an Item > selecting a non existent item
Inputs:
    Enter your choice: 3
    Input item number: 3
Output:
    Invalid item number, please select from the options above
    Input item number:

9. Modifying an Item > changing the price
Inputs:
    Enter your choice: 3
    Input item number: 1
    Choice: 3
    New Price: 249.99
Output:
    Updated Price

10. Modifying an Item > invalid quantity
Inputs:
    Enter your choice: 3
    Input item number: 1
    Choice: 2
    New Price: -1
Output:
    Invalid Quantity

11. Deleting an Item > invalid itemnumber
Inputs:
    Enter your choice: 6
    Input item number: 3
Outputs:
    Invalid item number, please select from the options above
    Input item number:

12. Deleting an Item
Inputs:
    Enter your choice: 6
    Input item number: 2
Outputs:
    Deleted Item

13. Purchasing > Invalid item number
Inputs:
    Enter your choice: 5
    Input item number: 2
Outputs: 
    Invalid item number, pelase select from the options above
    
14. Purchasing > Invalid quantity
Inputs:
    Enter your choice: 5
    Input item number: 1
    Quantity: 11
Outputs: 
    You are attempting to purchase more items than there are in stock, please try again

15. Purchasing > Invalid item number
Inputs:
    Enter your choice: 5
    Input item number: 1
    Quantity: 3
Outputs: 
    Total: P749.97
*/

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
   
    static final int maxNumItems = 99;
    static double totalRevenue = 0;
    static String[] itemNames = new String[maxNumItems];
    static int[] itemQuantities = new int[maxNumItems];
    static double[] itemPrices = new double[maxNumItems];

    static boolean isLoggedIn = false;
    static int itemNum = 0;
    static String username;
    static String password;
    static String[] validUserNames = {"Admin1", "Admin2", "Officer"};
    static String[] userPasswords = {"0000", "1111", "2222"};

    public static void main(String[] args) {
        boolean running = true;
        int choice;

        do{
            if (!isLoggedIn) {
                System.out.println("===========================");
                System.out.println("|          LOGIN          |");
                System.out.println("===========================");
                System.out.print("Username:");
                username = sc.nextLine();
                System.out.print("Password:");
                password = sc.nextLine();
                
                if (checkLogin(username, password)){
                    isLoggedIn = true;
                }
                else{
                    System.out.println("\nINVALID LOGIN!\n");
                }
            } 
            
            else{
                    System.out.println("===========================");
                    System.out.println("|        MAIN MENU        |");
                    System.out.println("===========================");
                    System.out.printf("Total Revenue: P%.2f%n", totalRevenue);
                    System.out.println("[1] Logout");
                    System.out.println("[2] Add a New Item");
                    System.out.println("[3] Modify Existing Items");
                    System.out.println("[4] View Current Stock");
                    System.out.println("[5] Purchase Items");
                    System.out.println("[6] Delete Items");
                    System.out.println("[7] Exit");
                
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {
                        case 1:
                            isLoggedIn = false;
                            break;
                        case 2:
                            addStock();
                            break;
                        case 3:
                            modifyStock();
                            break;
                        case 4:
                            viewStock();
                            break;
                        case 5:
                            totalRevenue += purchaseItems();
                            break;
                        case 6:
                            deleteItem();
                            break;
                        case 7:
                            System.out.println("Exiting C.A.S.H...");
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid Choice. Try Again.");          
                    }
            } 
        } while (running);
        sc.close();
    }


    public static double purchaseItems(){
        System.out.println("===========================");
        System.out.println(" - - - - Registrar - - - -");
        System.out.println("===========================");

        double total = 0;
        int itemNumber;
        int quantity;
        boolean inputValid;
        boolean purchasing = true;
        do{
            System.out.printf("%2d. Done Purchasing%n", 0);
            for (int i = 0; i < itemNum; i++){
                System.out.printf("%2d. P%-8.2f - %s%n", i + 1, itemPrices[i], itemNames[i]);
            }

            inputValid = false;
            do{
                System.out.print("Item Number:");
                itemNumber = sc.nextInt();

                if (itemNumber == 0){
                    return total;
                }

                if(itemNumber > 0 && itemNumber <= itemNum){
                    inputValid = true;
                }
                else{
                    System.out.println("Invalid item number, please select from the options above");
                }
            }while (!inputValid);
     
            itemNumber -= 1;
            System.out.printf("Items in stock: %d%n", itemQuantities[itemNumber]);
            System.out.print("Quantity: ");
            quantity = sc.nextInt();

            if (quantity > itemQuantities[itemNumber]){
                System.out.println("You are attempting to purchase more items than there are in stock, please try again");
            }
            else{
                itemQuantities[itemNumber] -= quantity;
                total += quantity * itemPrices[itemNumber];
                System.out.printf("Total: P%.2f%n", total);
            }
        }while (purchasing);

        System.out.printf("Total: P%.2f%n", total);
        System.out.print("Press enter to continue");
        sc.nextLine();
        return total;
    }

    public static boolean checkLogin(String username, String password){
        int numOfPasswords = userPasswords.length;
        for (int i = 0; i < numOfPasswords; i++){
            if (username.equals(validUserNames[i]) && password.equals(userPasswords[i])){
                return true;
            }
        }
        return false;
    }

    public static void deleteItem(){
        if (itemNum == 0){
            System.out.println("No Items in Stock");
            return;
        }
        
        System.out.println("===========================");
        System.out.println(" - - - SELECT A STOCK - - -");
        System.out.println("===========================");

        System.out.println("[0] Back to Main Menu\n");
        System.out.printf("%8s| %-20s | %-10s | %-8s |%n", "Item No.", "Name", "Quantity", "Price");
        for (int i = 0; i < itemNum; i++){
            System.out.printf("%8d.| %-20s | %-10d | P%-8.2f |%n", i + 1, itemNames[i], itemQuantities[i], itemPrices[i]);
        }

        boolean inputValid = false;
        int itemNumber;
        do{
            System.out.printf("Input item number:");
            itemNumber = sc.nextInt();

            if (itemNumber == 0){
                return;
            }
            else if(itemNumber > 0 && itemNumber <= itemNum){
                inputValid = true;
            }
            else{
                System.out.println("Invalid item number, please select from the options above");
            }
        }while (!inputValid);

        if (itemNumber - 1 >= maxNumItems){
            itemNum -= 1;
            return;
        }
        for (int i = itemNumber - 1; i < itemNum; i++){
            itemNames[i] = itemNames[i + 1];
            itemPrices[i] = itemPrices[i + 1];
            itemQuantities[i] = itemQuantities[i + 1];
        }
        itemNum -= 1;
        System.out.println("Deleted Item");
    }

    public static void modifyStock(){
        if (itemNum == 0){
            System.out.println("No Items in Stock");
            return;
        }
        
        System.out.println("===========================");
        System.out.println(" - - - SELECT A STOCK - - -");
        System.out.println("===========================");

        System.out.println("[0] Back to Main Menu\n");
        System.out.printf("%8s| %-20s | %-10s | %-8s |%n", "Item No.", "Name", "Quantity", "Price");
        for (int i = 0; i < itemNum; i++){
            System.out.printf("%8d.| %-20s | %-10d | P%-8.2f |%n", i + 1, itemNames[i], itemQuantities[i], itemPrices[i]);
        }

        boolean inputValid = false;
        int itemNumber;
        do{
            System.out.printf("Input item number:");
            itemNumber = sc.nextInt();

            if (itemNumber == 0){
                return;
            }
            else if(itemNumber > 0 && itemNumber <= itemNum){
                inputValid = true;
            }
            else{
                System.out.println("Invalid item number, please select from the options above");
            }
        }while (!inputValid);

        boolean isEditing = true;
        int editingChoice;
        do{
            System.out.println("[0] Done Editing");
            System.out.println("[1] Name");
            System.out.println("[2] Quantity");
            System.out.println("[3] Price");

            System.out.printf("Choice: ");
            editingChoice = sc.nextInt();
            sc.nextLine();
            double newPrice;
            int newQuantity;
            switch(editingChoice){
                case 0:
                    return;
                case 1:
                    System.out.print("New Name: ");
                    itemNames[itemNumber - 1] = sc.nextLine();
                    System.out.println("Updated Name");
                    break;
                case 2:
                    System.out.print("New Quantity: ");
                    newQuantity = sc.nextInt();
                    if (newQuantity >= 0){
                        itemQuantities[itemNumber - 1] = newQuantity;
                        System.out.println("Updated Quantity");
                    }
                    else{System.out.println("Invalid Quantity");}
                    
                    break;
                case 3:
                    System.out.print("New Price: ");
                    newPrice = sc.nextDouble();
                    if (newPrice > 0){
                        itemPrices[itemNumber - 1] = newPrice;
                        System.out.println("Updated Price");
                    }
                    else{
                        System.out.println("Invalid Price");
                    }
                    break;
                default:
                    System.out.println("Invalid input, please try again.");
            }
        }while(isEditing);
    }

    public static void addStock() {
        if (itemNum >= maxNumItems){
            System.out.println("Error: Maximum Number of Items has been reached!");
            return;
        }
        
        System.out.println("===========================");
        System.out.println(" - - - - ADD STOCK - - - - ");
        System.out.println("===========================");

        System.out.print("Enter Item Name: ");
        String itemName = sc.nextLine();
        System.out.print("Enter Quantity: ");
        int itemQuantity = sc.nextInt();
        System.out.print("Enter Price per Item: ");
        double itemPrice = sc.nextDouble();

        boolean valid = true;

        if (itemName.length() == 0) {
            System.out.println("Item does not have a name.");
            valid = false;
        }
        if (itemQuantity < 0) {
            System.out.println("Invalid quantity.");
            valid = false;
        }
        if (itemPrice < 0) {
            System.out.println("Invalid price.");
            valid = false;
        }


        if (valid) {
            itemNames[itemNum] = itemName;
            itemQuantities[itemNum] = itemQuantity;
            itemPrices[itemNum] = itemPrice;


            itemNum++;
            System.out.println("===========================");
            System.out.println("Item Added Successfully!");
        } else {
            System.out.println("Item not added. Try again.");
        }
    }


    public static void viewStock() { 
        System.out.println("===========================");
        System.out.println("- - - CLUB INVENTORY - - -");
        System.out.println("===========================");
        
        if (itemNum == 0) {
            System.out.println("No items currently in stock.");
        } else {
            for (int i=0; i < itemNum; i++) {
                System.out.printf("* * * * * ITEM #%d * * * * *%n", i + 1);
                System.out.printf("Item Name: %s%nPrice: PHP %.2f%nQuantity: %d%n",
                itemNames[i], itemPrices[i], itemQuantities[i]);
            }
        }
        System.out.print("Press enter to exit");
        sc.nextLine();
    }
}

