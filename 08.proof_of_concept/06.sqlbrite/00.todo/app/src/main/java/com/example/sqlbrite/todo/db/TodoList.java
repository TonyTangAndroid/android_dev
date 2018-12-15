package com.example.sqlbrite.todo.db;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

/**
 * Created by Juan on 12/26/2015.
 */
@AutoParcel
public abstract class TodoList {
    public static final String TABLE = "todo_list";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String ARCHIVED = "archived";

    public abstract long id();
    public abstract String name();
    public abstract boolean archived();

    public static Func1<Cursor, List<TodoList>> MAP = (cursor)->{
        try{
            List<TodoList> values = new ArrayList<>(cursor.getCount());

            while(cursor.moveToNext()){
                long id = Db.getLong( cursor, ID);
                String name = Db.getString(cursor, NAME);
                boolean archived = Db.getBoolean( cursor, ARCHIVED );
                values.add( new AutoParcel_TodoList(id, name, archived));
            }

            return values;

        }finally{
            cursor.close();
        }
    };


    public static  final class Builder{
        private final ContentValues values = new ContentValues();

        public Builder id(long id){
            values.put(ID, id );
            return this;
        }

        public Builder name(String name){
            values.put( NAME, name );
            return this;
        }

        public Builder archived( boolean archived ){
            values.put(ARCHIVED, archived );
            return this;
        }

        public ContentValues build(){
            return values;
        }
    }
}
