public class Item{
    String name;
    private boolean stackable;
    private int stackCount;
    private int maxStackCount;
    private int textureNum;
    //constructor method
    public Item(String n,int sc){
        name = n;
        stackCount = sc;
        maxStackCount = 64;
        stackable = true;
        textureNum = 0;
    }
    //returns the name of the object
    public String getName(){
        return name;
    }
    //returns the current amount of items in the stack
    public int getCount(){
        return stackCount;
    }
    //returns wether the item is stackable
    public boolean isStackable(){
        return stackable;
    }
    //return how many more items can be stacked
    public int canStackMore(){
        return maxStackCount-stackCount;
    }
    //adds to the stack
    public void increaseStack(int s){
        stackCount += s;
    }
    //removes from the stack
    public void decreaseStack(int s){
        stackCount -= s;
    }   
}