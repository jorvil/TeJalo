package tejalo.com.pe.Model;

import com.google.gson.annotations.SerializedName;

public class Reniec {

    @SerializedName("dni")
    private Long dni;
    @SerializedName("apellidos")
    private String apellidos;
    @SerializedName("nombres")
    private String nombres;
    @SerializedName("sexo")
    private int sexo;

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }
}
