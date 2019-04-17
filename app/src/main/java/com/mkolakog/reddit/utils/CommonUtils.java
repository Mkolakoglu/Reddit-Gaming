package com.mkolakog.reddit.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mkolakog.reddit.R;

public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static Snackbar snackbar(View view, String text, int duration) {
        return snackbar(view, text, null, null, duration);
    }

    public static Snackbar snackbar(View view, String text, String action, View.OnClickListener actionClickListener, int duration) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        if (action != null) {
            snackbar.setAction(action, actionClickListener);
        }
        View v = snackbar.getView();
        v.setBackgroundResource(R.color.colorPrimary);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(ContextCompat.getColor(v.getContext(), android.R.color.white));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
        return snackbar;
    }

    public static Toast toast(Context context, int textResId, int duration) {
        return toast(context, context.getString(textResId), duration);
    }

    public static Toast toastWarning(Context context, int textResId, int duration) {
        return toastWarning(context, context.getString(textResId), duration);
    }

    public static Toast toast(Context context, String text, int duration) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, context.getResources().getDimensionPixelSize(R.dimen.large_padding));
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shape_toast_bg);
        toast.show();
        return toast;
    }

    public static Toast toastWarning(Context context, String text, int duration) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, context.getResources().getDimensionPixelSize(R.dimen.large_padding));
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shape_toast_warning_bg);
        toast.show();
        return toast;
    }

}
