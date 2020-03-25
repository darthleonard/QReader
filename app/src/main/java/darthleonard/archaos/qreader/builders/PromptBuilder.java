package darthleonard.archaos.qreader.builders;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import darthleonard.archaos.qreader.interfaces.IPermissionHandler;
import darthleonard.archaos.qreader.MainActivity;
import darthleonard.archaos.qreader.R;

public class PromptBuilder {
    public void Create(final MainActivity mainActivity, int messageId, final IPermissionHandler permissionHandler) {
        new AlertDialog.Builder(mainActivity)
                .setTitle(R.string.permissionPromptTitle)
                .setMessage(messageId)
                .setNeutralButton(R.string.permissionButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionHandler.RequestPermissions(mainActivity);
                    }
                })
                .create()
                .show();
    }
}
