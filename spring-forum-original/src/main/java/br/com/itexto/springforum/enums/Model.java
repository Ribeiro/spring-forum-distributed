package br.com.itexto.springforum.enums;

public enum Model {
	ASSUNTO("assunto"),
	USUARIO("usuario"),
	TOPICO("topico")
	;

	 private String name;

	 private Model(String name) {
	   this.name = name;
	 }

	 public String getName() {
	   return name;
	 }

}
