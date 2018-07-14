package tejalo.com.pe.Model;

import com.google.gson.annotations.SerializedName;

public class Viaje {

    @SerializedName("idViaje")
    private Long idViaje;
    @SerializedName("distritoOrigen")
    private Long distritoOrigen;
    @SerializedName("distritoDestino")
    private Long distritoDestino;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("hora")
    private String hora;
    @SerializedName("cantidad")
    private int cantidad;
    @SerializedName("tarifa")
    private double tarifa;
    @SerializedName("estado")
    private String estado;
    @SerializedName("usuario")
    private Usuario usuario;

    public Long getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Long idViaje) {
        this.idViaje = idViaje;
    }

    public Long getDistritoOrigen() {
        return distritoOrigen;
    }

    public void setDistritoOrigen(Long distritoOrigen) {
        this.distritoOrigen = distritoOrigen;
    }

    public Long getDistritoDestino() {
        return distritoDestino;
    }

    public void setDistritoDestino(Long distritoDestino) {
        this.distritoDestino = distritoDestino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
