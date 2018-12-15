package com.example.sqlbrite.todo;

import android.app.Application;
import android.content.Context;
import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by Juan on 12/26/2015.
 */
public class TodoApp extends Application {
    private ObjectGraph objectGraph;

    @Override public void onCreate(){
        super.onCreate();

        if( BuildConfig.DEBUG ){
            Timber.plant( new Timber.DebugTree() );
        }

        objectGraph = ObjectGraph.create( new TodoModule(this) );
    }

    public static ObjectGraph objectGraph( Context context ){
        return ((TodoApp) context.getApplicationContext()).objectGraph;
    }
}
