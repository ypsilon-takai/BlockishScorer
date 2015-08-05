package ingsystem.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	// valiables
	private MyDBOpenHelper dbMan;        // DB接続用
	private SQLiteDatabase database;      // DB

	public DBManager (Context c) {
		dbMan = new MyDBOpenHelper(c);
        database = dbMan.getReadableDatabase();
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	// 未完成
	public CharSequence[] getGameNameList () {

		CharSequence[] itemlist;
		int[] dbidmap;

		String sql;
		SQLiteCursor cur;
		int datacount;

		sql = "select _ID, DATE, TITLE";
		sql += " from GAME";
		sql += ";";

		cur = (SQLiteCursor)database.rawQuery(sql, null);
		datacount = cur.getCount();

		// 表示用のリストをつくる
		itemlist = new CharSequence[datacount];
		dbidmap = new int[datacount];

		cur.moveToFirst();
		for ( int i = 0; i < datacount; i++) {
			int id = cur.getInt(0);
			String date = cur.getString(1);
			String title = cur.getString(2);
			itemlist[i] = date + " : " + title;

			dbidmap[i] = id;   // とっておく。

			cur.moveToNext();
		}

		// TODO IDマップを返していない。

		return itemlist;
	}

	// 未使用
	public SQLiteCursor getGameNameCur () {

		String sql;
		SQLiteCursor cur;

		sql = "select _ID, DATE || ': ' || TITLE" ;
		sql += " from GAME ";
		sql += ";";

		cur = (SQLiteCursor)database.rawQuery(sql, null);

		return cur;
	}


	public String getLeftTeamName (int gameid) {

		String sql;
		SQLiteCursor cur;

		sql = "select TEAM.NAME ";
		sql += "from TEAM, GAME ";
		sql += "where TEAM._ID = GAME.TEAM_A ";
	    sql += "    and GAME._ID = " + gameid + " ";
		sql += ";";
		cur = (SQLiteCursor)database.rawQuery(sql, null);

		cur.moveToFirst();
		return cur.getString(0);
	}

	public String getRightTeamName (int gameid) {

		String sql;
		SQLiteCursor cur;

		sql = "select TEAM.NAME ";
		sql += "from TEAM, GAME ";
		sql += "where TEAM._ID = GAME.TEAM_B ";
	    sql += "    and GAME._ID = " + gameid + " ";
		sql += ";";
		cur = (SQLiteCursor)database.rawQuery(sql, null);

		cur.moveToFirst();
		return cur.getString(0);
	}

	public SQLiteCursor getPointInfoCur (int gameid) {
		String sql;
		SQLiteCursor cur;

		sql  = "select PERSON.NAME, POINT.SECTION, POINT.TIME, POINT.AMOUNT, TEAM.NAME  ";
		sql += "from POINT left join PERSON on POINT.PERSON = PERSON._ID ";
		sql += "       left join TEAM on PERSON.ORGANIZATION = TEAM._ID ";
		sql += " where  POINT.GAME = " + gameid + " ";
		sql += ";";

		cur = (SQLiteCursor)database.rawQuery(sql, null);

		return cur;

	}

	public CharSequence[] getTeamList (String teamname) {

		CharSequence retlist[];

		String sql;
		SQLiteCursor cur;

		sql  = "select PERSON.NAME  ";
		sql += "from TEAM left join PERSON on TEAM._ID = PERSON.ORGANIZATION ";
		sql += " where TEAM.NAME  = \"" + teamname + "\" ";
		sql += ";";
		cur = (SQLiteCursor)database.rawQuery(sql, null);
		retlist = new CharSequence[cur.getCount()];
		cur.moveToFirst();
		do {

			retlist[cur.getPosition()] = cur.getString(0);

		}while (cur.moveToNext());

		return retlist;
	}


	public int getPersonId (String name) {

		String sql;
		SQLiteCursor cur;

		sql  = "select _ID  ";
		sql += "from PERSON";
		sql += " where NAME  = \"" + name + "\" ";
		sql += ";";
		cur = (SQLiteCursor)database.rawQuery(sql, null);

		cur.moveToFirst();
		return cur.getInt(0);

	}


	public boolean addPointInfo (int gameid,
								 String personname,
								 String section,
								 int time,
								 int amount) {

		int personid = this.getPersonId(personname);

		// DBに保存
    	ContentValues cv = new ContentValues();
    	cv.put("GAME", gameid);
    	cv.put("PERSON", personid);
    	cv.put("SECTION", section);
    	cv.put("TIME", time);
    	cv.put("AMOUNT", amount);

    	database.insert("POINT", null, cv);

		return true;
	}

}
