package dl.gl1.ObjectsSample;

import android.util.Log;

public class NetworkThread extends Thread {
    public static boolean needSend=true;
    public  static  MyGLSurfaceView myGLSurfaceView;

    public static boolean shutdown=false;


    public void run(){
        shutdown=false;
        EchoClient echoClient=new EchoClient();

        while(!shutdown){
            if(needSend){
                needSend=false;
                echoClient.sendPure(echoClient.encode(MyGLRenderer.mAnglex,MyGLRenderer.mAngley));
                continue;
            }else{
                String r=echoClient.sendEcho("b");
                Log.d("network_receive",r);
                float[] receive=echoClient.decode(r);
                if(receive==null)
                    continue;
                if(!needSend) {
                    MyGLRenderer.mAnglex = receive[0];
                    MyGLRenderer.mAngley = receive[1];
                    myGLSurfaceView.requestRender();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
