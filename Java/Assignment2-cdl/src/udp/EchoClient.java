package udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class EchoClient {
    private DatagramSocket socket;
    private InetAddress address;
 
    private byte[] buf;
 
    public EchoClient() {
        try {
            socket = new DatagramSocket();
	    address = InetAddress.getByName("0cdl.com");
	} catch (UnknownHostException | SocketException e) {
	    e.printStackTrace();
	}
    }
    
    public void sendPure(String msg) {
        buf = msg.getBytes();
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, address, 4445);
        try {
	    socket.send(packet);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
 
    public String sendEcho(String msg) {
        buf = msg.getBytes();
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, address, 4445);
        try {
	    socket.send(packet);
	    buf=new byte[256];
	    packet = new DatagramPacket(buf, buf.length);
	    socket.setSoTimeout(1000);
	    socket.receive(packet);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
        String received = new String(
          packet.getData(), 0, packet.getLength());
        return received;
    }
 
    public void close() {
        socket.close();
    }
}