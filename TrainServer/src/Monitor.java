public class Monitor {
	private boolean onA, onB;
	private int passAB, passBA, passMax;

	public Monitor(int passMax) {
		onA = true;
		onB = false;
		passAB = passBA = 0;
		this.passMax = passMax;
	}

	public synchronized void leaveA() {
		onA = false;
		System.out.println("\t\t\t\tTrain travelling Sofia -> Bourgas");
	}

	public synchronized void arriveB() {
		System.out.println("\t\t\t\tTrain arrive Bourgas");
		onB = true;
		notifyAll();
	}

	public synchronized void leaveB() {
		onB = false;
		System.out.println("\t\t\t\tTrain travelling Bourgas -> Sofia");
	}

	public synchronized void arriveA() {
		System.out.println("\t\t\t\tTrain arrive Sofia");
		onA = true;
		notifyAll();
	}

	public synchronized void taketA() {
		while (!onA || ((passAB + passBA) >= passMax)) {
			System.out.println("\t\t" + Thread.currentThread().getName() + " waiting train");
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		System.out.print(Thread.currentThread().getName() + " get the train;");
		passAB++;
		if (passAB + passBA >= passMax)
			System.out.print(" train is full");
		System.out.println("\t" + passAB + " passengers in the train traveling Sofia -> Bourgas");
	}

	public synchronized void taketB() {
		while (!onB || ((passAB + passBA) >= passMax)) {
			System.out.println("\t\t" + Thread.currentThread().getName() + " waiting train");
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		System.out.print(Thread.currentThread().getName() + " get the train");
		passBA++;
		if (passAB + passBA >= passMax)
			System.out.print(" train is full");
		System.out.println("\t" + passBA + " passengers in the train traveling Bourgas -> Sofia");
	}

	public synchronized void leavetA() {
		while (!onA) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		System.out.print(Thread.currentThread().getName() + " leaving the train and going home");
		if (passBA > 0)
			passBA--;
		System.out.println("\t" + passBA + " passengers Bourgas-> Sofia still in the train");
		notifyAll();
	}

	public synchronized void leavetB() {
		while (!onB) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		System.out.print(Thread.currentThread().getName() + " leaving the train and going home");
		if (passAB > 0)
			passAB--;
		System.out.println("\t" + passAB + " passengers  Sofia -> Bourgas still in the train");
		notifyAll();
	}
}