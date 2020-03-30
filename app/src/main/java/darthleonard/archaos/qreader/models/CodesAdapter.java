package darthleonard.archaos.qreader.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import darthleonard.archaos.qreader.R;
import darthleonard.archaos.qreader.holders.CodeViewHolder;
import darthleonard.archaos.qreader.interfaces.CodeItemClickListener;

public class CodesAdapter extends RecyclerView.Adapter<CodeViewHolder> {
    private List<String> dataSet;
    private LayoutInflater layoutInflater;
    private CodeItemClickListener codeClickListener;

    public CodesAdapter(Context context, List<String> dataSet) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }

    @Override
    public CodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CodeViewHolder(
                layoutInflater.inflate(R.layout.recyclerview_row, parent, false),
                codeClickListener);
    }

    @Override
    public void onBindViewHolder(CodeViewHolder holder, int position) {
        holder.tvCode.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public String getItem(int id) {
        return dataSet.get(id);
    }

    public void setClickListener(CodeItemClickListener codeItemClickListener) {
        this.codeClickListener = codeItemClickListener;
    }
}
