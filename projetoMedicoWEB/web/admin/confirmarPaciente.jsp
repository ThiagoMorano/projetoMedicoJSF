<%-- 
    Document   : ConfirmarPaciente
    Created on : 19/04/2018, 08:32:05
    Author     : 619710
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmar paciente</title>
    </head>
    <body>
        <h1>Novo Paciente</h1>
        <br/>Atenção!! Deseja mesmo adicionar o seguinte paciente?
        <br/><br/>
        Nome:               ${sessionScope.novoPaciente.nome}<br/>
        CPF:                ${sessionScope.novoPaciente.cpf}<br/>
        Data de nascimento: ${sessionScope.novoPaciente.dataDeNascimento}<br/>
        Sexo:               ${sessionScope.novoPaciente.sexo}<br/>
        Telefone:           ${sessionScope.novoPaciente.telefone}<br/><br/>
        
        <a href="GravarPacienteServlet">Confirmar</a>
        <a href="cadastroPaciente.jsp">Modificar</a>
        <a href="index.jsp">Cancelar</a>
    </body>
</html>
