package es.cifpcarlos3.tarea401.model;

import java.util.Date;

public class Nomina {
    private int idNomina;
    private int idUsu;
    private float pago;
    private Date fecha;

    public Nomina(int idNomina, int idUsu, float pago, Date fecha) {
        this.idNomina = idNomina;
        this.idUsu = idUsu;
        this.pago = pago;
        this.fecha = fecha;
    }

    public int getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(int idNomina) {
        this.idNomina = idNomina;
    }

    public int getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(int idUsu) {
        this.idUsu = idUsu;
    }

    public float getPago() {
        return pago;
    }

    public void setPago(float pago) {
        this.pago = pago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
