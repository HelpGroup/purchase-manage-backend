package com.jiurui.purchase.model;

public class ItemJsonResult<T> extends JsonResult {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private T item;

	public ItemJsonResult(){
		this.status = JsonResult.SUCCESS;
	}
	
	public ItemJsonResult(T item){
		this();
		this.item = item;
	}
	
	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}
}
