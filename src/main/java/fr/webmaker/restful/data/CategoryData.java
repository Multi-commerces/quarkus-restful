package fr.webmaker.restful.data;

import fr.webmaker.common.BaseResource;

public class CategoryData extends BaseResource {

	private int order = 0;
	
	private boolean visible = true;

	public CategoryData(String id) {
		super(id);
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	

	
}
