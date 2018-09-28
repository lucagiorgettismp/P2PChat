package it.unibo.sd1819.lab1;

public abstract class ActiveObject {
	private final Thread thread = new Thread(this::run);
	private boolean running = true;

	private void run() {
		try {
			onBegin();
			while (running) {
				loop();
			}
		} catch (Exception e) {
			onUncaughtException(e);
		} finally {
			onEnd();
		}
	}

	protected abstract void loop() throws Exception;

	protected abstract void onBegin() throws Exception;

	protected abstract void onEnd();

	protected void onUncaughtException(Exception e) {
		e.printStackTrace();
	}

	public void start() {
		thread.start();
	}

	public void stop() {
		running = false;
		thread.interrupt();
	}

	public void await() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// Do nothing
		}
	}

	protected void log(String format, Object... args) {
		System.out.printf(format + "\n", args);
	}
}
