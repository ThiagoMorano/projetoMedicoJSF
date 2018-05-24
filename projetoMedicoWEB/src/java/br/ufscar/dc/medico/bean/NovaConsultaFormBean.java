/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class NovaConsultaFormBean {
    private String cpfPaciente, crmMedico, dataConsulta;

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }
    
    public List<String> validar() {
        List<String> mensagens = new ArrayList<String>();
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
            Date dataNovaConsulta = sdf.parse(this.getDataConsulta());
            Date diaDeHoje = new Date();
            
            if (this.crmMedico == null) {
                mensagens.add("Por favor escolha um médico para a consulta!");
            }

            if (this.crmMedico.equals("")) {
                mensagens.add("Por favor escolha um médico para a consulta!");
            }

            if (dataNovaConsulta.before(diaDeHoje)) {
                mensagens.add("Por favor marque uma consulta para algum dia válido!");
            }
        } catch(Exception e) {
            mensagens.add(e.getLocalizedMessage());
        }
        
        return mensagens;
    }
}
