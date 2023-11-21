package com.example.appfinalcomcielo.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinalcomcielo.R;
import com.example.appfinalcomcielo.model.Fatura;
import com.example.appfinalcomcielo.views.activity.PayActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList<Fatura> faturas;

    public CustomAdapter(Context context, Activity activity, ArrayList<Fatura> faturas) {
        this.context = context;
        this.activity = activity;
        this.faturas = faturas;
    }


    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {
        Fatura fatura = faturas.get(position);

        holder.email.setText(String.valueOf(fatura.getUserEmail()));
        holder.produto.setText(String.valueOf(fatura.getProduct()));
        holder.valor.setText(String.valueOf(fatura.getValue()));
        holder.isCredit.setText(String.valueOf(fatura.getIsCredit()));
        holder.parcela.setText(String.valueOf(fatura.getParcel()));


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra("user_email", fatura.getUserEmail());
                intent.putExtra("produto", fatura.getProduct());
                intent.putExtra("valor", fatura.getValue());
                intent.putExtra("is_Credit", fatura.getIsCredit());
                intent.putExtra("parcela", fatura.getParcel());
                intent.putExtra("_id", fatura.getId());
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return faturas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView email, valor, produto, isCredit, parcela;

        LinearLayout linearLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.email_txt);
            valor = itemView.findViewById(R.id.valor_txt);
            produto = itemView.findViewById(R.id.txtNome);
            linearLayout = itemView.findViewById(R.id.mainLayout);
            isCredit = itemView.findViewById(R.id.iscredit_txt);
            parcela = itemView.findViewById(R.id.parcela_txt);





        }
    }
}
