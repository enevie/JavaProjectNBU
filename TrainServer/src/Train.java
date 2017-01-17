
public class Train extends Thread {
	Monitor monitor;

	public Train(Monitor monitor) {
		this.monitor = monitor;
	}

	public void run() {
		System.out.println("Train initialized");
		while(true) {
			try {
				sleep(3000);
			} catch (InterruptedException e) {
			}
			monitor.leaveA();
			try {
				sleep(4000);
			} catch (InterruptedException e) {
			}
			monitor.arriveB();
			try {
				sleep(3500);
			} catch (InterruptedException e) {
			}
			monitor.leaveB();
			try {
				sleep(4500);
			} catch (InterruptedException e) {
			}
			monitor.arriveA();
		}
	}
}