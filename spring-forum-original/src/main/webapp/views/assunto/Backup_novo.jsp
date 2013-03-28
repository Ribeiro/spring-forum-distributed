<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="row">
	<div class="four columns">
		<h4>Novo Assunto</h4>
	</div>
	<div class="eight columns">
	<sf:form modelAttribute="assunto" action="salvar" enctype="multipart/form-data">
		<label for="nome">Nome:<sf:errors path="nome" cssClass="erro"/></label>
		<sf:input path="nome" class="four"/>
				
		<input type="submit" value="Salvar" class="tiny button success"/>
	</sf:form>
	</div>
</div>