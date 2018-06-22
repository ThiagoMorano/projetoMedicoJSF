package br.ufscar.dc.medico.views;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lucas
 */
public class LoginSM implements Serializable {
    private final boolean mostraFormLogin;
    private final boolean mostraPaginaAutenticada;
    
    public LoginSM (Boolean mostraFormLogin, Boolean mostraPaginaAutenticada) {
        this.mostraFormLogin = mostraFormLogin;
        this.mostraPaginaAutenticada = mostraPaginaAutenticada;        
    }
    
    public static LoginSM inicio() {
        return new LoginSM(true, false);
    }
    
    public static LoginSM logou() {
        return new LoginSM(false, true);
    }
    
    public boolean isMostraFormLogin() {
        return mostraFormLogin;
    }

    public boolean isMostraPaginaAutenticada() {
        return mostraPaginaAutenticada;
    }    
}
