package com.openfridge;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;

public abstract class DataObject implements Serializable {

	private static final long serialVersionUID = 3805873422054352298L;
	protected String description;
	protected int id;
	protected int userId;

	protected DataObject(String description) {
		this(description,"","");
	}

	protected DataObject(String description, String id, String userId) {
		this(description,defaultInt(id, -1), defaultInt(userId, DataClient.getInstance().getUID()));
	}
	
	protected DataObject(String description, int id, int userId) {
		this.description = URLDecoder.decode(description.trim());
		this.id = id;
		this.userId = userId;
	}

	private static int defaultInt(String i, int defaultInt) {
		try {
			return Integer.parseInt(i.trim());
		} catch (NumberFormatException e) {
			return defaultInt;
		}
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}
	public int getUserId() {
		return userId;
	}

	public void setDescription(String description) {
		this.description = URLDecoder.decode(description.trim());
	}

	public void setId(String id) {
		try {
			setId(Integer.parseInt(id.trim()));
		} catch (NumberFormatException e) {
		}
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return description;
	}
	
	public abstract void push() throws IOException;

	public void update() throws IOException {}

	public abstract void remove() throws IOException;

	@Override
	public boolean equals(Object o) {
		return (o.getClass().equals(getClass()) & ((DataObject)o).getId()==getId());
	}

	@Override
	public int hashCode() {
		return getId();
	}
}
