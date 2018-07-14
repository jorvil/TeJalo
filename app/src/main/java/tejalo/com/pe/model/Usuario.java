package tejalo.com.pe.Model;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("codigo")
    private Long codigo;
    @SerializedName("apellido")
    private String apellido;
    @SerializedName("dni")
    private String dni;
    @SerializedName("email")
    private String email;
    @SerializedName("estado")
    private String estado;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("password")
    private String password;
    @SerializedName("sexo")
    private String sexo;
    @SerializedName("telefono")
    private String telefono;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return nombre + " " + apellido;
    }
}
