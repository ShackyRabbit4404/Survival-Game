public class Item{
    String name;
    private boolean stackable;
    private boolean placable;
    private int stackCount;
    private int maxStackCount; 
    private int textureNum;
    private String type;
    private String weakness;
    //constructor method
    public Item(String n,String t ,int sc,int tn,boolean s,boolean p){
        name = n;
        type = t;
        stackCount = sc;
        maxStackCount = 64;
        stackable = s;
        placable = p;
        textureNum = tn;
    }
    public String getWeakness(){
        return type.substring(type.indexOf("weakness:")+9,type.substring(type.indexOf("weakness:")).indexOf("-")+type.indexOf("weakness:"));
    }
    public int getDamage(String damageType){
        //System.out.println(type.substring(type.indexOf(damageType)+1+damageType.length(),type.substring(type.indexOf(damageType)).indexOf("-"))+typ.index);
        return Integer.parseInt(type.substring(type.indexOf(damageType)+1+damageType.length(),type.substring(type.indexOf(damageType)).indexOf("-")+type.indexOf(damageType)));
    }
    public Item getClone(){
        return new Item(name,type,stackCount,textureNum,stackable,placable);
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