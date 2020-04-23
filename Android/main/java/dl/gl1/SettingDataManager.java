package dl.gl1;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingDataManager {
    private Context context;

    public void setKey(String key,int value){
        SharedPreferences.Editor editor=context.getSharedPreferences("settings",context.MODE_PRIVATE).edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public int getKey(String key,int initialValue){
        SharedPreferences reader=context.getSharedPreferences("settings",context.MODE_PRIVATE);
        int result=reader.getInt(key,initialValue);
        return result;
    }
    public int getAlphaColor(){
        return getKey("alpha",255);
    }
    public int getRedColor(){
        return getKey("red",255);
    }
    public int getGreenColor(){
        return getKey("green",255);
    }
    public int getBlueColor(){
        return getKey("blue",255);
    }
}
