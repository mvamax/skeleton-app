package io.app.external.domain;

public class ExternalData {
	
	private String id;
	
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ExternalData [id=" + id + ", content=" + content + "]";
	}
	

}
