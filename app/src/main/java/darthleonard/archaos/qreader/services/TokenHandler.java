package darthleonard.archaos.qreader.services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.URLUtil;

public class TokenHandler {
    public void OpenToken(Context context, String token) {
        Intent intent;
        if (URLUtil.isValidUrl(token)) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(token));
        } else {
            intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, token);
            intent.setType("text/plain");
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}