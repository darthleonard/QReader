package darthleonard.archaos.qreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import darthleonard.archaos.qreader.interfaces.CodeItemClickListener;
import darthleonard.archaos.qreader.models.CodesAdapter;

public class CodesActivity extends AppCompatActivity implements CodeItemClickListener {
    CodesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codes);

        // data to populate the RecyclerView with
        ArrayList<String> codes = new ArrayList<>();
        codes.add("Horse");
        codes.add("Cow");
        codes.add("Camel");
        codes.add("Sheep");
        codes.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCodes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CodesAdapter(this, codes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
