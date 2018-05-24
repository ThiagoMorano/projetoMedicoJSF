/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.bean;

/**
 *
 * @author 619680
 */
public class Privilegio {
    private int id;
    String login;
    int privilegio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(int Privilegio) {
        this.privilegio = Privilegio;
    }
}
