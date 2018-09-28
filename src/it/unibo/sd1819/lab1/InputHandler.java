package it.unibo.sd1819.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		input = new BufferedReader(new InputStreamReader(System.in));
	}
	protected void loop () throws Exception {
		// read a line from stdin
		String payload = input.readLine();
		outputHandler.handle (new Message ( username , payload ));
	}
	@Override
	protected void onEnd() {
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
}
