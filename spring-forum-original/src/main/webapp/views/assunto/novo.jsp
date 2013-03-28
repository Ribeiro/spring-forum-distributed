<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="row">
<div><h3>${messages}</h3></div>
	<div class="four columns">
		<h4>Novo Template</h4>
	</div>
	<div class="eight columns">
	<sf:form modelAttribute="template" action="salvar">
		<label for="name">Name:<sf:errors path="name" cssClass="erro"/></label>
		<sf:input path="name" class="four"/>
		<label for="content">Content:<sf:errors path="content" cssClass="erro"/></label>
		<sf:textarea path="content"/>
		<label for="result">Result:<sf:errors path="result" cssClass="erro"/></label>
		<sf:textarea path="result"/>
				
		<input type="submit" value="Salvar" class="tiny button success"/>
	</sf:form>
	</div>
</div>