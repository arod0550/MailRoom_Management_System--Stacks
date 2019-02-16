import java.util.*;
import java.util.EmptyStackException;

/**
 * This class represents the stacks of packages that are going to be in the mailroom. This also represents the data structure which is stacks.
 */
public class PackageStack {
    //Data fields
    private final int CAPACITY = 7;
    private int maxSize;
    ArrayList<Package> packageStack = new ArrayList<Package>(CAPACITY);
    private int top;

    //Constructor
    /**
     * Empty constructor that initializes the stack of packages to an empty stack.
     */
    public PackageStack(){
        ArrayList<Package> packageStack = new ArrayList<Package>();
        maxSize = CAPACITY;
        top = -1; //stack is empty
    }
    public PackageStack(int maxSize){
        ArrayList<Package> packageStack = new ArrayList<Package>();
        this.maxSize = maxSize;
        top = -1; //stack is empty
    }

    //Getters and setters
    /**
     * Gets the index value of top
     * @return
     *  the index value of package at top of the stack.
     */
    public int getTop(){
        return top;
    }

    /**
     * Sets the index value of the top
     * @param top
     *  index value of package at top of stacks.
     */
    public void setTop(int top){
        this.top = top;
    }

    //Methods

    /**
     * prints the stack of packages with their respective attributes.
     * @return
     *      A string representing a list of packages in the stack.
     */
    @Override
    public String toString(){
        String packageStacks = "";

        //if the stack is empty print it out
        if(top == -1)
            return packageStacks = "empty.";

        for(int i = top; i >= 0; i--)
            packageStacks += packageStack.get(i).toString() + "";
        return  packageStacks;
    }

    /**
     * Pushes x onto the top of the backing data structure.
     * @param x
     *  object of package representing package being added to the stack.
     * @throws FullStackException
     *   if the stack is full.
     */
    public void push(Package x) throws FullStackException{
        if (top == CAPACITY-1){
            throw new FullStackException();
        }
        top++;
        packageStack.add(x);
    }

    /**
     * Removes the topmost package from the stack and returns it.
     * @return
     *  an object of package representing the package at the top of stack.
     * @throws EmptyStackException
     *  if stack was empty.
     */
    public Package pop(){
        Package result;

        if (top == -1){ //if stack is empty
            throw new EmptyStackException();
        }

        result = packageStack.remove(top);
        top--;
        return result;
    }

    /**
     * Returns the topmost Package from the stack without removing it.
     * The stack should be unchanged as a result of this method.
     * @return
     *  the package at the top without removing it.
     * @throws EmptyStackException
     *  if stack is empty.
     */
    public Package peek(){
        Package result;

        if(top == -1){ //if stack is empty
            throw new EmptyStackException();
        }

        result = packageStack.get(top);
        return result;
    }

    /**
     * Returns true if the stack is empty, false otherwise.
     */
    public boolean isEmpty(){
        return (top == -1);
    }

    /**
     * returns true if the stack is full and false otherwise.
     * @return
     */
    public boolean isFull() {
       return (packageStack.size() == 7);
    }

    /**
     * Removes everything from the stack.
     */
    public void emptyOutStack(){
        //remove evrything from stack
        packageStack.clear();
        //set the top to -1
        top = -1;
    }
}
