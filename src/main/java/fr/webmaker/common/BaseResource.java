package fr.webmaker.common;

import java.util.Objects;

import fr.webmaker.hateos.annotation.HateosId;

public class BaseResource implements HasId<String> {

	@HateosId
	private String id;

	public BaseResource() {
		super();
	}

	public BaseResource(String id) {
		super();
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseResource other = (BaseResource) obj;
		return Objects.equals(id, other.id);
	}

}
