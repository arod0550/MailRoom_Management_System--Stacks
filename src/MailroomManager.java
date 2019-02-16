import java.util.*;
import java.util.EmptyStackException;

/**
 * this is the main class of the project. This is a Mailroom Manager Computer System that
 * allows for the management of packages as they come in for arrival and leave for departure.
 * The packages are managed in a Stack Data Structure. Push and Pop. There are stacks for each group of letters in the alphabet.
 * This ensures the packages are sorted as they come in already based on the first letter of the recipient. Clever Huh?
 */
public class MailroomManager {

    public static void main(String[] args) {
        //Data fields
        String section;
        int day = 0;
        int position = 0;
        int position1 = 0;
        int pos = 0;

        //Data Structure
        PackageStack floorStack = new PackageStack(Integer.MAX_VALUE);
        PackageStack tempStack = new PackageStack(Integer.MAX_VALUE);
        PackageStack stackAtoG = new PackageStack();
        PackageStack stackHtoJ = new PackageStack();
        PackageStack stackKtoM = new PackageStack();
        PackageStack stackNtoR = new PackageStack();
        PackageStack stackStoZ = new PackageStack();


        //array of stacks
        PackageStack[] packageStacksArray = new PackageStack[]{stackAtoG, stackHtoJ, stackKtoM, stackNtoR, stackStoZ};

        //create a scanner
        Scanner input = new Scanner(System.in);

        //Welcoming message
        System.out.println("Welcome to the Irving Mailroom Manager. You can try to make it better, but the odds ");
        System.out.println("are stacked against you. It is day 0.");
        System.out.println();

        mainMenu();

        while (true) {
            section = input.next();

            switch (section.toLowerCase()) {
                case "d":
                    //Deliver a package
                    try {
                        //user input for package
                        System.out.println("Please enter the recipient name: ");
                        input.nextLine();//flushing
                        String recipientName = input.nextLine();
                        System.out.println("Please enter the weight (lbs): ");
                        double weight = input.nextDouble();

                        //create a package with the parameters passed
                        Package packageCreated = new Package(recipientName, day, weight);

                        //determine the target stack in the array
                        char firstLetter = packageCreated.getRecipient().toLowerCase().charAt(0);
                        if ((firstLetter >= 'a' && firstLetter <= 'g')) {
                            position = 0;
                        } else if ((firstLetter >= 'h' && firstLetter <= 'j')) {
                            position = 1;
                        } else if ((firstLetter >= 'k' && firstLetter <= 'm')) {
                            position = 2;
                        } else if ((firstLetter >= 'n' && firstLetter <= 'r')) {
                            position = 3;
                        } else if ((firstLetter >= 's' && firstLetter <= 'z')) {
                            position = 4;
                        } else {
                        }

                        //check if stack at position is not full and push the package if its not. if it is full, go to the next stack and see if you can dump it there.
                        //otherwise go to the floor stack and dump it there.
                        boolean found = false;
                        for (int i = 0; i < 5 && !found; i++) {
                            if (!(packageStacksArray[(position + i) % 5].isFull())) {
                                packageStacksArray[(position + i) % 5].push(packageCreated);
                                found = true;
                            }
                        }
                        if (!found) {
                            floorStack.push(packageCreated);
                        }

                        //let user know
                        System.out.println("A " + packageCreated.getWeight() + " lb package is awaiting pickup by " + packageCreated.getRecipient() + ".");

                    } catch (FullStackException ex) {
                        System.out.println("Your package was moved to the nearest stacked as the one it was supposed to go to was full. " +
                                "Check the floor stack it might be there if the rest were full.");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("That is not a valid input. Try again.  ");
                    } catch (InputMismatchException ex) {
                        System.out.println("invalid input. Try Again.");
                    }
                    System.out.println();
                    mainMenu();
                    break;

                case "g":
                    //Get someone's package
                    try {
                        //user input for package
                        System.out.println("Please enter the recipient name: ");
                        input.nextLine();//flushing
                        String recipientName1 = input.nextLine();

                        //create package with the recipient name given
                        Package packageOnlyName = new Package(recipientName1);

                        //determine the target stack in the array
                        char firstLetter1 = packageOnlyName.getRecipient().toLowerCase().charAt(0);
                        if ((firstLetter1 >= 'a' && firstLetter1 <= 'g')) {
                            position1 = 0;
                        } else if ((firstLetter1 >= 'h' && firstLetter1 <= 'j')) {
                            position1 = 1;
                        } else if ((firstLetter1 >= 'k' && firstLetter1 <= 'm')) {
                            position1 = 2;
                        } else if ((firstLetter1 >= 'n' && firstLetter1 <= 'r')) {
                            position1 = 3;
                        } else if ((firstLetter1 >= 's' && firstLetter1 <= 'z')) {
                            position1 = 4;
                        } else {
                        }

                        if (packageStacksArray[position1].isEmpty()) {
                            throw new EmptyStackException();
                        }

                        //move packages that are not equal to the floor, leave the ones that are there
                        int numPackMovedFloor = 0;
                        while (!packageStacksArray[position1].isEmpty()) {
                            Package packagedPeeked = packageStacksArray[position1].peek();
                            if (!packagedPeeked.equalNames(packageOnlyName)) {
                                floorStack.push(packageStacksArray[position1].pop());
                                numPackMovedFloor++;
                            } else {
                                tempStack.push(packageStacksArray[position1].pop());
                            }
                        }
                        while (!tempStack.isEmpty()) {
                            packageStacksArray[position1].push(tempStack.pop());
                        }
                        System.out.println("Move " + numPackMovedFloor + " packages from " + (position1 + 1) + " to floor.");

                        System.out.println();
                        //Print the stacks
                        System.out.println("Current Packages: (From Top to Bottom)");
                        System.out.println("--------------------------------");
                        System.out.println("Stack 1 (A-G): |" + stackAtoG.toString());
                        System.out.println("Stack 2 (H-J): |" + stackHtoJ.toString());
                        System.out.println("Stack 3 (K-M): |" + stackKtoM.toString());
                        System.out.println("Stack 4 (N-R): |" + stackNtoR.toString());
                        System.out.println("Stack 5 (S-Z): |" + stackStoZ.toString());
                        System.out.println("Floor: |" + floorStack.toString());
                        System.out.println();

                        int given = 0;
                        while (!packageStacksArray[position1].isEmpty()) {
                            Package p = packageStacksArray[position1].peek();
                            if (p.getArrivalDate() == 0 || p.getArrivalDate() < day) {
                                packageStacksArray[position1].pop();
                                given++;
                            } else {
                                floorStack.push(packageStacksArray[position1].pop());
                                numPackMovedFloor++;
                            }
                        }

                        while (!floorStack.isEmpty()) {
                            packageStacksArray[position1].push(floorStack.pop());
                        }

                        System.out.println(given + " package(s) were given to " + packageOnlyName.getRecipient() + ".");
                        System.out.println(numPackMovedFloor + " package(s) were returned to " + (position + 1) + " from the floor.");

                        //Print the stacks
                        System.out.println("Current Packages: (From Top to Bottom)");
                        System.out.println("--------------------------------");
                        System.out.println("Stack 1 (A-G): |" + stackAtoG.toString());
                        System.out.println("Stack 2 (H-J): |" + stackHtoJ.toString());
                        System.out.println("Stack 3 (K-M): |" + stackKtoM.toString());
                        System.out.println("Stack 4 (N-R): |" + stackNtoR.toString());
                        System.out.println("Stack 5 (S-Z): |" + stackStoZ.toString());
                        System.out.println("Floor: |" + floorStack.toString());
                        System.out.println();

                        if (numPackMovedFloor == 0) {
                            System.out.println("a package with the name of: " + packageOnlyName.getRecipient() + " wasn't found in it's appropriate stack.");
                            System.out.println();
                            mainMenu();
                            break;
                        }

                    } catch (FullStackException ex) {
                        System.out.println("Your package was moved to the nearest stacked as the one it was supposed to go to was full. " +
                                "Check the floor stack it might be there if the rest were full.");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("That is not a valid input. Try again.  ");
                    } catch (EmptyStackException ex) {
                        System.out.println("Stacks are empty. Silly Rabbit! Tricks are for kids. Try Again.");
                    } catch (InputMismatchException ex) {
                        System.out.println("invalid input. Try Again.");
                    }
                    System.out.println();
                    mainMenu();
                    break;
                case "t":
                    try {
                        //Make It Tomorrow
                        day++;
                        System.out.println("It is now day " + day);

                        //remove packages older than 5 days
                        while (day >= 5) {
                            for (int i = 0; i < 5; i++) {
                                while (!packageStacksArray[i].isEmpty()) {
                                    if (packageStacksArray[i].peek().getArrivalDate() == (day - 5)) {
                                        packageStacksArray[i].pop();
                                    } else {
                                        tempStack.push(packageStacksArray[i].pop());
                                    }
                                }
                                while (!tempStack.isEmpty()) {
                                    packageStacksArray[i].push(tempStack.pop());
                                }
                            }
                            break;
                        }

                    } catch (FullStackException ex) {
                        System.out.println("Your package was moved to the nearest stacked as the one it was supposed to go to was full. " +
                                "Check the floor stack it might be there if the rest were full.");
                    }
                    System.out.println();
                    mainMenu();
                    break;

                case "p":
                    //Print the stacks
                    System.out.println("Current Packages: (From Top to Bottom)");
                    System.out.println("--------------------------------");
                    System.out.println("Stack 1 (A-G): |" + stackAtoG.toString());
                    System.out.println("Stack 2 (H-J): |" + stackHtoJ.toString());
                    System.out.println("Stack 3 (K-M): |" + stackKtoM.toString());
                    System.out.println("Stack 4 (N-R): |" + stackNtoR.toString());
                    System.out.println("Stack 5 (S-Z): |" + stackStoZ.toString());
                    System.out.println("Floor: |" + floorStack.toString());
                    //go back to main menu
                    System.out.println();
                    mainMenu();
                    break;
                case "m":
                    //Move a package from one stack to another
                    try {
                        //user input
                        System.out.println("Please enter the source stack (enter 0 for floor): ");
                        int source = input.nextInt();
                        System.out.println("Please enter the destination stack(enter 0 for floor):");
                        int destination = input.nextInt();

                        //cases for the floor stack
                        if (source == 0 && destination == 0) {
                            System.out.println("Why are you trying to move something from the floor to the floor?!!?? Try again.");
                        } else if (source == 0 && destination != 0) {
                            packageStacksArray[destination - 1].push(floorStack.pop());
                            System.out.println("Move was successful!!! package went from stack " + source + " to stack " + destination);
                        } else if (source != 0 && destination == 0) {
                            floorStack.push(packageStacksArray[source - 1].pop());
                            System.out.println("Move was successful!!! package went from stack " + source + " to stack " + destination);
                        }

                        //cases where the floor stack isn't included
                        while (source != 0 && destination != 0) {
                            if (packageStacksArray[source - 1].isEmpty() && packageStacksArray[destination - 1].isEmpty()) {
                                System.out.println("Both stacks are empty. Most importantly, the source stack is empty. Action wasn't executed. Try Again.");
                            } else if (packageStacksArray[source - 1].isEmpty()) {
                                System.out.println("source stack is empty. Action wasn't executed. Try Again.");
                            } else if (packageStacksArray[destination - 1].isFull()) {
                                System.out.println("destination stack is full. Action wasn't executed. Try Again.");
                            } else if (!packageStacksArray[source - 1].isEmpty() && !packageStacksArray[destination - 1].isFull()) {
                                packageStacksArray[destination - 1].push(packageStacksArray[source - 1].pop());
                                System.out.println("Move was successful!!! package went from stack " + source + " to stack " + destination);
                            }
                            break;
                        }
                    } catch (FullStackException ex) {
                        System.out.println("Your package was moved to the nearest stacked as the one it was supposed to go to was full. " +
                                "Check the floor stack it might be there if the rest were full.");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("That is not a valid input. Try again.  ");
                    } catch (EmptyStackException ex) {
                        System.out.println("one of the list was empty. Silly Rabbit, Try again.");
                    } catch (InputMismatchException ex) {
                        System.out.println("invalid input. Try Again.");
                    } catch (ArrayIndexOutOfBoundsException ex){
                        System.out.println("invalid input. Try Again.");
                    }
                    System.out.println();
                    mainMenu();
                    break;
                case "f":
                    //Find packages in the wrong stack and move to floor
                    try {
                        for (int i = 0; i < 5; i++) {
                            while (packageStacksArray[i].isEmpty() && i < 4) {
                                i++;
                            }
                            while (!packageStacksArray[i].isEmpty()) {
                                Package Peek = packageStacksArray[i].peek();
                                char firstLetter3 = Peek.getRecipient().toLowerCase().charAt(0);

                                //determine the target stack in the array
                                if ((firstLetter3 >= 'a' && firstLetter3 <= 'g')) {
                                    pos = 0;
                                } else if ((firstLetter3 >= 'h' && firstLetter3 <= 'j')) {
                                    pos = 1;
                                } else if ((firstLetter3 >= 'k' && firstLetter3 <= 'm')) {
                                    pos = 2;
                                } else if ((firstLetter3 >= 'n' && firstLetter3 <= 'r')) {
                                    pos = 3;
                                } else if ((firstLetter3 >= 's' && firstLetter3 <= 'z')) {
                                    pos = 4;
                                } else {
                                }

                                if (i == pos) {
                                    tempStack.push(packageStacksArray[i].pop());
                                } else {
                                    floorStack.push(packageStacksArray[i].pop());
                                }
                            }
                            while (!tempStack.isEmpty()) {
                                packageStacksArray[i].push(tempStack.pop());
                            }
                        }
                        System.out.println("Misplaced packages moved to floor.(if all the stacks were empty nothing happened.)");
                    } catch (FullStackException ex) {
                        System.out.println("Check the floor stack.");
                    } catch (EmptyStackException ex) {
                        System.out.println("All list were empty. Silly Rabbit, Try again.");
                    }
                    System.out.println();
                    mainMenu();
                    break;
                case "l":
                    try {
                        //List all packages awaiting a user
                        //user input
                        System.out.println("Please enter the recipient name: ");
                        input.nextLine();//flushing
                        String recipientsName = input.nextLine();

                        //create package with the recipient name given
                        Package packageWithName = new Package(recipientsName);

                        int numOfPack = 0;
                        for (int i = 0; i < 5; i++) {
                            while (packageStacksArray[i].isEmpty() && i < 4) {
                                i++;
                            }
                            while (!packageStacksArray[i].isEmpty()) {
                                Package packagedPeeked3 = packageStacksArray[i].peek();
                                if (!packagedPeeked3.equalNames(packageWithName)) {
                                    tempStack.push(packageStacksArray[i].pop());
                                } else {
                                    numOfPack++;
                                    System.out.println("Package "+ numOfPack +" is in stack "+ (i+1) +", it was delivered on day "
                                            +packagedPeeked3.getArrivalDate()+", and weighs "+packagedPeeked3.getWeight()+ "lbs.");
                                    tempStack.push(packageStacksArray[i].pop());
                                }
                            }
                            while (!tempStack.isEmpty()) {
                                packageStacksArray[i].push(tempStack.pop());
                            }
                        }
                        System.out.println(""+recipientsName+ " has "+ numOfPack +" packages total.");

                    } catch (FullStackException ex) {
                        System.out.println("Your package was moved to the nearest stacked as the one it was supposed to go to was full. " +
                                "Check the floor stack it might be there if the rest were full.");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("That is not a valid input. Try again.  ");
                    } catch (EmptyStackException ex) {
                        System.out.println("one of the list was empty. Silly Rabbit, Try again.");
                    } catch (InputMismatchException ex) {
                        System.out.println("invalid input. Try Again.");
                    }
                    System.out.println();
                    mainMenu();
                    break;
                case "e":
                    //Empty the floor.
                    floorStack.emptyOutStack();
                    System.out.println("The floor has been emptied. Mr. Trash Can is no longer hungry. ");
                    System.out.println();
                    mainMenu();
                    break;
                case "q":
                    // Section Q (Quit)
                    System.out.println("You are now leaving the Irving Mailroom Manager Simulator.");
                    System.out.println("We congratulate you on your decision to do something more productive with your time.");
                    System.exit(0);
                    break;
                default:
                    //Display wrong status, No Section was matched
                    System.out.println("Error: invalid status. Try again");
                    mainMenu();
            }
        }
    }

    /**
     * Method containing Menu.
     */
    public static void mainMenu() {
        //Menu
        System.out.println("Menu:");
        System.out.println("    D) Deliver a package");
        System.out.println("    G) Get someone's package");
        System.out.println("    T) Make it tomorrow");
        System.out.println("    P) Print the stacks");
        System.out.println("    M) Move a package from one stack to another");
        System.out.println("    F) Find packages in the wrong stack and move to floor");
        System.out.println("    L) List all packages awaiting a user");
        System.out.println("    E) Empty the floor.");
        System.out.println("    Q) Quit Irving Mailroom Manager and move on to real life.");
        System.out.println();

        //prompt user to go to section desired
        System.out.println("please select an option: ");
    }
}
