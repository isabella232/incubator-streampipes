package de.fzi.cep.sepa.streampipes.codegeneration;

import com.squareup.javapoet.JavaFile;

import de.fzi.cep.sepa.model.impl.graph.SepaDescription;

public abstract class Generator {
	protected String name;
	protected String packageName;
	protected SepaDescription sepa;
	
	public Generator(SepaDescription sepa, String name, String packageName) {
		super();
		this.sepa = sepa;
		this.name = name;
		this.packageName = packageName;
	}

	public abstract JavaFile build();

	
	public SepaDescription getSepa() {
		return sepa;
	}

	public void setSepa(SepaDescription sepa) {
		this.sepa = sepa;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	
	
}