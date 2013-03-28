package br.com.itexto.springforum.servicos;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

/**
 * This resource loader can be used to create templates out of Strings.
 * Strings should be added to it at runtime via its setTemplate method.
 *
 * Created on May 15, 2003
 * @author Moshe Sambol
 */
public class StringResourceLoader extends ResourceLoader {

	public static final String EMPTY_TEMPLATE_NAME = "StringResourceLoader EMPTY TEMPLATE";
	
	class TemplateDetails {
		byte[] bodyBytes;
		long lastModified;
	}
	
	private HashMap<String, TemplateDetails> templates;
		
	/** 
	 * Initialize the template loader with a a resources class.
	 */
	public void init(ExtendedProperties arg0) {
		templates = new HashMap<String, TemplateDetails>();
		
		// this is a hack so that later, the Velocity client code will be able 
		// to retrieve a handle to this loader in order to add templates to it
		//  See main() test method below.
		setTemplate(EMPTY_TEMPLATE_NAME, "");
	}

	/**
	 * Use this method to add a template String to this loader.
	 * If a template is already known with the given name then it will be 
	 * overwritten.
	 */
	public void setTemplate(String name, String body) {
		TemplateDetails td = new TemplateDetails();
		td.bodyBytes = body.getBytes();
		td.lastModified = System.currentTimeMillis();
		templates.put(name, td);
	}
	
	public void removeTemplate(String name) {
		templates.remove(name);
	}
	
	public boolean hasTemplate(String name) {
		return templates.containsKey(name);
	}
	
	/**
	 * Get the InputStream that the Runtime will parse to create a template.
	 */
	public InputStream getResourceStream(String name)
	throws ResourceNotFoundException {
		
		if (templates.containsKey(name)) {
			TemplateDetails td = (TemplateDetails) templates.get(name);
			
			return new ByteArrayInputStream(td.bodyBytes); 
		} else {
			throw new ResourceNotFoundException("Template " + name + " not found.");
		}
	}

	/**
	 * Given a template, check to see if the source of InputStream has been 
	 * modified.
	 */
	public boolean isSourceModified(Resource resource) {
		TemplateDetails td = (TemplateDetails) 
		templates.get(resource.getName());
		if (td == null) {
			return false;
		}
		return (td.lastModified > resource.getLastModified());
	}

	/**
      * Get the last modified time of the InputStream source that was used to 
      * create the template. We need the template here because we have to extract
      * the name of the template in order to locate the InputStream source.
	 */
	public long getLastModified(Resource resource) {
		TemplateDetails td = (TemplateDetails) 
		templates.get(resource.getName());
		if (td == null) {
			return -1;
		}
		return td.lastModified;
	}
	
}
