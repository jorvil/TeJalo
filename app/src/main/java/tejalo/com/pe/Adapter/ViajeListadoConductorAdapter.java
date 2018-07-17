package tejalo.com.pe.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tejalo.com.pe.Model.Viaje;
import tejalo.com.pe.R;

public class ViajeListadoConductorAdapter extends ArrayAdapter<Viaje>  {

    private static final String TAG = "ViajeListadoConductorAdapter";

    public ViajeListadoConductorAdapter(Context context, List<Viaje> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViajeListadoConductorAdapter.ViewHolder viewHolder;
        //ConvertView es la vista del item de la lista
        if (convertView == null) {
            //convertView es nulo cuando se muestra por primera vez
            Log.i(TAG, "Convert view null");
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_viaje_conductor,parent,false);

            viewHolder = new ViajeListadoConductorAdapter.ViewHolder();
            viewHolder.txtViaje = (TextView) convertView.findViewById(R.id.txtViaje);
            viewHolder.txtFecha = (TextView) convertView.findViewById(R.id.txtFecha);
            viewHolder.txtOrigen = (TextView) convertView.findViewById(R.id.txtOrigen);
            viewHolder.txtDestino = (TextView) convertView.findViewById(R.id.txtDestino);
            viewHolder.txtCantidad = (TextView) convertView.findViewById(R.id.txtCantidad);
            viewHolder.txtTarifa = (TextView) convertView.findViewById(R.id.txtTarifa);
            //Agregar el viewHolder al tag del convertView para guardar la referencia y reusar las vistas
            convertView.setTag(viewHolder);

        } else {
            //Si convertView no es nulo, sacar el viewHolder del tag del convertView
            viewHolder = (ViajeListadoConductorAdapter.ViewHolder) convertView.getTag();
        }
        Viaje viaje = getItem(position);

        viewHolder.txtViaje.setText(viaje.getIdViaje().toString());
        viewHolder.txtFecha.setText(viaje.getFecha());
        viewHolder.txtOrigen.setText(viaje.getOrigen().getNombre());
        viewHolder.txtDestino.setText(viaje.getDestino().getNombre());
        viewHolder.txtCantidad.setText(String.valueOf(viaje.getCantidad()));
        viewHolder.txtTarifa.setText(String.valueOf(viaje.getTarifa()));
        return convertView;
    }

    private static class ViewHolder {
        public TextView txtViaje,txtFecha, txtOrigen, txtDestino, txtCantidad, txtTarifa;
    }

}
