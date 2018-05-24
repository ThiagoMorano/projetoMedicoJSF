<%-- 
    Document   : cadastroPaciente
    Created on : 12/04/2018, 11:15:43
    Author     : 619710
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
	<title>Cadastro de Paciente - ProjetoMedico</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="/ProjetoMedico/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/css/util.css">
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/css/main.css">
<!--===============================================================================================-->
</head>
    <body>
        <c:if test="${!empty requestScope.mensagens}">
            <ul class="erro">
            <c:forEach items="${requestScope.mensagens}" var="mensagem">
                <li>${mensagem}</li>
            </c:forEach>
            </ul>
            <hr>
        </c:if>
        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-50">
                    <form class="login100-form validate-form" action="CadastrarPacienteServlet" method ="post">
                        <h4 align="center">Digite os dados do paciente a ser cadastrado</h4>
                        <div class="wrap-input100">
                            <input class="input100" type="text" name="nome" placeholder="Nome" value="${sessionScope.novoPaciente.nome}">
                            <span class="focus-input100-1"></span>
                            <span class="focus-input100-2"></span>
                        </div>
                        <div class="wrap-input100 rs1">
                            <input class="input100" name="senha" type="password" value="${sessionScope.novoPaciente.senha}" placeholder="Senha"/>
                            <span class="focus-input100-1"></span>
                            <span class="focus-input100-2"></span>
                        </div>
                        <div class="wrap-input100">                            
                            <input name="cpf" class="input100" type="text" value="${sessionScope.novoPaciente.cpf}" placeholder="CPF"/>
                            <span class="focus-input100-1"></span>
                            <span class="focus-input100-2"></span>
                        </div>
                        <div class="wrap-input100 validate-input">                            
                            <input name="telefone" class="input100" type="tel" value="${sessionScope.novoPaciente.telefone}" placeholder="Telefone"/>
                        </div>                        
                        <div class="wrap-input100">                            
                            <input name="dataDeNascimento" class="input100" type="text" value="${sessionScope.novoPaciente.dataDeNascimento}" placeholder="Data de Nascimento DD/MM/AAAA"/>
                        </div>
                        <div class="wrap-input100">
                            <input name="sexo" class="input100" type="text" value="${sessionScope.novoPaciente.sexo}" placeholder="Sexo"/>
                        </div>
                        <div class="container-login100-form-btn m-t-20">
                            <button class="login100-form-btn" type="submit">
                                    Enviar
                            </button>
                        </div>
                        <div class="container-login100-form-btn m-t-20">
                            <a class="login100-form-btn" href="/ProjetoMedico/admin"> Voltar </a>
                        </div>
                </div>
            </div>
        </div>
    </body>
</html>