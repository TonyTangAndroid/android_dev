package com.flexnroses.android.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

public class MyService extends Service
{
	public static final String TAG = "ServiceWorkshop";
	private BgThread tweet;
	private boolean _running;

	@Override
	public void onCreate()
	{

		// TODO Auto-generated method stub
		super.onCreate();
		
		Log.d(TAG, "onCreate" );
		this._running = true;
		
		//3 adding new bgThread
		tweet = new BgThread( this );
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Log.d(TAG, "onDestroy" );
		
		//3 making sure Thread is void
		this._running = false;
		tweet.cancel(true );
	}

	@Override
	public void onLowMemory()
	{
		Log.d(TAG, "onLowMemory" );

		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{

		Log.d(TAG, "onStartCommand " + intent.toString() + " " + Integer.toString(startId) + " "  + tweet.getStatus() + " canceled?:" + tweet.isCancelled() );
		
		//2. get attributes
		if( intent.hasExtra("weather") )
			Log.d(TAG, "weather " + intent.getStringExtra("weather") );

		if( intent.hasExtra("creator") )
			Log.d(TAG, "creator "+ intent.getStringExtra( "creator") );
		
		//3 get a thread doing some work for us
		Status status = tweet.getStatus();
		
		//is asynctask running? then don't attempt to execute twice..
		if( status.equals(Status.PENDING) )
		{	
			tweet.execute( 0, 10 );
		}
		else
		if( status.equals(Status.FINISHED) )
		{
			if( tweet.isCancelled() == false )
				tweet.cancel(true);
			
			//use AsyncTask only once.. therefore make a new one.
			tweet = new BgThread( this );
			tweet.execute( 0, 10 );
		}
		
		// TODO Auto-generated method stub
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0)
	{

		// TODO Auto-generated method stub
		return null;
	}

	public void setProgress( Integer progress )
	{
		Intent intent = new Intent( MyService.TAG );
		intent.putExtra( "action", "progress" );
		intent.putExtra( "percent", progress );
		
		Message m = new Message( Parcel.obtain() );
		m.setAction("progress");
		m.setPercent( progress );
		
		intent.putExtra("myparcel", m );
	
		
		this.sendBroadcast(intent);
	}
	
	public boolean is_running()
	{
		return _running;
	}
}