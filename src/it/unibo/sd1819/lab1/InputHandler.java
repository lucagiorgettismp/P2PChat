package it.unibo.sd1819.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import it.unibo.sd1819.lab1.utils.Const;
import it.unibo.sd1819.lab1.utils.Message;

public class InputHandler extends ActiveObject {
	private final String username ;
	private final OutputHandler outputHandler ;
	private BufferedReader input ;
	
	public InputHandler(String username , OutputHandler outputHandler) { 
		this.username = username;
		this.outputHandler = outputHandler;
	}
	
	protected void onBegin () throws Exception {
		input = new BufferedReader(new InputStreamReader(System.in));
	}
	
	protected void loop () throws Exception {
		String payload = input.readLine(); // read a line from stdin
		if (payload.equals(Const.EXIT_COMMAND)){
			System.exit(0);
		} else if (!payload.replace(" ", "").isEmpty()) {
			outputHandler.handle(new Message( username, payload));
		}
	}
	
	@Override
	protected void onEnd() {
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
