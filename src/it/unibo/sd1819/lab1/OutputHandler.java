package it.unibo.sd1819.lab1;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import it.unibo.sd1819.lab1.utils.Message;

public class OutputHandler extends ActiveObject {
	private final List < PeerHandler > peers ;
	// why Blocking ?
	private final BlockingQueue < Message > messageQueue = new
	LinkedBlockingQueue < >();
	
	public OutputHandler (List < PeerHandler > peers ) {
		// why syncronized ?
		this.peers = Collections . synchronizedList ( peers );
	}
	
	public void handle ( Message message ) {
	// add a message to the queue
	}
	
	public void notifyDisconnected ( PeerHandler peer ) { /* do
	something */ 
		
	}
	
	public void notifyConnected ( PeerHandler peer ) { /* do something
	*/

	}
	protected void loop () throws Exception {
		Message message = new Message("", "");// get a message from the queue
		
		// why synchornized ?
		synchronized (peers ) {
			for ( PeerHandler peer:peers ) {
			// send message to peer
			}
		}
		System.out.println(message);
	}
		
	@Override
	protected void onBegin() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onEnd() {
		// TODO Auto-generated method stub
		
	}
}