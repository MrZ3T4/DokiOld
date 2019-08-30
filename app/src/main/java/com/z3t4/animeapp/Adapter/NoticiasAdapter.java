package com.z3t4.animeapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.z3t4.animeapp.Model.Noticia;
import com.z3t4.animeapp.R;

import java.util.List;

import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.MyViewHolder> {

    private List<Noticia> noticias;
    private Context ctx;

    public NoticiasAdapter(List<Noticia> noticiass, Context ctx) {
        this.noticias = noticiass;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.zanoticias_modelo,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Noticia actual = noticias.get(position);

        holder.mTitulo.setText(actual.getmTitulo());
        holder.mAutor.setText(actual.getmCreador());
        holder.mDescripcion.setText(actual.getmDescripcion()
                .substring(actual.getmDescripcion().indexOf("<p>"), actual.getmDescripcion().indexOf("<a"))
                .substring(3).replaceAll("&#8220;", "").replaceAll("&#160;", " ").replaceAll("&#8221;", " ").replaceAll("&#8211;", ""));
        holder.mFecha.setText(actual.getmFecha().substring(3,17).replace("," , " ").replace("Aug", "de Agosto del").replace("Sep","de Septiembre del"));

        for (int i = 0 ; i < noticias.size() ; i++);{

            if (actual.getmThumb().contains("<img")) {
                holder.mImagen.setVisibility(View.VISIBLE);
                String imagenFinal = actual.getmThumb()
                        .substring(actual.getmThumb().indexOf("<img src=\""),
                                actual.getmThumb().indexOf("\" alt")).substring(10).replace("http", "https")
                        .replaceAll("httpss", "https");
                    Picasso.with(ctx).load(imagenFinal).into(holder.mImagen);

                    System.out.println(imagenFinal);

            } else if (actual.getmThumb().contains("\" src=\"")) {

                if (actual.getmThumb().contains("youtube")) {
                String getYoutubeThumb = actual.getmThumb().substring(actual.getmThumb().indexOf("\" src=\"")
                        , actual.getmThumb().indexOf("?")).substring(37);
                String youtubeThumbFinal = "https://img.youtube.com/vi/" + getYoutubeThumb + "/0.jpg";
                Picasso.with(ctx).load(youtubeThumbFinal).resize(400, 320).into(holder.mImagen);

                    System.out.println(youtubeThumbFinal);

                } else if (actual.getmThumb().contains("dailymotion")) {
                    String getDailymotionThumb = actual.getmThumb().substring(actual.getmThumb().indexOf("\" src=\"")
                            , actual.getmThumb().indexOf("\" a")).substring(47);
                    String dailymotionThumbFinal = "https://www.dailymotion.com/thumbnail/video/" + getDailymotionThumb;
                    Picasso.with(ctx).load(dailymotionThumbFinal).resize(400, 320).into(holder.mImagen);

                    System.out.println(dailymotionThumbFinal);

                } else if (actual.getmThumb().contains("<center>")) {
                    Picasso.with(ctx).cancelRequest(holder.mImagen);
                    holder.mImagen.setImageResource(R.drawable.twitter_content);
                    System.out.println("Twitter contenido");}

            } else { holder.mImagen.setVisibility(View.GONE);
                Picasso.with(ctx).cancelRequest(holder.mImagen); } }


                holder.mNoticia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                                .addDefaultShareMenuItem()
                                .setToolbarColor(ctx.getResources().getColor(R.color.Toolbar_Light))
                                .setShowTitle(true)
                                .build();
                        CustomTabsHelper.addKeepAliveExtra(ctx, customTabsIntent.intent);
                        CustomTabsHelper.openCustomTab(ctx, customTabsIntent,
                                Uri.parse(actual.getmLink()),
                                new WebViewFallback());
                    }
                });
    }

    @Override
    public int getItemCount() {
        try {
            if (noticias.size() > 0) {
              //  System.out.println("contiene objetos");
                return noticias.size();
            }
        } catch (NullPointerException e) {
            //System.out.println("No contiene objetos... roto");
            }
        return getItemCount();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTitulo, mAutor, mFecha, mDescripcion;
        CardView mNoticia;
        ImageView mImagen;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            mImagen = itemView.findViewById(R.id.news_image);
            mNoticia = itemView.findViewById(R.id.news_cardview);
            mTitulo = itemView.findViewById(R.id.news_titulo);
            mAutor = itemView.findViewById(R.id.news_autor);
            mFecha = itemView.findViewById(R.id.news_fecha);
            mDescripcion = itemView.findViewById(R.id.news_desc);

        }
    }
}
