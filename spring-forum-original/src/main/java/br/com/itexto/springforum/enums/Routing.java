package br.com.itexto.springforum.enums;

public enum Routing {
	 
	ASSUNTO_NEW(Model.ASSUNTO.getName().concat(CommonRoute.SEPARATOR.getBasicRoute()).concat(CommonRoute.NEW.getBasicRoute())), 
	ASSUNTO_SHOW("assunto/show"),
	USUARIO_SHOW("usuario/show");

	 private String route;

	 private Routing(String route) {
	   this.route = route;
	 }

	 public String getRoute() {
	   return route;
	 }

}
