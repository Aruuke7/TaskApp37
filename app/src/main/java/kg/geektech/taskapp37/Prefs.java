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

    public String getSavedImage(){
        return preferences.getString("image",null);
    }

    public  void saveEmail(String email){
        preferences.edit().putString("email", email).apply();
    }
    public String getSavedEmail(){ return  preferences.getString("email",null);}

    public void savePhone(String phone){
        preferences.edit().putString("phone",phone).apply();
    }

    public String getSavedPnone(){
        return preferences.getString("phone",null);
    }

    public void saveGender(String gender){
        preferences.edit().putString("gender",gender).apply();
    }

    public String getSavedGender(){
        return preferences.getString("gender",null);
    }

    public void saveDate(String date){
        preferences.edit().putString("date",date).apply();
    }

    public String getSavedDate(){
        return  preferences.getString("date",null);
    }

    public void deleteUserImage(Uri image) {
        preferences.edit().remove(String.valueOf(image)).apply();
    }
}
