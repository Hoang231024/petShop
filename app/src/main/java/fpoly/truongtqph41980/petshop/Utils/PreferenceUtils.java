package fpoly.truongtqph41980.petshop.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class PreferenceUtils {
    private static final String KEY_FIRST_RUN = "first_run";

    public static boolean isFirstRun(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("NGUOIDUNG", Context.MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean(KEY_FIRST_RUN, true);
        Log.d("PreferenceUtilsssssssss", "isFirstRun: " + isFirstRun);
        if (isFirstRun) {
            Log.d("PreferenceUtilssssssssss", "First run detected, performing necessary tasks");
            // Đây là lần đầu tiên, thực hiện các công việc cần thiết
            // ...

            // Sau khi hoàn thành công việc, đặt isFirstRun thành false
            preferences.edit().putBoolean(KEY_FIRST_RUN, false).apply();
        }else {
            Log.d("PreferenceUtils", "Not the first run");
        }

        return isFirstRun;
    }
}
