<%-- 
    Document   : confirmarMedico
    Created on : 12/04/2018, 11:18:47
    Author     : 619680
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projeto Médico WEB</title>
    </head>
    <body>
        <h1>Novo médico</h1>
        Por favor, verifique os dados do médico
        <br/><br/>
        Nome: ${sessionScope.novoMedico.nome}<br/>
        Especilidade: ${sessionScope.novoMedico.especialidade}<br/>
        CRM: ${sessionScope.novoMedico.crm}<br/>
        
        <br/>
        <a href="/ProjetoMedico/admin/gravarMedico">Confirmar</a>
        <a href="/ProjetoMedico/admin/cadastroMedicoForm.jsp">Modificar</a>
        <a href="/ProjetoMedico/admin/index.jsp">Cancelar</a>
    </body>
</html>
