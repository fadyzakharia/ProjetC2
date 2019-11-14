package com.example.fady.goldenbank;


import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Un singleton qui encapsule une instance de RequestQueue a partager parmi toutes les classes
 * qui souhaitent emettre des requetes HTTP
 */
public class SimpleRequestQueueFactory {
    //Attributes
    private static SimpleRequestQueueFactory self = null;
    private RequestQueue queue = null;
    private Context context = null;
    //Constructors
    private SimpleRequestQueueFactory(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
    }
    //Getters and Setters
    public static SimpleRequestQueueFactory getInstance(Context context) throws Exception {
        if (context == null) {
            throw new Exception("context cannot be null");
        }
        if (self == null) {
            self = new SimpleRequestQueueFactory(context);
        }
        return self;
    }
    public RequestQueue getQueueInstance() {
        return this.queue;
    }
}
