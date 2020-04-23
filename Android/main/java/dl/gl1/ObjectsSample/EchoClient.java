package dl.gl1.ObjectsSample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
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
        if (socket == null)
            return;
        try {
            buf = msg.getBytes();
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encode(float x, float y) {
        return "a" + " " + x + " " + y;
    }

    public float[] decode(String str) {
        String[] split = str.split(" ");
        if (split.length != 3)
            return null;

        try {
            float[] result = new float[2];
            result[0] = Float.parseFloat(split[1]);
            result[1] = Float.parseFloat(split[2]);
            return result;
        }catch (Exception e){

        }
        return new float[]{0,0};
    }

    public String sendEcho(String msg) {
        if (socket == null)
            return "";

        try {
            buf = msg.getBytes();
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            buf = new byte[32];
            packet = new DatagramPacket(buf, buf.length);
            socket.setSoTimeout(1000);
            socket.receive(packet);
            String received = new String(
                    packet.getData(), 0, packet.getLength());
            return received;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void close() {
        socket.close();
    }
}