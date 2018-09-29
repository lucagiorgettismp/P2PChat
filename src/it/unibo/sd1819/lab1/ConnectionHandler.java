package it.unibo.sd1819.lab1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.sd1819.lab1.utils.TransportAddress;

public class ConnectionHandler extends ActiveObject {
	private final String username ;
	private final int port ;
	private final List <TransportAddress> peerAddresses ;
	
	
	public ConnectionHandler(String username, int port, List<TransportAddress> peerAddresses) {
		this.username = username;
		this.port = port;
		this.peerAddresses = peerAddresses;
	}

	private ServerSocket server ;
	private List <PeerHandler> peers ;
	private OutputHandler outputHandler ;
	private InputHandler inputHandler ;
	
	protected void onBegin () throws Exception {
		peers = peerAddresses.stream()
		.map(ta -> {
			log (" Trying to connect to %s ... ", ta);
			try { 
				return new Socket(ta.getHost(), ta.getPort()); 
			} catch ( IOException e) {
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
		peers = Collections.synchronizedList(peers);
		
		// start all active objects created so far
		outputHandler = new OutputHandler(peers);
		inputHandler = new InputHandler(username, outputHandler);
		
		outputHandler.start();
		inputHandler.start();

		server = new ServerSocket(port);
	}
	
	protected void loop () throws Exception {
		log(" Waiting for incoming connections on port %d ... ", port);
		//TODO:Try catch da sistemare?
		try {
			Socket socket = this.server.accept(); // wait for incoming connections
			log(" Connected to peer %s:%d", socket.getInetAddress());
			PeerHandler peer = new PeerHandler(socket);
			peer.start();
			peers.add(peer);
			// inform the output handler
			outputHandler.notifyConnected(peer);
		} catch ( /*SocketException*/ Exception e) {
			// Socket closed , silently ignores
			System.out.println("Eccezione Connection Handler loop: " );
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onEnd() {
		// TODO Auto-generated method stub
		
	}
}