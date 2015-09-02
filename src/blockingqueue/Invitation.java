package blockingqueue;


class Invitation {

	final public static int Result_Init = -1;
	final public static int Result_Reject = 0;
	final public static int Result_Accept = 1;

	final public int leaderId;
	final public int followerId;
	final public int danceTypeId;
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
