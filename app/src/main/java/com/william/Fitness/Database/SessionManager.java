package com.william.Fitness.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    public static final String SESSION_USER = "userLoginSession";
    public static final String KEY_REMEMBER_ME = "rememberMe";

    private static final String IS_LOGIN = "UserIsLogin";


    //User Store Information
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";

    //Remember Me Infomation
    private static final String IS_REMEMBER = "IsRememberMe";

    public static final String REMEMBER_PHONENUMBER = "phoneNumber";
    public static final String REMEMBER_PASSWORD = "password";

    public SessionManager(Context mContext, String sessionName) {
        context = mContext;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    /*
    Store Information Function
    */

    public void createLoginSession(String fullName, String username, String email, String phoneNo, String password, String date, String gender) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONENUMBER, phoneNo);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DATE, date);
        editor.putString(KEY_GENDER, gender);

        editor.commit();

    }

    public HashMap<String, String> getInfomationUser() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));

        return userData;
    }

    public boolean checkUserLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void checkUserLogout() {
        editor.clear();
        editor.commit();
    }

    /*
    Remember Me Function
    */

    public void createRememberMeSession(String phoneNo, String password) {

        editor.putBoolean(IS_REMEMBER, true);

        editor.putString(REMEMBER_PHONENUMBER, phoneNo);
        editor.putString(REMEMBER_PASSWORD, password);

        editor.commit();

    }

    public HashMap<String, String> rememberMeClick() {
        HashMap<String, String> remember = new HashMap<String, String>();

        remember.put(REMEMBER_PHONENUMBER, userSession.getString(REMEMBER_PHONENUMBER, null));
        remember.put(REMEMBER_PASSWORD, userSession.getString(REMEMBER_PASSWORD, null));

        return remember;
    }

    public boolean checkRemember() {
        if (userSession.getBoolean(IS_REMEMBER, false)) {
            return true;
        } else {
            return false;
        }
    }
}
