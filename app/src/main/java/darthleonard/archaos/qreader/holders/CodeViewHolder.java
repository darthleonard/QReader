package darthleonard.archaos.qreader.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import darthleonard.archaos.qreader.R;
import darthleonard.archaos.qreader.interfaces.CodeItemClickListener;

public class CodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private CodeItemClickListener clickListener;
    public TextView tvCode;

    public CodeViewHolder(View itemView, CodeItemClickListener clickListener) {
        super(itemView);
        this.tvCode = itemView.findViewById(R.id.tvCode);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (clickListener != null) {
            clickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
