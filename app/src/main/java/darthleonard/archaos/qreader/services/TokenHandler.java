package darthleonard.archaos.qreader.services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.URLUtil;

import darthleonard.archaos.qreader.database.DBManager;

public class TokenHandler {
    public void OpenToken(Context context, String token) {
        if (URLUtil.isValidUrl(token)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(token));
            context.startActivity(browserIntent);
        } else {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, token);
            shareIntent.setType("text/plain");
            context.startActivity(shareIntent);
        }
    }

    public void Save(Context context, String token) {
        Save(context, token, "");
    }

    public void Save(Context context, String token, String notes) {
        DBManager db = new DBManager(context);
        if(db.Exist(token)) {
            return;
        }
        db.Insert(token, notes);
    }
}
