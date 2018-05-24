<%-- 
    Document   : confirmaConsulta
    Created on : 24/04/2018, 14:36:24
    Author     : Lucas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ProjetoMedicoWEB</title>
    </head>
    <body>
        <h1>Nova Consulta</h1>
        Por favor, verifique os dados da consulta!
        <br/><br/>
        CPF do Paciente: ${sessionScope.novaConsulta.cpfPaciente}<br/>
        CRM do Medico: ${sessionScope.novaConsulta.crmMedico}<br/>
        Data da Consulta: <fmt:formatDate value="${sessionScope.dataConsulta}" pattern="dd/MM/yyyy"/><br/>
        
        <br/>
        <a href="/ProjetoMedico/confirmaConsulta">Confirmar</a>
        <a href="/ProjetoMedico/novaConsulta">Modificar</a>
        <a href="/ProjetoMedico/index.jsp">Cancelar</a>
    </body>
</html>