package info.juanmendez.android.intentservice.service.magazine;

import info.juanmendez.android.intentservice.model.pojo.Magazine;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.http.GET;

/** Created by Juan on 7/22/2015. */
public interface RetroService {
  @GET("/development/android/latest.json")
  void getLatestIssue(Callback<Magazine> cb);

  @GET("/development/android/latest.json")
  Magazine getLatestIssue();

  @GET("/development/android/list.json")
  void getIssues(Callback<ArrayList<Magazine>> cb);

  @GET("/development/android/list.json")
  ArrayList<Magazine> getIssues();
}
