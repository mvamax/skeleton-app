package io.app.web.param;

public class Param {

	private Class<?> clazz;
	
	private Object[] params;

	public Param(Class<?> clazz,Object... params) {
		super();
		this.clazz=clazz;
		this.params = params;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
	
	
	
}
