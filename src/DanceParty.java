 

import java.util.Scanner;

public class DanceParty {
	public DanceType[] danceTypes = new DanceType[] {
			new DanceType(0, "Waltz"),
			new DanceType(1, "Tango"),
			new DanceType(2, "Foxtrot"),
			new DanceType(3, "Quickstep"),
			new DanceType(4, "Rumba"),
			new DanceType(5, "Samba"),
			new DanceType(6, "ChaCha"),
			new DanceType(7, "Jive")
	};
	public Leader[] leaders;
	public Follower[] followers;

	public static void main(String[] args) {
		DanceParty danceParty = new DanceParty();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to this Dancing Party");
		System.out.println("Please Input Leader number:");
		int leaderNum = scanner.nextInt();
		System.out.println("Please Input Follower number:");
		int followerNum = scanner.nextInt();
		scanner.close();
		
		danceParty.init(leaderNum, followerNum);
		danceParty.start();
		danceParty.waitLeaders();
		danceParty.printResult();
		
		System.exit(0);
	}

	

	
	public void init(int leaderNum, int followerNum) {
		this.leaders = new Leader[leaderNum];
		for (int i = 0; i < this.leaders.length; i++) {
			this.leaders[i] = new Leader(i);
			this.leaders[i].init(this);
		}
		this.followers = new Follower[followerNum];
		for (int i = 0; i < this.followers.length; i++) {
			this.followers[i] = new Follower(i);
			this.followers[i].init(this);
		}
	}

	public void start() {
		for (int i = 0; i < this.followers.length; i++) {
			this.followers[i].start();
		}
		for (int i = 0; i < this.leaders.length; i++) {
			this.leaders[i].start();
		}
	}
	
	public void waitLeaders() {
		for (int i = 0; i < this.leaders.length; i++) {
			try {
				this.leaders[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		boolean iscompleted = false;
//		while (!iscompleted) {
//			iscompleted = true;
//			for (int i = 0; i < this.leaders.length; i++) {
//				if (!this.leaders[i].isCompleted()) {
//					iscompleted = false;
//					break;
//				}
//			}
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) { 
//				e.printStackTrace();
//			}
//		}
	}

	
	private void printResult() {
		for (int i = 0; i < this.leaders.length; i++) {
			Leader leader = this.leaders[i];
			System.out.println("Leader :" + i);
			for (int j = 0; j < leader.danceConfirmed.length; j++) {
				int followerId = leader.danceConfirmed[j];
				if (followerId < 0) { 
					System.out.println(String.format("%15s with %3s", this.danceTypes[j].name, "--"));
				}
				else {
					System.out.println(String.format("%15s with %3s", this.danceTypes[j].name, followerId));
				}
			}
			System.out.println();
		}
	}
}

