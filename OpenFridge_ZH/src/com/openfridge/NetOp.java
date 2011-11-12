package com.openfridge;

import java.io.IOException;

enum NetOp {
	PUSH {
		public void doIt(DataObject to) throws IOException {
			to.push();
		}
	},
	UPDATE {
		@Override
		public void doIt(DataObject to) throws IOException {
			to.update();
			
		}
	}, REMOVE {
		@Override
		public void doIt(DataObject to) throws IOException {
			to.remove();
			
		}
	};
	public abstract void doIt(DataObject to) throws IOException;
}