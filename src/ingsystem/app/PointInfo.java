package ingsystem.app;

class PointInfo {
	private String person;
	private String teamname;
	private String period;
	private int time;
	private int amaunt;

	public PointInfo( String who, String team, String pe, int t, int am) {
		this.person = who;
		this.teamname = team;
		this.period = pe;
		this.time = t;
		this.amaunt = am;
	}

	public String getPerson () {
		return person;
	}

	public String getTeamname () {
		return teamname;
	}

	public String getPeriod () {
		return period;
	}

	public int getTime () {
		return time;
	}

	public int getAmount () {
		return amaunt;
	}
}