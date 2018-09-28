package it.unibo.sd1819.lab1.utils;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	private static final long serialVersionUID = 8111306450579272958L;
	
	private final String sender;
	private final Date timestamp;
	private final String payload;
	
	public Message(String sender, String payload) {
		this(sender, new Date(), payload);
	}
	
	public Message(String sender, Date timestamp, String payload) {
		this.sender = sender;
		this.timestamp = timestamp;
		this.payload = payload;
	}

	public String getSender() {
		return sender;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getPayload() {
		return payload;
	}
	
	@Override
	public String toString() {
		return String.format("[%s | %s] %s", timestamp, sender, payload);
	}
	
}
