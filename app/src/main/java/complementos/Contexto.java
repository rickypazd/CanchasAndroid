package complementos;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class Contexto extends Application {

    private static Contexto instancia;
    public static String APP_TAG = ".appcanchas";

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        instancia = this;
    }

    public static Contexto getInstancia() {
        return instancia;
    }

}