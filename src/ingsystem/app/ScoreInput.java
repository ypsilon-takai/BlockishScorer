package ingsystem.app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ScoreInput extends Activity {

	// valiables
	private DBManager dbMan;        // DB接続ツール

	private String gameName = null;        // ゲームの名前
	private String teamBlueLeft = null;    // 左のチーム
	private CharSequence[] teamBlueMember;
	private int teamBluePoint;
	private String teamRedRight = null;    // 右のチーム
	private CharSequence[] teamRedMember;
	private int teamRedPoint;
	private ArrayList<PointInfo> pointList; //試合の得点保持
	private PointInfoAdapter pinfoAdapter = null;

	private int gtMemberSelectedIdx;  //oops!

	private int gameId = -1;

	private int selectedGameIdOnList; // oops!

	private int[] gameDbIdmap;
	private CharSequence[] itemlist;

	// dialog list
	static final int PICK_GAME_DAILOG  = 1;
	static final int PICK_PERSON_DIALOG_BLUE  = 2;
	static final int PICK_PERSON_DIALOG_RED = 3;
	static final int PICK_SECTION_DIALOG = 4;
	static final int PICK_TIME_DIALOG = 5;


	// view objects
	private TextView tvGameName;

	private TextView tvBlueTeamName;
	private TextView tvBluePoint;
	private Button btBlueGetPoint;

	private TextView tvRedTeamName;
	private TextView tvRedPoint;
	private Button btRedGetPoint;

	private ListView lvPointList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreinput);

        tvGameName = (TextView)findViewById(R.id.GameTitle);

        tvBlueTeamName = (TextView)findViewById(R.id.TeamNameL);
        tvBluePoint = (TextView)findViewById(R.id.PointDispL);

        btBlueGetPoint = (Button)findViewById(R.id.ButtonL);
        btBlueGetPoint.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View view) {
				showDialog(PICK_PERSON_DIALOG_BLUE);
			}
		});

        tvRedTeamName = (TextView)findViewById(R.id.TeamNameR);
        tvRedPoint = (TextView)findViewById(R.id.PointDispR);
        btRedGetPoint = (Button)findViewById(R.id.ButtonR);
        btRedGetPoint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(PICK_PERSON_DIALOG_RED);

			}
		});

        lvPointList = (ListView)findViewById(R.id.GamePointList);

        // get database accessed
        dbMan = new DBManager(this);

        // Point list contents holder
        pointList = new ArrayList<PointInfo>();
		pinfoAdapter = new PointInfoAdapter(this, R.layout.pointlistview, pointList);


    }

    @Override
	protected void onStart () {
    	super.onStart();

    	if (gameName == null) {
    		// ダイアログを表示してチーム名を選択
    		// 各種情報が設定される。               ** 気持ち悪い
    		// TODO エラーハンドリングを追加
    		showDialog(PICK_GAME_DAILOG);
    	}
    }


    @Override
    protected Dialog onCreateDialog (int dialogid) {
    	super.onCreateDialog(dialogid);

    	Dialog retDiag = null;

    	AlertDialog.Builder builder;

    	switch (dialogid) {
    	case PICK_GAME_DAILOG:

    		SQLiteCursor cur;
    		int datacount;

    		cur = dbMan.getGameNameCur();
    		datacount = cur.getCount();

    		// 表示用のリストをつくる
    		itemlist = new CharSequence[datacount];
    		gameDbIdmap = new int[datacount];

    		cur.moveToFirst();
    		for ( int i = 0; i < datacount; i++) {
    			gameDbIdmap[i] = cur.getInt(0);  // とっておく
    			itemlist[i] = cur.getString(1);

    			cur.moveToNext();
    		}

    		// ダイアログ作成
    		builder = new AlertDialog.Builder(ScoreInput.this);
    		builder.setTitle(R.string.choose_game_name);
    		builder.setSingleChoiceItems(itemlist, -1, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				selectedGameIdOnList = which;
    			}
    		});
    		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				setupGameInfo();
    			}
    		});

    		retDiag = builder.create();

    		break;

    	case PICK_PERSON_DIALOG_BLUE:
    		// 得点ダイアログ作成
    		builder = new AlertDialog.Builder(ScoreInput.this);
    		builder.setTitle(R.string.select_getter_name);
    		builder.setSingleChoiceItems(teamBlueMember, -1, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				gtMemberSelectedIdx = which;
    			}
    		});
    		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				setupPoint(PICK_PERSON_DIALOG_BLUE);
    			}
    		});

    		retDiag = builder.create();

    		break;

    	case PICK_PERSON_DIALOG_RED:
    		// 得点ダイアログ作成
    		builder = new AlertDialog.Builder(ScoreInput.this);
    		builder.setTitle(R.string.select_getter_name);
    		builder.setSingleChoiceItems(teamRedMember, -1, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				gtMemberSelectedIdx = which;
    			}
    		});
    		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				setupPoint(PICK_PERSON_DIALOG_RED);
    			}
    		});

    		retDiag = builder.create();

    		break;

    	default:
    		retDiag = null;

    	}

    	return retDiag;
    }

    @Override
    protected void onPrepareDialog (int id, Dialog diag) {
    	super.onPrepareDialog(id, diag);

    	switch (id) {
    	case PICK_GAME_DAILOG:
    	case PICK_PERSON_DIALOG_BLUE:
    	case PICK_PERSON_DIALOG_RED:
    		AlertDialog adiag = (AlertDialog)diag;
    		adiag.getListView().clearChoices();
    	}
    }


    public class PointInfoAdapter extends ArrayAdapter<PointInfo> {

        private ArrayList<PointInfo> items;  //得点のリストが入っている
        private LayoutInflater inflater;     //レイアウトを取り扱うために必要
        int resid;

        public PointInfoAdapter(Context context, int resourceId, ArrayList<PointInfo> items) {
            super(context, resourceId, items);
            this.items = items;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.resid = resourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            //新規に作る場合はnullがわたってくるから、入れ物をつくる。
            View v = convertView;
            if(v == null){
                v = inflater.inflate(resid, null);
            }

            // itemsからデータを取り出し、
            // vから取り出した画面にくっついているViewに値をマッピングする
            PointInfo pinfo = (PointInfo)items.get(position);
//            final PointInfo fPinfo = pinfo;       //サンプルからコピーしたんだけど、理由が不明。 インスタンスの持続？

            ImageView leftImage = (ImageView)v.findViewById(R.id.MarkLeft);
            leftImage.setImageResource(R.drawable.blue_indicator);

            TextView textPeriod = (TextView)v.findViewById(R.id.Period);
            textPeriod.setText(pinfo.getPeriod());

            TextView textTime = (TextView)v.findViewById(R.id.Time);
            textTime.setText(Integer.toString(pinfo.getTime()));

            TextView textName = (TextView)v.findViewById(R.id.Person);
            textName.setText(pinfo.getPerson());

            ImageView rightImage = (ImageView)v.findViewById(R.id.MarkRight);
            rightImage.setImageResource(R.drawable.red_indicator);

            if (pinfo.getTeamname().equals(teamBlueLeft)) {
                rightImage.setVisibility(View.INVISIBLE);
                leftImage.setVisibility(View.VISIBLE);
            } else {
                rightImage.setVisibility(View.VISIBLE);
                leftImage.setVisibility(View.INVISIBLE);
            }

            return v;
        }
    }

    /**
     * 開始時のダイアログで選択されたゲームの情報を元に、各種設定を行なう。
     *
     */
    private void setupGameInfo () {

    	int listindex = selectedGameIdOnList;

		// 試合のDB上のID保存
		gameId = gameDbIdmap[listindex];

		// 試合名の設定
		gameName = itemlist[listindex].toString();

		// 左チーム名設定
		teamBlueLeft = dbMan.getLeftTeamName(gameId);

		// 右チーム名設定
		teamRedRight = dbMan.getRightTeamName(gameId);

		// DB上のその試合のデータを取り出して、pointList に詰め込む
    	SQLiteCursor cur;
    	cur = dbMan.getPointInfoCur(gameId);

    	if (cur.getCount() > 0) {  //得点データがなかったらスキップ
    		teamBluePoint = 0;
    		teamRedPoint = 0;

    		cur.moveToFirst();
    		do {
    			pointList.add(new PointInfo(cur.getString(0),
    					cur.getString(4),
    					cur.getString(1),
    					cur.getInt(2),
    					cur.getInt(3)));

    			if (cur.getString(4).equals(teamBlueLeft)) {
    				teamBluePoint+= 1;
    			} else {
    				teamRedPoint += 1;
    			}

    		}while (cur.moveToNext());

    		// ListViewに設定する。
    		lvPointList.setAdapter(pinfoAdapter);
    	}

		// チームごとのメンバーの一覧取得
		// Blue
		teamBlueMember = dbMan.getTeamList(teamBlueLeft);
		// Red
		teamRedMember = dbMan.getTeamList(teamRedRight);


		// 画面表示を設定する。
		tvBluePoint.setText(String.valueOf(teamBluePoint));
		tvRedPoint.setText(String.valueOf(teamRedPoint));
		tvGameName.setText(gameName);
		tvBlueTeamName.setText(teamBlueLeft);
		tvRedTeamName.setText(teamRedRight);


    }

    /**
     * 得点情報を元に、得点を追加する。
     *
     * @param side : Blue(PICK_PERSON_DIALOG_BLUE) Red(PICK_PERSON_DIALOG_RED)
     */
    private void setupPoint (int side) {

    	String person;
    	String teamname;

    	switch (side) {
    	case PICK_PERSON_DIALOG_BLUE:
    		person = teamBlueMember[gtMemberSelectedIdx].toString();
    		teamname = teamBlueLeft;
    		teamBluePoint++;
    		break;
    	case PICK_PERSON_DIALOG_RED:
    		person = teamRedMember[gtMemberSelectedIdx].toString();
    		teamname = teamRedRight;
    		teamRedPoint++;
    		break;
    	default:
    		person = "unknown";
    		teamname = "unknown";
    		break;
    	}

		// 得点を更新する
		tvBluePoint.setText(String.valueOf(teamBluePoint));
		tvRedPoint.setText(String.valueOf(teamRedPoint));

		// 得点のリストに情報を追加
    	pointList.add(new PointInfo(person,
    				                teamname,
    				                "後半",
    				                35,
    				                1));


		// ListViewに設定する。
    	if (lvPointList.getAdapter() == null) {  //
    		lvPointList.setAdapter(pinfoAdapter);
    	}

    	// 表示の反映
    	pinfoAdapter.notifyDataSetChanged();

    	// 得点をDBに登録
    	dbMan.addPointInfo(gameId, person, "後半", 35, 1);


    }

}

