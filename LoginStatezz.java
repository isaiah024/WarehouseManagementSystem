package warehouseproject;

import java.util.Scanner;

public class LoginState {

    public static void main(String[] args) {
        int num;

        //Login State
        System.out.print("1. Client Menu\n");
        System.out.print("2. Clerk Menu\n");
        System.out.print("3. Manager Menu\n");
        System.out.print("Select Menu: ");
        Scanner scan = new Scanner(System.in);
        num = scan.nextInt();
        
        switch(num) {
        case 1: 
            System.out.print("You select Client Menu");
            //need to connect to Client Menu
            break;
        case 2:
            System.out.print("You select Clerk Menu");
            //need to connect to Clerk Menu
            break;
        case 3:
            System.out.print("You select Manager Menu");
            //need to connect to Manager Menu
            break;
        default:
                System.out.println("Wrong number.");
                break;
        }
        
        }
}