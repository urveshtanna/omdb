package in.urveshtanna.omdb.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class HelperClass {

    private static String TAG = "HelperClass";

    public static void showMessage(Context context, String message) {
        try {
            showSnackBar(context, message);
        } catch (Exception e) {
            showToastBar(context, message);
            e.printStackTrace();
        }
    }

    public static void showSnackBar(Context context, String message) {
        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showToastBar(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void copyToClipboard(Context context, String codeToCopy, String toastMessage) {
        if (codeToCopy != null && !codeToCopy.isEmpty()) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(codeToCopy, codeToCopy);
            clipboard.setPrimaryClip(clip);
            HelperClass.showToastBar(context, toastMessage);
        }
    }
}
