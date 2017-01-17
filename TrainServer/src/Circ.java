public class Circ {
	public static void main(String arg[]) {

		Monitor monitor = new Monitor(2);
		Train train = new Train(monitor);

		(new Server(monitor, train)).start();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
		}
	}
}