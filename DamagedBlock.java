public class DamagedBlock{
    private Item type;
    private double sturdyness;
    private double health;
    private int[] cords;
    public DamagedBlock(int blockNum,int X, int Y){
        cords = new int[]{X,Y};
        if(blockNum == 1 || blockNum == 2){
            sturdyness = 3;
            type = new Item("Dirt","building-dig:1-mine:1-chop:1-weakness:dig-",1,1,true,true);
        }
        else if(blockNum == 3){
            sturdyness = 10;
            type = new Item("Stone","building-crafting-dig:1-mine:1-chop:1-weakness:mine-",1,2,true,true);
        }
        health = sturdyness;
    }
    public double getHealthPercent(){
        return health/sturdyness;
    }
    public Item getItem(){
        return type;
    }
    public int getX(){
        return cords[0];
    }
    public int getY(){
        return cords[1];
    }
    public boolean dealDamage(double damage){
        health -= damage;
        if(health <= 0){
            health = 0;
            return true;
        }
        return false;
    }
}