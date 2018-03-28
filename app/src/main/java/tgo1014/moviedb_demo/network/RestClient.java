package tgo1014.moviedb_demo.network;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tgo1014.moviedb_demo.BuildConfig;
import tgo1014.moviedb_demo.network.services.GenresService;
import tgo1014.moviedb_demo.network.services.MoviesService;

public class RestClient {

    private static RestClient instance = null;

    private MoviesService moviesService;
    private GenresService genresService;

    private static String BASE_URL_POSTER = "https://image.tmdb.org/t/p/";
    public static String BASE_URL_POSTER_SIZE_185 = BASE_URL_POSTER + "w185/";
    public static String BASE_URL_POSTER_SIZE_342 = BASE_URL_POSTER + "w342/";
    public static String BASE_URL_POSTER_SIZE_500 = BASE_URL_POSTER + "w500/";
    public static String BASE_URL_POSTER_SIZE_ORIGINAL = BASE_URL_POSTER + "original/";

    private RestClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        interceptAndAddApiKey(okHttpBuilder);

        OkHttpClient client = okHttpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        moviesService = retrofit.create(MoviesService.class);
        genresService = retrofit.create(GenresService.class);

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

    public GenresService getGenresService() {
        return genresService;
    }

    private void interceptAndAddApiKey(OkHttpClient.Builder client) {
        client.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
    }
}