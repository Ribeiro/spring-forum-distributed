package br.com.itexto.springforum.controladores;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.itexto.springforum.dao.DAOAssunto;
import br.com.itexto.springforum.dao.DAOTopico;
import br.com.itexto.springforum.dao.jdbc.JdbcAssunto;
import br.com.itexto.springforum.entidades.Assunto;
import br.com.itexto.springforum.entidades.Template;
import br.com.itexto.springforum.entidades.Usuario;
import br.com.itexto.springforum.servicos.StringResourceLoader;

@Controller
public class AssuntoController {
	@Autowired
	private DAOAssunto daoAssunto;
	
	private JdbcAssunto jdbcAssunto;
	
	@Autowired
	private DAOTopico daoTopico;
	
	public AssuntoController(){
		this.jdbcAssunto = new JdbcAssunto();
	}
	
	@RequestMapping("/assunto/{id}")
	public ModelAndView show(Long id) {
		ModelAndView resultado = new ModelAndView();
		
		resultado.addObject("assunto", getDaoAssunto().get(id));
		resultado.addObject("topicos", daoTopico.getTopicosPorAssunto((Assunto)resultado.getModel().get("assunto"), 0, 20));
		resultado.setViewName("assunto/show");
		
		return resultado;
	}
	
	@RequestMapping("/assunto/novo")
	public String novo(Map<String, Object> model) {

		if (model.get("assunto") == null) {
			//Assunto assunto = new Assunto();
			//model.put("assunto", assunto);
			Template template = new Template();
			model.put("template", template);
		}
		return "assunto/novo";
	}
	
//	@RequestMapping(value = "/assunto/salvar", method = RequestMethod.POST)
//	public String salvar(
//			@Valid Assunto assunto,
//			BindingResult bindingResult,
//			HttpSession sessao,
//			HttpServletRequest request) {
//		
//		if (bindingResult.hasErrors()) {
//			Map<String, Object> model = new HashMap<String, Object>();
//			model.put("assunto", assunto);
//			return novo(model);
//		}
//		
//		getJdbcAssunto().persistirJDBC(assunto);
//		return "redirect:/";
//	}
	
	@RequestMapping(value = "/assunto/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(Template template, RedirectAttributes redirectAttrs) {
		java.util.Properties props = new java.util.Properties();
		props.put("resource.loader","srs");
		props.put("srs.resource.loader.public.name","StringResourceLoader");
		props.put("srs.resource.loader.class", "br.com.itexto.springforum.servicos.StringResourceLoader");
		
		ModelAndView resultado = new ModelAndView();
		resultado.setViewName("assunto/novo");
		
		// variavel que sera acessada no template:
				List list = new ArrayList();
				list.add("Item 1");
				list.add("Item 2");
				list.add("Item 3");
				list.add("Item 4");
				list.add("Item 5");
				
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
				ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
				ve.init();


		try {
			Velocity.init(props);
			
			
		} catch (Exception e) {
			System.out.println("Error Initializing TemplateEngine: " + e.getMessage());
			
		}

		try {
			// is there a cleaner way to get a reference to the loader?  
			// I couldn't find one.
			StringResourceLoader srs = (StringResourceLoader) Velocity.getTemplate(StringResourceLoader.EMPTY_TEMPLATE_NAME).getResourceLoader();
			srs.setTemplate(template.getName(), template.getContent().toString());
			
		} catch (Exception e) {
			System.out.println("Error populating StringResourceLoader: " + e.getMessage());
			
		}
				
		org.apache.velocity.Template message = null;

		try {
			message = Velocity.getTemplate(template.getName());
			
			if (message == null) {
				System.out.println("template is null!");
				
			} 

			VelocityContext context = new VelocityContext();	
			context.put("lista", list);
			java.io.StringWriter sw = new java.io.StringWriter(); 
			message.merge(context, sw);
			//File file = new File("/Users/geovannyribeiro/git/spring-forum-distributed/spring-forum-original/" + template.getName() + ".vm", sw.toString());
			FileUtils.writeStringToFile(new File("/Users/geovannyribeiro/git/spring-forum-distributed/spring-forum-original/src/main/java/" + template.getName() + ".vm"), sw.toString());
			//sw = new java.io.StringWriter(); 
			org.apache.velocity.Template t = ve.getTemplate(template.getName() + ".vm");
			t.merge(context, sw);
			template.setResult(new StringBuffer(sw.toString()));
			resultado.addObject("messages", "SUCCESS PROCESSING TEMPLATE ");
			//System.out.println("SUCCESS PROCESSING TEMPLATE => " + sw.toString());
			
						
		} catch (Exception e) {
			//System.out.println("Exception merging template and context:" + e.getMessage());
			//e.printStackTrace();
			//System.err.println("ERROR PROCESSING TEMPLATE =>  Inputed template has errors ! Unable to merge template and context !");
			resultado.addObject("messages", "ERROR PROCESSING TEMPLATE =>  Inputed template has errors ! Unable to merge template and context !");
		}
		
		resultado.addObject("template", template);
		return resultado;
		
	}

	public DAOAssunto getDaoAssunto() {
		return daoAssunto;
	}

	public void setDaoAssunto(DAOAssunto daoAssunto) {
		this.daoAssunto = daoAssunto;
	}

	public JdbcAssunto getJdbcAssunto() {
		return jdbcAssunto;
	}

	public void setJdbcAssunto(JdbcAssunto jdbcAssunto) {
		this.jdbcAssunto = jdbcAssunto;
	}

	
}
