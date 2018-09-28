package it.unibo.sd1819.lab1;

import java.io.BufferedReader;

import it.unibo.sd1819.lab1.utils.Message;

public class InputHandler extends ActiveObject {
	private final String username ;
	private final OutputHandler outputHandler ;
	private BufferedReader input ;
	public InputHandler(String username , OutputHandler outputHandler)
	{ 
		this.username = username;
		this.outputHandler = outputHandler;
	}
	protected void onBegin () throws Exception {
	// do something here
	}
	protected void loop () throws Exception {
	String payload = // read a line from stdin
	outputHandler.handle (new Message ( username , payload ));
	}
	@Override
	protected void onEnd() {
		// TODO Auto-generated method stub
		
	}
}
