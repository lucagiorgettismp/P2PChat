package it.unibo.sd1819.lab1;

import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedList;
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
		try {
			//try to add message into queue, if there isn't any free space wait
			messageQueue.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyDisconnected(PeerHandler peer) { 
		peers.remove(peer);
		peer.stop();
	}
	
	public void notifyConnected(PeerHandler peer) { 
		this.peers.add(peer);
	}
	
	protected void loop() throws Exception {
		Message message = this.messageQueue.take();
		List<PeerHandler> toRemove = new LinkedList<PeerHandler>();
		for (PeerHandler peer:peers) {
			try {
				peer.getPeerOutputStream().writeObject(message);
			} catch(SocketException e) {
				toRemove.add(peer);
			}
		}
		toRemove.forEach(p -> this.peers.remove(p));
		System.out.println(message);
	}
		
	@Override
	protected void onBegin() throws Exception {}
	
	@Override
	protected void onEnd() {
		//clean resources
		peers.forEach(PeerHandler::stop);
	}
}