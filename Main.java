public class Main{
    public static void main(String[] args){
        Multicast m = new Multicast();
        m.addGameCommandListener((e)->{
            System.out.println(e);
        });
        m.send(new GameCommand("TEST1"));
        m.send(new GameCommand("end"));
        
    }
}