package com.openfridge;

enum ExpState {
	NEAR (R.id.nearLV), GOOD (R.id.goodLV), EXPIRED (R.id.pastLV);
	
	private final int listViewID;

	private ExpState(int listViewID) {
		this.listViewID = listViewID;
	}

	public int getListViewID() {
		return listViewID;
	}
	
}