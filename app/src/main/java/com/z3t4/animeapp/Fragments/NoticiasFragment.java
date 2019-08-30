package com.z3t4.animeapp.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.z3t4.animeapp.Model.Noticia;
import com.z3t4.animeapp.Adapter.NoticiasAdapter;
import com.z3t4.animeapp.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class NoticiasFragment extends Fragment {

    private RecyclerView recyclerView;
    private com.airbnb.lottie.LottieAnimationView progressBar;
    private TextView textnews;
    private int resId = R.anim.layout_items;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.z_noticias_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progress_news);
        textnews = view.findViewById(R.id.text_news);

        swipeRefreshLayout = view.findViewById(R.id.swp_news);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actualizarContenido();
            }
        });
        actualizarContenido();
        return view;
    }

    public void actualizarContenido(){
    LectorRss lectorRss = new LectorRss(getActivity(), recyclerView);
    lectorRss.execute(); }


    public class LectorRss extends AsyncTask<Void, Void, Void> {

        private Context context;
        private String direccion = "https://somoskudasai.com/feed";
        private URL url;
        private RecyclerView recyclerView;
        private List<Noticia> noticias;

        public LectorRss(Context context, RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            if (swipeRefreshLayout.isRefreshing()) {
                textnews.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            } else {
            recyclerView.setVisibility(View.INVISIBLE);
            textnews.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);}
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            procesarXML(obtenerDatos());
            return null;
        }

        public Document obtenerDatos(){
            try {
                url = new URL (direccion);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document xmlDoc = builder.parse(inputStream);
                return  xmlDoc;
            } catch (Exception e) {e.printStackTrace();}
            return null; }

        private void procesarXML(Document data) {

            if (data != null) {
                System.out.println("Peticion obtenida");
                noticias = new ArrayList<>();
                Element root = data.getDocumentElement();
                Node channel = root.getChildNodes().item(1);
                NodeList items = channel.getChildNodes();
                for (int i = 0; i < items.getLength(); i++) {
                    Node cureentchild = items.item(i);
                    if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                        NodeList itemchilds = cureentchild.getChildNodes();
                        Noticia noticia = new Noticia();
                        for (int j = 0; j < itemchilds.getLength(); j++) {
                            Node cureent = itemchilds.item(j);
                            if (cureent.getNodeName().equalsIgnoreCase("title")) {
                                noticia.setmTitulo(cureent.getTextContent());
                            } else if (cureent.getNodeName().equalsIgnoreCase("pubDate")) {
                                noticia.setmFecha(cureent.getTextContent());
                            } else if (cureent.getNodeName().equalsIgnoreCase("dc:creator")) {
                                noticia.setmCreador(cureent.getTextContent());
                            } else if (cureent.getNodeName().equalsIgnoreCase("link")) {
                                noticia.setmLink(cureent.getTextContent());
                            } else if (cureent.getNodeName().equalsIgnoreCase("content:encoded")) {
                                noticia.setmThumb(cureent.getTextContent());
                            } else if (cureent.getNodeName().equalsIgnoreCase("description")) {
                                noticia.setmDescripcion(cureent.getTextContent());
                            }
                        }
                        noticias.add(noticia);
                    }
                }
            } else {
                System.out.println("Reiniciando peticion");
                obtenerDatos();
                procesarXML(obtenerDatos());}
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            swipeRefreshLayout.setRefreshing(false);
            recyclerView.setVisibility(View.VISIBLE);
            textnews.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            NoticiasAdapter noticiasAdapter = new NoticiasAdapter(noticias, context);
            recyclerView.setAdapter(noticiasAdapter);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
            recyclerView.setLayoutAnimation(animation);
            noticiasAdapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);}
    }
}
