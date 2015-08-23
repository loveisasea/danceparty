
public class Invitation {

	public static int Result_Init = -1;
	public static int Result_Reject = 0;
	public static int Result_Accept = 1;

	public int leaderId;
	public int followerId;
	public int danceTypeId;
	public int result = Result_Init;

	public Invitation(int leaderId, int followerId, int danceTypeId) {
		this.leaderId = leaderId;
		this.followerId = followerId;
		this.danceTypeId = danceTypeId;
	}

	@Override
	public String toString() {
		return "<invitation: " + this.leaderId + ", " + this.followerId + ", " + this.danceTypeId +">";
		// return String.format("<invitation: %s, %s, %s>", this.leaderId,
		// this.followerId, this.danceTypeId);
	}

}
