package complementos;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class infoCelda implements Serializable {
    private int dia;
    private String hora;
    private String Fecha;
    private int costo;
    private int abierto;
    private int tipo;
    private String texto;
    private int estado;
    public infoCelda() {
    }

    public infoCelda(int dia, String hora, String fecha, int costo, int abierto, int tipo) {
        this.dia = dia;
        this.hora = hora;
        Fecha = fecha;
        this.costo = costo;
        this.abierto = abierto;
        this.tipo = tipo;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getAbierto() {
        return abierto;
    }

    public void setAbierto(int abierto) {
        this.abierto = abierto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getdia(){
        switch (this.dia){
            case 0:
                return "Lun";
            case 1:
                return "Mar";
            case 2:
                return "Mie";
            case 3:
                return "Jue";
            case 4:
                return "Vie";
            case 5:
                return "Sab";
            case 6:
                return "Dom";

        }
        return "";
    }

    private SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy");
    private SimpleDateFormat formfin = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date fech;

    public String getFechaHora(){
        String fecha="";
        try {
            fech = form.parse(getFecha());
            fecha=formfin.format(fech);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fecha+" "+getHora();
    }
    public Date getDateFecha(){
        String fecha="";
        try {
            fech = form.parse(getFecha());
            fecha=formfin.format(fech);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            return date.parse(fecha+" "+getHora());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
