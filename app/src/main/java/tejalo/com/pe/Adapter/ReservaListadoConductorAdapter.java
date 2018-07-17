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

public class ReservaListadoConductorAdapter extends ArrayAdapter<Reserva> {

    private static final String TAG = "ReservaListadoConductorAdapter";

    public ReservaListadoConductorAdapter(Context context, List<Reserva> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReservaListadoConductorAdapter.ViewHolder viewHolder;
        //ConvertView es la vista del item de la lista
        if (convertView == null) {
            //convertView es nulo cuando se muestra por primera vez
            Log.i(TAG, "Convert view null");
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_reserva_conductor,parent,false);

            viewHolder = new ReservaListadoConductorAdapter.ViewHolder();
            viewHolder.txtReserva = (TextView) convertView.findViewById(R.id.txtReserva);
            viewHolder.txtNombre = (TextView) convertView.findViewById(R.id.txtNombre);
            viewHolder.txtApellido = (TextView) convertView.findViewById(R.id.txtApellido);
            //Agregar el viewHolder al tag del convertView para guardar la referencia y reusar las vistas
            convertView.setTag(viewHolder);

        } else {
            //Si convertView no es nulo, sacar el viewHolder del tag del convertView
            viewHolder = (ReservaListadoConductorAdapter.ViewHolder) convertView.getTag();
        }
        Reserva reserva = getItem(position);

        viewHolder.txtReserva.setText(reserva.getIdReserva().toString());
        viewHolder.txtNombre.setText(reserva.getUsuario().getNombre());
        viewHolder.txtApellido.setText(reserva.getUsuario().getApellido());
        return convertView;
    }

    private static class ViewHolder {
        public TextView txtReserva,txtNombre, txtApellido;
    }

}
