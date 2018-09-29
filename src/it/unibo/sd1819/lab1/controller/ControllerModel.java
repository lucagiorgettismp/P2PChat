package it.unibo.sd1819.lab1.controller;

public interface ControllerModel {
	/**
	 * Add user to view.
	 * @param user String
	 */
	public void userArrived(String user);
	/**
	 * Remove user from view.
	 * @param user String
	 */
	public void userLeft(String user);
}
