package it.unibo.sd1819.lab1;

import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

import it.unibo.sd1819.lab1.utils.Message;

public class PeerHandler extends ActiveObject {
	private final Socket peer ;
	private ObjectInputStream peerInputStream ;
		
	public PeerHandler(Socket peer) {
		this.peer = peer;
	}
	
	protected void onBegin () throws Exception {
	// do something here
	}
	
	protected void loop () throws Exception {
		try {
			Message message = new Message("", "");// read a Message obj from the socket ;
			log(message.toString());
		} catch ( StreamCorruptedException e) {
			/* Silently ignores */ 
		}
	}
	
	public Socket getSocket () { 
		return peer ;
	}
	
	@Override
	protected void onEnd() {
		// TODO Auto-generated method stub
		
	}
}