package com.example.login;

import java.io.Serializable;

public class userLoginGetData {

    private String NOMBRE;
    private String EMAIL;
    private String TELEFONO;
    private String PASSWORD;
    private String DISPOSITIVO;
    private String PAGO;

    public userLoginGetData() {

    }

    public String getNOMBRE() {
        return NOMBRE;
    }
    public void setNOMBRE(String nombre) {
        this.NOMBRE = nombre;
    }

    public String getEMAIL() {
        return EMAIL;
    }
    public void setEMAIL(String email) {
        this.EMAIL = email;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }
    public void setTELEFONO(String  tel) {
        this.TELEFONO = tel;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
    public void setPASSWORD(String pass) {  this.PASSWORD = pass;}

    public String getDISPOSITIVO() {
        return DISPOSITIVO;
    }
    public void setDISPOSITIVO(String dis) {
        this.DISPOSITIVO = dis;
    }

    public String getPAGO() {
        return PAGO;
    }
    public void setPAGO(String pago) {
        this.PAGO = pago;
    }

}
