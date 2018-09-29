package it.unibo.sd1819.lab1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;

import it.unibo.sd1819.lab1.utils.Message;

public class PeerHandler extends ActiveObject {
	private final Socket peer ;
	private ObjectInputStream peerInputStream;
	private ObjectOutputStream peerOutputStream;
	
	public PeerHandler(Socket peer) {
		this.peer = peer;
		try {
			this.peerOutputStream = new ObjectOutputStream(this.peer.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void onBegin () throws Exception {
		peerInputStream = new ObjectInputStream(peer.getInputStream());
	}
	
	protected void loop () throws Exception {
		try {
			// read a Message obj from the socket ;
			Message message = (Message) peerInputStream.readObject();
			log(message.toString());
		} catch ( StreamCorruptedException e) {
			/* Silently ignores */
		} catch (SocketException e) {
			//Stop peer thread
			this.stop();
			System.out.println("Peer " + peer.getInetAddress().getHostAddress() + ":" + peer.getPort() + " disconnected");
		}
	}
	
	public ObjectOutputStream getPeerOutputStream() {
		return peerOutputStream;
	}

	public Socket getSocket () { 
		return peer ;
	}
	
	@Override
	protected void onEnd() {
		//Close socket for release resources
		try {
			this.peer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}