/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.servlet;

import br.ufscar.dc.medico.bean.Consulta;
import br.ufscar.dc.medico.bean.Medico;
import java.io.IOException;
import br.ufscar.dc.medico.bean.NovaConsultaFormBean;
import br.ufscar.dc.medico.bean.Privilegio;
import br.ufscar.dc.medico.dao.ConsultaDAO;
import br.ufscar.dc.medico.dao.MedicoDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO.PrivilegioEnum;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Lucas
 */
@WebServlet(name = "CadastrarConsultaServlet", urlPatterns = {"/novaConsulta"})
public class CadastrarConsultaServlet extends HttpServlet {
    
    @Resource(name="jdbc/MedicoDBLocal")
    DataSource dataSource;
    
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

        try {
            MedicoDAO mdao = new MedicoDAO(dataSource);
            List<Medico> listaMedicos = mdao.listarTodosMedicos();
            
            if (request.getMethod().equals("POST")) {
                NovaConsultaFormBean ncfb = new NovaConsultaFormBean();
                BeanUtils.populate(ncfb, request.getParameterMap());
                
                ConsultaDAO cdao = new ConsultaDAO(dataSource);
                List<Consulta> consultas = cdao.listarConsultasPacienteEMedico(ncfb.getCrmMedico(),ncfb.getCpfPaciente());
               
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dataNovaConsulta = sdf.parse(ncfb.getDataConsulta());
                Boolean erro = false;
                List<String> mensagensErro = ncfb.validar();
                
                for (Consulta consulta : consultas) {
                    if (consulta.getDataConsulta().equals(dataNovaConsulta)) {
                        mensagensErro.add("Já existe uma consulta marcada nessa data. Por favor escolha outra data.");
                        break;
                    }
                }

                request.getSession().setAttribute("novaConsulta", ncfb);
                
                if (mensagensErro.isEmpty()) {
                    request.getSession().setAttribute("dataConsulta", dataNovaConsulta);
                    request.getRequestDispatcher("confirmaConsulta.jsp").forward(request, response);
                } else {
                    request.setAttribute("mensagens", mensagensErro);
                    request.setAttribute("medicos", listaMedicos);
                    request.getRequestDispatcher("novaConsulta.jsp").forward(request, response);
                }
                
            } else {
                Privilegio pri = (Privilegio) request.getSession().getAttribute("login");
                
                if (pri != null && pri.getPrivilegio() == PrivilegioEnum.PACIENTE.getValor()){
                    request.setAttribute("medicos", listaMedicos);
                    request.getRequestDispatcher("novaConsulta.jsp").forward(request, response);    
                } else {
                    response.sendRedirect("/ProjetoMedico/login?next=/ProjetoMedico/novaConsulta");
                }
                
            }        
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("mensagem" + e.getLocalizedMessage());
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
