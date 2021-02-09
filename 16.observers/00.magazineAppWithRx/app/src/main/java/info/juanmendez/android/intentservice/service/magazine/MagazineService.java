package info.juanmendez.android.intentservice.service.magazine;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import java.util.Date;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/** Created by Juan on 7/22/2015. */
public class MagazineService {
  public static RetroService getService(String url) {
    Gson gson =
        new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .create();

    RestAdapter restAdapter =
        new RestAdapter.Builder()
            .setEndpoint(url)
            .setErrorHandler(new RetrofitErrorHandler())
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setConverter(new GsonConverter(gson))
            .build();

    return restAdapter.create(RetroService.class);
  }

}
