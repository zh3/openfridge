package com.openfridge;

import java.io.IOException;

import org.xml.sax.SAXException;

import android.app.IntentService;
import android.content.Intent;

public class DataClientIntentService extends IntentService {

	  /** 
	   * A constructor is required, and must call the super IntentService(String)
	   * constructor with a name for the worker thread.
	   */
	  public DataClientIntentService() {
	      super("DataClientIntentService");
	  }

	  /**
	   * The IntentService calls this method from the default worker thread with
	   * the intent that started the service. When this method returns, IntentService
	   * stops the service, as appropriate.
	   */
	  @Override
	  protected void onHandleIntent(Intent intent) {
		  try {
			DataClient.getInstance().reloadFoods();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }
	}