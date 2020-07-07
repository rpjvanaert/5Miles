package five.miles.failurism.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.miles.failurism.R;

public class LinkDialogFragment extends DialogFragment {
    private String largeUrl;
    public LinkDialogFragment(String largeURL) {
        this.largeUrl = largeURL;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.details_title).setMessage(getString(R.string.link_opener) + this.largeUrl).setPositiveButton(R.string.confirm, null).setNegativeButton(R.string.copy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newHtmlText(getString(R.string.clip_label), largeUrl, largeUrl);
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(getContext(), R.string.link_copied_toast, Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }
}
