import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;

class PassangerService extends Thread {

	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private Monitor monitor;
	private CountDownLatch countDownLatch;

	public PassangerService(Socket s, Monitor monitor) throws IOException {
		this.socket = s;
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		this.monitor = monitor;
		this.countDownLatch = new CountDownLatch(1);
		start();
	}

	public void run() {
		boolean directionSofia;
		try {
			String leftToRight = input.readLine();
			if (leftToRight.equalsIgnoreCase("Sofia"))
				directionSofia = true;
			else
				directionSofia = false;
			(new Passenger(monitor, directionSofia, countDownLatch)).start();

			countDownLatch.await();			
			output.println("You arrived!");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}
}