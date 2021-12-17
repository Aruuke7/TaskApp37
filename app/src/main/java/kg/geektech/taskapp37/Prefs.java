package kg.geektech.taskapp37;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import kg.geektech.taskapp37.databinding.FragmentProfileBinding;

public class Prefs {

    private SharedPreferences preferences;
    private FragmentProfileBinding binding;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }

    public void saveBoardState(){
        preferences.edit().putBoolean("isShown",true).apply();
    }

    public boolean isBoardShown(){
        return preferences.getBoolean("isShown",false);
    }

    public void saveText(String name){
        preferences.edit().putString("text",name).apply();
    }

    public  String isTextSave(){
        return preferences.getString("text",null);
    }


    public void saveImage(Uri image){
        preferences.edit().putString("image",image.toString()).apply();
    }

    public String isImageSave(){
        return preferences.getString("image",null);
    }
}
