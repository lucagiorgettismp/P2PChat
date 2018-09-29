package it.unibo.sd1819.lab1.controller;

import it.unibo.sd1819.lab1.view.ChatView;

public class Controller implements ControllerView, ControllerModel {

	private final ChatView chatView;
		
	public Controller(ChatView chatView) {
		this.chatView = chatView;
	}

	@Override
	public void sendMessage(String message) {
		this.chatView.addMessage(message);
	}

	@Override
	public void userArrived(String user) {
		this.chatView.addUser(user);
	}

	@Override
	public void userLeft(String user) {
		this.chatView.deleteUser(user);
	}
	
}
