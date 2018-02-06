package agtzm.tareaswipe;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class App extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ArrayList<listItem> items = null;
    private ListView lista            = null;
    private adapterListItems adapter  = null;
    private int cuantas = 12;
    private SwipeRefreshLayout swipe=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app);


        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipe.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        swipe.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright);


        lista = (ListView)findViewById(R.id.list);
        items = new ArrayList<>();
        for (int i=0;i<cuantas;i++){
            items.add(new listItem("Titulo "+i,"Subtitulo "+i));
        }
        adapter = new adapterListItems(this,items);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void onRefresh() {
        swipe.setRefreshing(true);
        //Vamos a generar un hilo para simular la carga de un dato
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(lista.getChildCount()>0)
                    items.remove(--cuantas);
                //cuantos--;
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);

            }
        },100);

    }

    class adapterListItems extends ArrayAdapter<listItem>{

        Context context;

        public adapterListItems(@NonNull Context context, ArrayList<listItem> users) {
            super(context, 0,users);
            this.context = context;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View item = convertView;

            listItem user = getItem(position);

            if(item == null) {
                item = LayoutInflater.from(getContext()).inflate(R.layout.swipe, parent, false);
            }
            TextView tvName = (TextView)item.findViewById(R.id.textView);
            TextView tvHome = (TextView)item.findViewById(R.id.textView2);
            tvName.setText(user.getTitle());
            tvHome.setText(user.getSubtitle());
            return item;
        }
    }

}