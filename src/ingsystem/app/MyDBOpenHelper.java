package ingsystem.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "SCORE_CARD";

	private Context myContext;

	public MyDBOpenHelper (Context c) {
		super(c, DB_NAME, null, 1);
		myContext = c;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {

		db.beginTransaction();

		try {
			// assetsのsqlにあるDB生成文を実行。
			execSql(db, "sql/bsdb.sql");

			// デバッグ でばっぐ
			// assetsのsqlにあるテストデータを投入。
			execSql(db, "sql/Test_01.sql");

			db.setTransactionSuccessful();

		} catch (IOException e) {
			//TODO 例外をちゃんとする。
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO いまのところは何もしない。

	}

	/**
	 * 引数に指定したファイルのsqlを実行します。
	 *
	 * 式のセパレータとして、「/」を使用
	 * @param db データベース
	 * @param assetsDir assetsフォルダ内のフォルダのパス
	 * @throws IOException
	 */
	private void execSql(SQLiteDatabase db,String targetFile) throws IOException {
		AssetManager as = myContext.getResources().getAssets();
		try {
			String str = readFile(as.open(targetFile));
			for (String sql: str.split("/")){
				db.execSQL(sql);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Original
	 * 引数に指定したassetsフォルダ内のsqlを実行します。
	 * @param db データベース
	 * @param assetsDir assetsフォルダ内のフォルダのパス
	 * @throws IOException

	  	private void execSql(SQLiteDatabase db,String assetsDir) throws IOException {
		AssetManager as = myContext.getResources().getAssets();
		try {
			String files[] = as.list(assetsDir);
			for (int i = 0; i < files.length; i++) {
				String str = readFile(as.open(assetsDir + "/" + files[i]));
				for (String sql: str.split("/")){
					db.execSQL(sql);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 */

	/**
	 * ファイルから文字列を読み込みます。
	 * @param is
	 * @return ファイルの文字列
	 * @throws IOException
	 */
	private String readFile(InputStream is) throws IOException{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

			StringBuilder sb = new StringBuilder();
			String str;
			while((str = br.readLine()) != null){
				sb.append(str +"\n");
			}
			return sb.toString();
		} finally {
			if (br != null) br.close();
		}
	}

}
