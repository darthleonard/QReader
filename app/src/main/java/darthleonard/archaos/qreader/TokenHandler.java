package darthleonard.archaos.qreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.URLUtil;

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
}
