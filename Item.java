public class Item{
    String name;
    private boolean stackable;
    private boolean placable;
    private int stackCount;
    private int maxStackCount; 
    private int textureNum;
    //constructor method
    public Item(String n,int sc,int tn,boolean s,boolean p){
        name = n;
        stackCount = sc;
        maxStackCount = 64;
        stackable = s;
        placable = p;
        textureNum = tn;
    }
    //Says wether the item can be placed or not
    public boolean isPlacable(){
        return placable;
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
    //Retures the number of the texture, it corrilates the the placement in the arraylist of textures in the display class
    public int getTextureNum(){
        return textureNum;
    }
    public String toString(){
        return "Type: "+name+", Quantity: "+stackCount+", can it be placed: "+placable+", can it be stacked: "+stackable;
    }
}