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

public class ViajeListadoPasajeroAdapter extends ArrayAdapter<Viaje> {

    private static final String TAG = "ViajeListadoPasajeroAdapter";

    public ViajeListadoPasajeroAdapter(Context context, List<Viaje> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViajeListadoPasajeroAdapter.ViewHolder viewHolder;
        //ConvertView es la vista del item de la lista
        if (convertView == null) {
            //convertView es nulo cuando se muestra por primera vez
            Log.i(TAG, "Convert view null");
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_viaje_pasajero,parent,false);

            viewHolder = new ViajeListadoPasajeroAdapter.ViewHolder();
            viewHolder.txtViaje = (TextView) convertView.findViewById(R.id.txtViaje);
            viewHolder.txtFecha = (TextView) convertView.findViewById(R.id.txtFecha);
            viewHolder.txtNombre = (TextView) convertView.findViewById(R.id.txtNombre);
            viewHolder.txtApellido = (TextView) convertView.findViewById(R.id.txtApellido);
            viewHolder.txtTarifa = (TextView) convertView.findViewById(R.id.txtTarifa);
            //Agregar el viewHolder al tag del convertView para guardar la referencia y reusar las vistas
            convertView.setTag(viewHolder);

        } else {
            //Si convertView no es nulo, sacar el viewHolder del tag del convertView
            viewHolder = (ViajeListadoPasajeroAdapter.ViewHolder) convertView.getTag();
        }
        Viaje viaje = getItem(position);

        viewHolder.txtViaje.setText(viaje.getIdViaje().toString());
        viewHolder.txtFecha.setText(viaje.getFecha());
        viewHolder.txtNombre.setText(viaje.getUsuario().getNombre());
        viewHolder.txtApellido.setText(viaje.getUsuario().getApellido());
        viewHolder.txtTarifa.setText(String.valueOf(viaje.getTarifa()));
        return convertView;
    }

    private static class ViewHolder {
        public TextView txtViaje,txtFecha, txtNombre, txtApellido, txtTarifa;
    }

}
