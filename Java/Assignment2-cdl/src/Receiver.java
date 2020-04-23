import udp.EchoClient;

import udp.EchoClient;

public class Receiver extends Thread{
    public static boolean send=false;
    public void run() {
	EchoClient echoClient=new EchoClient();
	while(true) {
	 
	try {   
	    Thread.sleep(50);
	    String receive=echoClient.sendEcho("b");
	    if(send) {
		echoClient.sendPure("a"+" "+(-MainWindow.rotationy)+" "+MainWindow.rotationx);
		send=false;
		continue;
	    }
	    System.out.println(receive);
	    String[] result=receive.split(" ");
	    if(result.length==3) {
		MainWindow.rotationy=-Float.parseFloat(result[1]);
		MainWindow.rotationx=Float.parseFloat(result[2]);
	    }
	} catch (Exception e) {
	    System.err.println("interval");
	}}
    }
    

}
