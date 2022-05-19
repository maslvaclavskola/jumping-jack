import java.net.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@FunctionalInterface
interface GameCommandListener{
    public void onGameCommand(GameCommand data);
}
public class Multicast extends Thread {
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
    private MulticastSocket socket2 = null;
    private byte[] buf2 = new byte[256];
    private List<GameCommandListener> listeners = new ArrayList<>();
    public Multicast(){
        start();
    }
    public void addGameCommandListener(GameCommandListener l){
        listeners.add(l);
    }
    @Override
    public void run() {
        try{
            socket2 = new MulticastSocket(1234);
            InetAddress group = InetAddress.getByName("239.1.1.1");
            socket2.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf2, buf2.length);
                socket2.receive(packet);
                
                String received = new String(packet.getData(), 0, packet.getLength());
                for(GameCommandListener ml: listeners){
                    ml.onGameCommand(new GameCommand(received));
                }
                if ("end".equals(received)) {
                    break;
                }
            }
            socket2.leaveGroup(group);
            socket2.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void send(GameCommand cmd){
        try{
            socket = new DatagramSocket();
            group = InetAddress.getByName("239.1.1.1");
            buf = cmd.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 1235);
            socket.send(packet);
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
