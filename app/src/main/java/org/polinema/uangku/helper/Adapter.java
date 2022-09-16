package org.polinema.uangku.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.polinema.uangku.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context context;

    private ArrayList idTgl, idNominal, idKet, idStatus;

    public Adapter(Context context, ArrayList idTgl, ArrayList idNominal, ArrayList idKet, ArrayList idStatus) {
        this.context = context;
        this.idTgl = idTgl;
        this.idNominal = idNominal;
        this.idKet = idKet;
        this.idStatus = idStatus;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cashflow,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (idStatus.get(position).equals("Pemasukan")) {
            holder.idNominal.setText(String.valueOf(idNominal.get(position)));
            holder.idStatus.setImageResource(R.drawable.left_arrow);

        }else {
            holder.idNominal.setText(String.valueOf(idNominal.get(position)));
            holder.idStatus.setImageResource(R.drawable.right_arrow);
        }
        holder.idKet.setText(String.valueOf(idKet.get(position)));
        holder.idTgl.setText(String.valueOf(idTgl.get(position)));
    }

    @Override
    public int getItemCount() {
        return idNominal.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idTgl, idNominal, idKet;
        ImageView idStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idNominal = itemView.findViewById(R.id.txtNominalCard);
            idKet = itemView.findViewById(R.id.txtKetCard);
            idTgl = itemView.findViewById(R.id.txtTglCard);
            idStatus = itemView.findViewById(R.id.imgCard);
        }
    }
}
