package br.com.carlos.projeto.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import br.com.carlos.projeto.db.models.Carro;
import br.com.carlos.projeto.R;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class CarroAdapter extends RealmBaseAdapter<Carro> implements ListAdapter {

    private static class ViewHolder {

        AppCompatTextView tv_placa, tv_modelo, tv_cor;

    }

    public CarroAdapter(OrderedRealmCollection<Carro> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.carros_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_modelo = (AppCompatTextView) convertView.findViewById(R.id.tv_modelo);
            viewHolder.tv_cor = (AppCompatTextView) convertView.findViewById(R.id.tv_cor);
            viewHolder.tv_placa = (AppCompatTextView) convertView.findViewById(R.id.tv_placa);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            final Carro carro = adapterData.get(position);

            viewHolder.tv_modelo.setText(carro.getModelo());
            viewHolder.tv_cor.setText(carro.getCor());
            viewHolder.tv_placa.setText(carro.getPlaca());

            /*if (inDeletionMode) {
                viewHolder.deleteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        countersToDelete.add(seriados.getId());
                    }
                });
            } else {
                viewHolder.deleteCheckBox.setOnCheckedChangeListener(null);
            }
            viewHolder.deleteCheckBox.setChecked(countersToDelete.contains(seriados.getId()));
            viewHolder.deleteCheckBox.setVisibility(inDeletionMode ? View.VISIBLE : View.GONE);*/
        }
        return convertView;
    }

}
