package tejalo.com.pe.Model;

import com.google.gson.annotations.SerializedName;

public class Viaje {

    @SerializedName("idViaje")
    private Long idViaje;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("hora")
    private String hora;
    @SerializedName("cantidad")
    private int cantidad;
    @SerializedName("disponible")
    private int disponible;
    @SerializedName("tarifa")
    private double tarifa;
    @SerializedName("estado")
    private String estado;

    @SerializedName("usuario")
    private Usuario usuario;
    @SerializedName("origen")
    private Distrito origen;
    @SerializedName("destino")
    private Distrito destino;

    public Long getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Long idViaje) {
        this.idViaje = idViaje;
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

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
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

    public Distrito getOrigen() {
        return origen;
    }

    public void setOrigen(Distrito origen) {
        this.origen = origen;
    }

    public Distrito getDestino() {
        return destino;
    }

    public void setDestino(Distrito destino) {
        this.destino = destino;
    }
}
