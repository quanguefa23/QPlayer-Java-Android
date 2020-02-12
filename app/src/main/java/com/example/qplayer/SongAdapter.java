package com.example.qplayer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Song> listSong;
    private int selected;

    public SongAdapter(Context context, int layout, ArrayList<Song> listPlayer) {
        this.context = context;
        this.layout = layout;
        this.listSong = listPlayer;
        selected = -1;
    }

    @Override
    public int getCount() {
        return listSong.size();
    }

    @Override
    public Object getItem(int i) {
        return listSong.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void moveUp(int i) {
        if (i == 0)
            return;

        Song t = listSong.get(i);
        listSong.set(i, listSong.get(i - 1));
        listSong.set(i - 1, t);

        if (selected == i)
            selected--;
        else if (selected > -1 && selected == i - 1)
            selected++;
    }

    public void moveDown(int i) {
        if (i == getCount() - 1)
            return;

        Song t = listSong.get(i);
        listSong.set(i, listSong.get(i + 1));
        listSong.set(i + 1, t);

        if (selected == i)
            selected++;
        else if (selected == i + 1)
            selected--;
    }

    public int getSrc(int i) {
        return listSong.get(i).getSrcMusic();
    }

    private class ViewHolder {
        TextView nameTV;
        TextView artistTV;
        TextView qualityTV;
        ImageView bannerIV;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();

            //map
            holder.nameTV = view.findViewById(R.id.name);
            holder.artistTV = view.findViewById(R.id.artist);
            holder.qualityTV = view.findViewById(R.id.quality);
            holder.bannerIV = view.findViewById(R.id.small_banner);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        if (selected == i) {
            view.setBackgroundColor(Color.parseColor("#33C9C920"));
        }
        else
            view.setBackgroundColor(Color.parseColor("#33E6E6DC"));

        //get value
        Song temp = listSong.get(i);
        holder.nameTV.setText(temp.getName());
        holder.artistTV.setText(temp.getArtist());
        holder.qualityTV.setText(temp.getQuality());
        holder.bannerIV.setImageResource(temp.getBannerImage());

        return view;
    }

    public void select(int i) {
        selected = i;
    }

    public void remove(int i) {
        listSong.remove(i);
    }

    public boolean isSelected(int i) {
        return i == selected;
    }

}
