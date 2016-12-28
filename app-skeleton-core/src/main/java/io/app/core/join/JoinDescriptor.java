package io.app.core.join;

import javax.persistence.criteria.JoinType;

public class JoinDescriptor {
	

	public String attributeName;

	public JoinType joinType;

	public JoinDescriptor(String attributeName,
			JoinType joinType) {
		super();
		this.attributeName = attributeName;
		this.joinType = joinType;
	}



	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}
	
	
}
