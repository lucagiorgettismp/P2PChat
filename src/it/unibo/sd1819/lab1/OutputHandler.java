package it.unibo.sd1819.lab1;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import it.unibo.sd1819.lab1.utils.Message;

public class OutputHandler extends ActiveObject {
	private final List<PeerHandler> peers ;
	// why Blocking ?
	private final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
	
	public OutputHandler (List <PeerHandler> peers ) {
		// why syncronized ?
		this.peers = Collections.synchronizedList(peers);
	}
	
	public void handle (Message message) {
		// add a message to the queue
		messageQueue.add(message);
	}
	
	public void notifyDisconnected(PeerHandler peer) { 
		peers.remove(peer);
		
	}
	
	public void notifyConnected(PeerHandler peer) { 
		peers.add(peer);
	}
	
	protected void loop() throws Exception {
		// get a message from the queue
		// Il metodo poll() dovrebbe prendere e togliere il primo elemento della lista	
		if(!messageQueue.isEmpty()) {
			Message message = messageQueue.poll();

			// TODO: why synchornized ?
			synchronized(peers) {
				for (PeerHandler peer:peers) {
					//send message to peer
					ObjectOutputStream os = new ObjectOutputStream(peer.getSocket().getOutputStream());
					os.writeObject(message);
				}
			}
			System.out.println(message);
		}
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