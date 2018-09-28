package it.unibo.sd1819.lab1.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public final class TransportAddress {
	private final InetAddress host;
	private final int port;

	public TransportAddress(InetAddress host, int port) {
		this.host = Objects.requireNonNull(host);
		this.port = port;
	}

	public static TransportAddress of(InetAddress host, int port) {
		return new TransportAddress(host, port);
	}

	public static TransportAddress parse(String string) {
		return parse(string, 8080);
	}

	public static TransportAddress parse(String string, int defaultPort) {
		final int colon = string.lastIndexOf(':');
		try {
			if (colon >= 0) {
				return new TransportAddress(InetAddress.getByName(string.substring(0, colon)),
						Integer.parseInt(string.substring(colon + 1)));
			} else {
				return new TransportAddress(InetAddress.getByName(string), defaultPort);
			}
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public InetAddress getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String toString() {
		return host.getHostAddress() + ":" + port;
	}
}