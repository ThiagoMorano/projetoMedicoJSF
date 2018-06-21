/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.servlet;

import br.ufscar.dc.medico.bean.CadastroMedicoFormBean;
import br.ufscar.dc.medico.bean.Consulta;
import br.ufscar.dc.medico.bean.Medico;
import br.ufscar.dc.medico.bean.NovaConsultaFormBean;
import br.ufscar.dc.medico.bean.Privilegio;
import br.ufscar.dc.medico.dao.ConsultaDAO;
import br.ufscar.dc.medico.dao.MedicoDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO.PrivilegioEnum;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author 496227
 */
@WebServlet(name = "GravarConsultaServlet", urlPatterns = {"/confirmaConsulta"})
public class GravarConsultaServlet extends HttpServlet {
    
    @Resource(name =  "jdbc/MedicoDBLocal")
    DataSource dataSource;
    
    @Inject
    ConsultaDAO cdao;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("text/html;charset=UTF-8");
        
       NovaConsultaFormBean ncfb = (NovaConsultaFormBean) request.getSession().getAttribute("novaConsulta");
       
       request.getSession().removeAttribute("novaConsulta");
       //ConsultaDAO cdao = new ConsultaDAO(dataSource);
              
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataNovaConsulta = sdf.parse(ncfb.getDataConsulta());
            
            Consulta c = new Consulta();
            c.setCpfPaciente(ncfb.getCpfPaciente());
            c.setCrmMedico(ncfb.getCrmMedico());
            c.setDataConsulta(dataNovaConsulta);
            
            cdao.gravarConsulta(c);
            
            request.setAttribute("mensagem", "Consulta marcada com successo!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", e.getLocalizedMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
