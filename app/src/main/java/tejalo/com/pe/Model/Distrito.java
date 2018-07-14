package tejalo.com.pe.Model;

import com.google.gson.annotations.SerializedName;

public class Distrito {

    @SerializedName("codigo")
    private Long codigo;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("provincia_id")
    private Long provincia_id;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getProvincia_id() {
        return provincia_id;
    }

    public void setProvincia_id(Long provincia_id) {
        this.provincia_id = provincia_id;
    }

    public String toString() {
        return nombre;
    }
}
