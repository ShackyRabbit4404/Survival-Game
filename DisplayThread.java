class DisplayThread implements Runnable{
    private Display frame;
    public DisplayThread(Display f){
        frame = f;
    }
    public void run(){
        frame.draw();
    }
}