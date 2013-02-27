package br.com.itexto.springforum.enums;

public enum CommonRoute {
	APP_ROOT("/"),
	NEW("new"),
	REDIRECT_TO_APP_ROOT("redirect:/"),
	SEPARATOR("/"),
	SHOW("show")
	;

	 private String commonRoute;

	 private CommonRoute(String commonRoute) {
	   this.commonRoute = commonRoute;
	 }

	 public String getBasicRoute() {
	   return commonRoute;
	 }

}
