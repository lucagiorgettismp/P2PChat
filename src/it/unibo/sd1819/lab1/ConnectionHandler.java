package it.unibo.sd1819.lab1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.sd1819.lab1.utils.TransportAddress;

public class ConnectionHandler extends ActiveObject {
	private final String username ;
	private final int port ;
	private final List < TransportAddress > peerAddresses ;
	
	
	public ConnectionHandler(String username, int port, List<TransportAddress> peerAddresses) {
		this.username = username;
		this.port = port;
		this.peerAddresses = peerAddresses;
	}

	private ServerSocket server ;
	private List < PeerHandler > peers ;
	private OutputHandler outputHandler ;
	private InputHandler inputHandler ;
	
	protected void onBegin () throws Exception {
		peers = peerAddresses . stream ()
		. map (ta -> {
			log (" Trying to connect to %s ... ", ta);
			try { 
				return new Socket (ta. getHost () , ta. getPort ()); 
			}
			catch ( IOException e) {
				log (" Attempt to connect to %s failed ", ta);
				return null ;
			}
		})
		.filter (s -> s != null )
		.peek(s -> log ("Connected to peer %s:%d", 
				s.getInetAddress().getHostAddress(), 
				s.getPort()))
		.map(PeerHandler :: new)
		.collect( Collectors.toList());
		peers = Collections.synchronizedList (peers);
		// start all active objects created so far
		server = new ServerSocket(port);
	}
	
	protected void loop () throws Exception {
		log(" Waiting for incoming connections on port %d ... ", port);
		try {
			Socket socket = new Socket(); // wait for incoming connections
			log(" Connected to peer %s:%d", socket.getInetAddress());
			PeerHandler peer = new PeerHandler ( socket );
			peer.start ();
			peers.add ( peer );
			// inform the output handler
		} catch ( SocketException e) {
			// Socket closed , silently ignores
		}
	}
	
	@Override
	protected void onEnd() {
		// TODO Auto-generated method stub
		
	}
}