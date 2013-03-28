package br.com.itexto.springforum.entidades;

import java.io.Serializable;

public class Template implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7180342486639344345L;

	private Integer id;
	private String name;
	private StringBuffer content;
	private StringBuffer result;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StringBuffer getContent() {
		return content;
	}

	public void setContent(StringBuffer content) {
		this.content = content;
	}

	public StringBuffer getResult() {
		return result;
	}

	public void setResult(StringBuffer result) {
		this.result = result;
	}

	

}