package complementos;

import android.app.Application;

public class Contexto extends Application {

    private static Contexto instancia;
    public static String APP_TAG = ".appcanchas";

    @Override
    public void onCreate() {
        super.onCreate();
        instancia = this;
    }

    public static Contexto getInstancia() {
        return instancia;
    }

}