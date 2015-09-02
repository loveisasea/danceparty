package list;

import java.util.Random;

class Leader {
	final public static int Invitation_Response_Wait_Timeout = 2000;

	final public int id;
	private DanceParty danceParty;
	// 索引是danceType，值是followerId
	public int[] danceConfirmed;

	public Leader(int id) {
		this.id = id;
	}

	public void init(DanceParty danceParty) {
		this.danceParty = danceParty;
		this.danceConfirmed = new int[danceParty.danceTypes.length];
		for (int i = 0; i < this.danceConfirmed.length; i++) {
			this.danceConfirmed[i] = -1;
		}
	}

	private Thread _thread = new Thread() {
		@Override
		public void run() {
			final Random random = new Random();
			// 随机选择舞蹈
			int[] dances = new int[Leader.this.danceConfirmed.length];
			for (int i = 0; i < dances.length; i++) {
				dances[i] = i;
			}
			for (int i = 0; i < dances.length; i++) {
				final int randi = random.nextInt(dances.length - 1);
				final int temp = dances[i];
				dances[i] = dances[randi];
				dances[randi] = temp;
			}
			for (int index = 0; index < dances.length; index++) {
				final int danceTypeId = dances[index];
				if (Leader.this.danceConfirmed[danceTypeId] < 0) {
					// 随机抽取伴舞
					final Follower[] followers = new Follower[danceParty.followers.length];
					for (int i = 0; i < followers.length; i++) {
						followers[i] = danceParty.followers[i];
					}
					for (int i = 0; i < followers.length; i++) {
						final int randi = random.nextInt(followers.length - 1);
						Follower temp = followers[i];
						followers[i] = followers[randi];
						followers[randi] = temp;
					}

					for (Follower follower : followers) {
						final Invitation inv = new Invitation(Leader.this.id, follower.id, danceTypeId);
						synchronized (inv) {
							try {
								follower.addInvitation(inv);
								if (DanceParty.isDebug){
									System.out.println(String.format("%s发送邀请%s...", Leader.this, inv));
								}
								inv.wait(Invitation_Response_Wait_Timeout); 
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						if (inv.result == Invitation.Result_Init) {
							if (DanceParty.isDebug){
								System.out.println(String.format("%s等待超时%s", Leader.this, inv));
							}
						} else if (inv.result == Invitation.Result_Accept) {
							Leader.this.danceConfirmed[inv.danceTypeId] = inv.followerId;
							if (DanceParty.isDebug){
								System.out.println(String.format("%s收到了接受的回应%s====", Leader.this, inv));
							}
							break;
						} else if (inv.result == Invitation.Result_Reject) {
							if (DanceParty.isDebug){
								System.out.println(String.format("%s收到了拒绝邀请的回应%s", Leader.this, inv)); 
							}
						}
					}
				}
			}
		}
	}; 

	@Override
	public String toString() {
		return "<leader:"+this.id+">";
	}

	public void start() {
		this._thread.start();
	}
	
	public void join() throws InterruptedException{
		this._thread.join();
	}

//	public boolean isCompleted() {
//		State state = this._thread.getState();
//		return state == State.TERMINATED;
//	}
}
