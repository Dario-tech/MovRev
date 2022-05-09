package com.example.yourmovieopinion;
import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
public class ColaPeticionesHTTP extends Application {
    private static ColaPeticionesHTTP sInstance;
    private RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
    }
    public synchronized static ColaPeticionesHTTP getInstance() {
        return sInstance;
    }
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}