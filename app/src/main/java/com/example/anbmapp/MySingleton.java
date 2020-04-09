package com.example.anbmapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
//all the method to use volley
public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingleton(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }
//method that return arrayrequestque
    private RequestQueue getRequestQueue() {
        if(requestQueue==null)
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        return requestQueue;
    }
    //mehtod that return the object of the class
    public static synchronized  MySingleton getInstance(Context context){
        if(mInstance==null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }
    //method for adding each of the request in their request que
     public <T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
     }
}
