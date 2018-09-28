package it.unibo.sd1819.lab1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.sd1819.lab1.utils.TransportAddress;

public class App {

	/**
	 * 
	 * @param args Expected: <your_name> <port> <addr1:port1> <addr2:port2> ...
	 */
	
	public static void main(String[] args) {
		final String username = args[0];
		final int port = Integer.parseInt(args[1]);
		final List<TransportAddress> peerAddresses = Arrays.stream(args)
				.skip(2)
				.map(TransportAddress::parse)
				.collect(Collectors.toList());
		
		//
		
		// do something
		System.out.printf("Hello %s@localhost:%s, your peers are %s\n", username, port, peerAddresses);
	} 

}
