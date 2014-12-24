package com.thoughtworks.tictactoe;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/*
 * 
 * @author Sree Kumar A.V
 * 
 * 
 */

public class TicTacToe extends ActionBarActivity implements OnClickListener {

	private static final int END_GAME = -1;
	public static final int FIRST_PLAYER = 1;
	public static final int SECOND_PLAYER = 2;

	private ImageButton mBox1;
	private ImageButton mBox2;
	private ImageButton mBox3;
	private ImageButton mBox4;
	private ImageButton mBox5;
	private ImageButton mBox6;
	private ImageButton mBox7;
	private ImageButton mBox8;
	private ImageButton mBox9;

	private TextView mWinInfo;
	private TextView mWin;
	private TextView mLoss;
	private TextView mTie;

	private Button mRest;
	private Button mPlay;

	private int mCrossImage = R.drawable.cross_green;
	private int mDotImage = R.drawable.circle_gray;

	private int mWinningCrossImage = R.drawable.cross_red;
	private int mWinningDotImage = R.drawable.circle_red;

	private int mBoxId[] = { R.id.button1, R.id.button2, R.id.button3,
			R.id.button4, R.id.button5, R.id.button6, R.id.button7,
			R.id.button8, R.id.button9 };

	private TicTacToeGameEngine mGameEngine;

	private int mCurrentPlayer = FIRST_PLAYER;	
	private boolean mIsGameOver;	
	private Score mScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBox1 = (ImageButton) findViewById(R.id.button1);
		mBox2 = (ImageButton) findViewById(R.id.button2);
		mBox3 = (ImageButton) findViewById(R.id.button3);
		mBox4 = (ImageButton) findViewById(R.id.button4);
		mBox5 = (ImageButton) findViewById(R.id.button5);
		mBox6 = (ImageButton) findViewById(R.id.button6);
		mBox7 = (ImageButton) findViewById(R.id.button7);
		mBox8 = (ImageButton) findViewById(R.id.button8);
		mBox9 = (ImageButton) findViewById(R.id.button9);
		mPlay = (Button) findViewById(R.id.play);

		

		mWinInfo = (TextView) findViewById(R.id.win_info);
		mWin = (TextView) findViewById(R.id.win);
		mTie = (TextView) findViewById(R.id.tie);
		mLoss = (TextView) findViewById(R.id.loss);

		mRest = (Button) findViewById(R.id.reset);
		mGameEngine = new TicTacToeGameEngine();
		resetScore();

		mScore = new Score();
		
		setListeners();

		

	}

	private void setListeners() {
		mBox1.setOnClickListener(this);
		mBox2.setOnClickListener(this);
		mBox3.setOnClickListener(this);
		mBox4.setOnClickListener(this);
		mBox5.setOnClickListener(this);
		mBox6.setOnClickListener(this);
		mBox7.setOnClickListener(this);
		mBox8.setOnClickListener(this);
		mBox9.setOnClickListener(this);
		
		mRest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setListeners();
				mCurrentPlayer = FIRST_PLAYER;								
				mGameEngine = new TicTacToeGameEngine();
				mScore = new Score();
				resetBoxStatus();
				
				resetScore();
				mIsGameOver = false;
			

			}

		});

		mPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mGameEngine = new TicTacToeGameEngine();
				resetBoxStatus();
				mWinInfo.setText("");
				mCurrentPlayer = FIRST_PLAYER;
				mIsGameOver = false;			
				mPlay.setVisibility(View.GONE);

			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

		ImageButton selectedBox = (ImageButton) v;
		selectedBox.setEnabled(false);

		final CharSequence posStr = (CharSequence) v.getTag();
		final int pos = (int) posStr.charAt(0) - 48;

		mGameEngine.updateMove(mCurrentPlayer, pos);
		updateBox(mCurrentPlayer, selectedBox, pos);

		if (!mIsGameOver)
			makeMove(mGameEngine.getMovePosition(mCurrentPlayer),
					mCurrentPlayer);

	}

	private void makeMove(int movePosition, int player) {

		if (movePosition != END_GAME) {

			ImageButton box = (ImageButton) findViewById(mBoxId[movePosition - 1]);

			mGameEngine.updateMove(player, movePosition);
			updateBox(player, box, movePosition);

		} else {
			mWinInfo.setText("Draw match !!!");
			disableBox();
			mScore.tie = mScore.tie + 1;
			setScore();		
			mPlay.setVisibility(View.VISIBLE);

		}

	}

	private void updateBox(int player, ImageButton box, int position) {
		
		if (player == FIRST_PLAYER) {
			box.setBackgroundResource(mCrossImage);
			mCurrentPlayer = SECOND_PLAYER;
		} else {
			box.setBackgroundResource(mDotImage);
			mCurrentPlayer = FIRST_PLAYER;
		}

		box.setEnabled(false);

		if (mGameEngine.isWinMove(player, position)) {
			if (player == FIRST_PLAYER)
				mWinInfo.setText("You Won !!");
			else
				mWinInfo.setText("I Won !!");
			mIsGameOver = true;
			disableBox();
			higlightWinning(mGameEngine.getWinMovePosition(player, position),
					player);

			if (player == FIRST_PLAYER)
				mScore.win = mScore.win + 1;
			else
				mScore.loss = mScore.loss + 1;

			setScore();
			mPlay.setVisibility(View.VISIBLE);

		}

	}

	private void setScore() {

		mWin.setText(getString(R.string.win_, mScore.win));
		mLoss.setText(getString(R.string.loss_, mScore.loss));
		mTie.setText(getString(R.string.tie_, mScore.tie));

	}

	private void resetScore() {

		mWin.setText(getString(R.string.win_, 0));
		mLoss.setText(getString(R.string.loss_, 0));
		mTie.setText(getString(R.string.tie_, 0));
		
		mWinInfo.setText("");

	}

	private void higlightWinning(int[] winMovePosition, int player) {

		for (int i = 0; i < winMovePosition.length; i++) {

			ImageButton box = (ImageButton) findViewById(mBoxId[winMovePosition[i] - 1]);

			if (player == FIRST_PLAYER) {
				box.setBackgroundResource(mWinningCrossImage);

			} else {
				box.setBackgroundResource(mWinningDotImage);

			}

		}

	}

	private void resetBoxStatus() {
		for (int i = 0; i < mBoxId.length; i++) {
			final ImageButton box = (ImageButton) findViewById(mBoxId[i]);
			box.setBackgroundResource(android.R.color.transparent);
			box.setEnabled(true);

		}
	}

	private void disableBox() {
		for (int i = 0; i < mBoxId.length; i++) {
			final ImageButton box = (ImageButton) findViewById(mBoxId[i]);
			box.setEnabled(false);

		}
	}
}
