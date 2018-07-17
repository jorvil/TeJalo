package tejalo.com.pe.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tejalo.com.pe.Model.Reserva;
import tejalo.com.pe.R;

public class ReservaListadoAdapter extends ArrayAdapter<Reserva> {

    private static final String TAG = "ReservaListadoAdapter";

    public ReservaListadoAdapter(Context context, List<Reserva> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReservaListadoAdapter.ViewHolder viewHolder;
        //ConvertView es la vista del item de la lista
        if (convertView == null) {
            //convertView es nulo cuando se muestra por primera vez
            Log.i(TAG, "Convert view null");
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_reserva,parent,false);

            viewHolder = new ReservaListadoAdapter.ViewHolder();
            viewHolder.txtReserva = (TextView) convertView.findViewById(R.id.txtReserva);
            viewHolder.txtFecha = (TextView) convertView.findViewById(R.id.txtFecha);
            viewHolder.txtOrigen = (TextView) convertView.findViewById(R.id.txtOrigen);
            viewHolder.txtDestino = (TextView) convertView.findViewById(R.id.txtDestino);
            viewHolder.txtConductor = (TextView) convertView.findViewById(R.id.txtNombre);
            viewHolder.txtTarifa = (TextView) convertView.findViewById(R.id.txtTarifa);
            //Agregar el viewHolder al tag del convertView para guardar la referencia y reusar las vistas
            convertView.setTag(viewHolder);

        } else {
            //Si convertView no es nulo, sacar el viewHolder del tag del convertView
            viewHolder = (ReservaListadoAdapter.ViewHolder) convertView.getTag();
        }
        Reserva reserva = getItem(position);

        viewHolder.txtReserva.setText(reserva.getIdReserva().toString());
        viewHolder.txtFecha.setText(reserva.getFecha());
        viewHolder.txtOrigen.setText(reserva.getViaje().getOrigen().getNombre());
        viewHolder.txtDestino.setText(reserva.getViaje().getDestino().getNombre());
        viewHolder.txtConductor.setText(reserva.getViaje().getUsuario().getNombre());
        viewHolder.txtTarifa.setText(String.valueOf(reserva.getViaje().getTarifa()));
        return convertView;
    }

    private static class ViewHolder {
        public TextView txtReserva,txtFecha, txtOrigen, txtDestino, txtConductor, txtTarifa;
    }

}
