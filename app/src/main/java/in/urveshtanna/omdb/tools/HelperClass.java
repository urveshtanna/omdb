package in.urveshtanna.omdb.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Helpler with genric methods for error message,toast,etc
 *
 * @author urveshtanna
 * @version 1.0
 * @since 1.0
 */

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

    public static void showDialogMessage(Context context, String title, String message, String positiveText, String neutralText, String negativeText, boolean isCancelable, OnDialogClickListener onDialogClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(isCancelable);

        if (positiveText != null)
            builder.setPositiveButton(positiveText, (dialog, which) -> {
                if (onDialogClickListener != null)
                    onDialogClickListener.onPositiveClick();
            });
        if (negativeText != null)
            builder.setNegativeButton(negativeText, ((dialog, which) -> {
                if (onDialogClickListener != null)
                    onDialogClickListener.onNegativeClick();
            }));

        if (neutralText != null)
            builder.setNeutralButton(neutralText, ((dialog, which) -> {
                if (onDialogClickListener != null)
                    onDialogClickListener.onNeutralClick();
            }));

        builder.create().show();
    }

    public interface OnDialogClickListener {

        void onPositiveClick();

        void onNegativeClick();

        void onNeutralClick();
    }
}
