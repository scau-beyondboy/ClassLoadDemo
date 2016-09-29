package intefa;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface IDynamic {
    String getTip();
    Drawable getDrawable(Resources res, @DrawableRes int id);
    String getString(Resources res);
    public void startPluginActivity(Context context,Class<?> cls);
    public void startPluginActivity(Context context);
}
