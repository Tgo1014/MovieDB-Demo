package tgo1014.moviedb_demo.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tgo1014.moviedb_demo.BuildConfig;

public class RestClient {

    private static RestClient instance = null;

    private MoviesService moviesService;

    private RestClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient client = okHttpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        moviesService = retrofit.create(MoviesService.class);
    }

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public MoviesService getMovieService() {
        return moviesService;
    }
}