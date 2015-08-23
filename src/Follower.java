 
import java.util.LinkedList; 

public class Follower {
	//跟同一个leader最多只能跳支舞
	public static int Max_SameLeader_Dance_Count = 2;

	public int id;
	private DanceParty danceParty;

	// 受到的邀请
	private LinkedList<Invitation> invitations;
	//索引是danceType，值是leaderId
	private int[] confirmedDance;
	//索引是leaderId，值是已经match过的次数count
	private int[] leaderDance;

	public Follower(int id) {
		this.id = id;
	}

	public void init(DanceParty danceParty) {
		this.danceParty = danceParty;
		this.invitations = new LinkedList<Invitation>();
		this.confirmedDance = new int[danceParty.danceTypes.length];
		for (int i = 0; i < this.confirmedDance.length; i++) {
			this.confirmedDance[i] = -1;
		}
		this.leaderDance = new int[danceParty.leaders.length];
//		for (int i = 0; i < this.leaderDance.length; i++) {
//			this.leaderDance[i] = 0;
//		}
	}

	private Thread _thread = new Thread() {
		@Override
		public void run() {
			while (true) {
				while (true) {
					Invitation inv = null;
					synchronized (Follower.this.invitations) {
						inv = Follower.this.invitations.poll();
					}
					if (inv != null) {
						System.out.println(String.format("%s收到邀请%s", Follower.this, inv));
						Follower.this.reply(inv);
					}
					else {
						break;
					}
				}
				try {
					synchronized (Follower.this.invitations) {
						Follower.this.invitations.wait(4000);
					}
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}
		}
	};

	private void reply(Invitation inv) {
		synchronized (inv) {
			if (this.confirmedDance[inv.danceTypeId] >= 0) {
				inv.result = Invitation.Result_Reject;
				System.out.println(String.format("%s拒绝邀请，因为已和%s参与过舞蹈%s", this, danceParty.leaders[this.confirmedDance[inv.danceTypeId]], danceParty.danceTypes[inv.danceTypeId]));
			}
			else if (this.leaderDance[inv.leaderId] >= Max_SameLeader_Dance_Count) {
				inv.result = Invitation.Result_Reject;
				System.out.println(String.format("%s拒绝邀请，因为已接受过%s的%s次邀请", this, danceParty.leaders[inv.leaderId], Max_SameLeader_Dance_Count));
			}
			else {
				inv.result = Invitation.Result_Accept;
				this.confirmedDance[inv.danceTypeId] = inv.leaderId;
				this.leaderDance[inv.leaderId]++;
				System.out.println(String.format("%s接受邀请%s", this, inv));
			}
			inv.notifyAll();
		}
	}

	public void addInvitation(Invitation invitation) {
		synchronized (this.invitations) {
			this.invitations.add(invitation);
			this.invitations.notifyAll();
		}
	}

	@Override
	public String toString() {
		return "<follower:"+this.id+">";
//		return String.format("<follower:%s>", this.id);
	}

	public void start() {
		this._thread.start();
	}

}
