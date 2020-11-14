package com.isil.appproyectoandroid.models;

import java.util.Date;

public class Movimiento {

    Integer idmovimiento;
    String fecha;
    String descripcion;
    float monto;
    int movimiento;

    public Movimiento(Integer idmovimiento, String fecha, String descripcion, float monto, int movimiento) {
        this.idmovimiento = idmovimiento;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.monto = monto;
        this.movimiento = movimiento;
    }

    public Movimiento() {
    }

    public Integer getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(Integer idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "idmovimiento=" + idmovimiento +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", monto=" + monto +
                ", movimiento=" + movimiento +
                '}';
    }
}
