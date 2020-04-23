import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class EchoServer extends Thread {

    public final int maxBuffer=32;
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[maxBuffer];

    private byte[] store = new byte[maxBuffer];
    public EchoServer() {
	try {
	    socket = new DatagramSocket(4445);
	} catch (SocketException e) {
	    e.printStackTrace();
	}
    }
    
    public void clearBuffer(){
    	for (int i=0;i<store.length;i++)
		store[i]=20;
    }

    public void storeData() {
	clearBuffer();
	for(int i=0;i<store.length;i++)
	    store[i]=buf[i];
    }
    public void readData() {
	buf=new byte[store.length];
	for(int i=0;i<store.length;i++)
	    buf[i]=store[i];
    }

    public void run() {
	running = true;

	while (running) {
	    DatagramPacket packet = new DatagramPacket(buf, buf.length);
	    try {
		socket.receive(packet);

		if(buf[0]=='a') {
		    storeData();
		}
		else if(buf[0]=='b') {
        		InetAddress address = packet.getAddress();
        		int port = packet.getPort();
        		readData();
        		packet = new DatagramPacket(buf, buf.length, address, port);
        		//String received = new String(packet.getData(), 0, packet.getLength());
        		socket.send(packet);
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	socket.close();
    }
    

    public static void main(String[] args) {
	new EchoServer().start();	
    }
}
