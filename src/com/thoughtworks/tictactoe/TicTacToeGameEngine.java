package com.thoughtworks.tictactoe;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * 
 * Contain the AI of the game for different Levels of decision making. TicTacToe
 * can be solved by using Three Levels of Logical steps.
 * 
 * First Level
 * 
 * 1) Can he makes three, Make it
 * 
 * 2) Can opponent makes three, Prevent it
 * 
 */
public class TicTacToeGameEngine {

	private static final int NONE_MATCH = -1;
	private static final int SECOND_ROW = 1;
	private static final int FIRST_ROW = 0;

	private static final String TAG = "TicTacToeGameEngine";

	private static final int FIST_ROW_INDEX = 1;
	private static final int SECOND_ROW_INDEX = 3;
	private static final int THIRD_ROW_INDEX = 5;

	private static final int FIRST_ROW_LAST_POS = 3;
	private static final int SECOND_ROW_LAST_POS = 6;
	private static final int EMPTY_BLOCK = 0;

	private static final int BEGIN_BOX = 0;
	private static final int MID_BOX = 1;
	private static final int END_BOX = 2;
	private static final int CENTER_DIAGNAL = 1;

	private static final int BACKWARD_DIAGONAL_ROW = 2;
	private static final int BACKWARD_DIAGONAL_COL = 0;

	private int mBoard[][] = new int[3][3];

	public class Box {
		public int row;
		public int col;

		public Box(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	public int canMakeThree(int player, List<Box> freeBoxes) {

		for (Box freeBox : freeBoxes) {
			mBoard[freeBox.row][freeBox.col] = player;
			final int pos = convertRowColumnToPosition(freeBox.row, freeBox.col);
			if (isWinMove(player, pos))
				return pos;
			mBoard[freeBox.row][freeBox.col] = EMPTY_BLOCK;

		}
		return NONE_MATCH;

	}
	
	
	

	public void updateMove(int player, int position) {

		int row;
		int col;

		int pos[][] = convertPositionToRowAndColumn(position);

		row = pos[0][0];
		col = pos[0][1];

		mBoard[row][col] = player;

		Log.i(TAG, "Player Move: Player ID:" + player + "Move to Position row "
				+ row + " Col " + col);

	}

	public int getMovePosition(int player) {
		List<Box> freeBoxes = new ArrayList<Box>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (mBoard[i][j] == EMPTY_BLOCK) {
					// return convertRowColumnToPosition(i, j);
					freeBoxes.add(new Box(i, j));

				}

			}

		}

		if (freeBoxes.size() > 0) {
			int gameOverPos = canMakeThree(player, freeBoxes);

			if (gameOverPos != NONE_MATCH)
				return gameOverPos;

			int opponent = player == TicTacToe.FIRST_PLAYER ? TicTacToe.SECOND_PLAYER
					: TicTacToe.FIRST_PLAYER;
			gameOverPos = canMakeThree(opponent, freeBoxes);

			if (gameOverPos != NONE_MATCH)
				return gameOverPos;

			return convertRowColumnToPosition(freeBoxes.get(0).row,
					freeBoxes.get(0).col);
		}

		return NONE_MATCH;

	}

	private int convertRowColumnToPosition(int i, int j) {

		if (i == FIRST_ROW)
			return j + 1;

		else if (i == SECOND_ROW)

			return i + j + SECOND_ROW_INDEX;

		else
			return i + j + THIRD_ROW_INDEX;

	}

	private int[][] convertPositionToRowAndColumn(int position) {

		int[][] pos = new int[1][2];
		int row;
		int col;

		if (position <= FIRST_ROW_LAST_POS) {
			row = 0;
			col = position - FIST_ROW_INDEX;
		} else if (position <= SECOND_ROW_LAST_POS) {
			row = 1;
			col = position - (SECOND_ROW_INDEX + row);
		} else {

			row = 2;
			col = position - (THIRD_ROW_INDEX + row);

		}

		pos[0][0] = row;
		pos[0][1] = col;
		return pos;

	}

	public boolean isWinMove(int player, int position) {

		int row;
		int col;

		int pos[][] = convertPositionToRowAndColumn(position);

		row = pos[0][0];
		col = pos[0][1];

		// Check Horizontally

		if (col == BEGIN_BOX) {
			if (mBoard[row][col] == mBoard[row][col + 1]
					&& mBoard[row][col + 1] == mBoard[row][col + 2]) {
				Log.i(TAG, "IS a Win Move: BEGIN_BOX  for player: " + player);
				return true;
			}

		} else if (col == MID_BOX) {
			if (mBoard[row][col] == mBoard[row][col - 1]
					&& mBoard[row][col] == mBoard[row][col + 1]) {
				Log.i(TAG, "IS a Win Move: MID_BOX for player: " + player);
				return true;
			}
		} else if (col == END_BOX) {

			if (mBoard[row][col] == mBoard[row][col - 1]
					&& mBoard[row][col - 1] == mBoard[row][col - 2]) {
				Log.i(TAG, "IS a Win Move: END_BOX  for player: " + player);
				return true;
			}

		}

		// Check vertically

		if (row == BEGIN_BOX) {
			if (mBoard[row][col] == mBoard[row + 1][col]
					&& mBoard[row + 1][col] == mBoard[row + 2][col]) {
				Log.i(TAG, "Is a Win Move: END_BOX vertically for player: "
						+ player);
				return true;
			}

		} else if (row == MID_BOX) {
			if (mBoard[row][col] == mBoard[row - 1][col]
					&& mBoard[row][col] == mBoard[row + 1][col]) {
				Log.i(TAG, "IS a Win Move: END_BOX vertically for player: "
						+ player);
				return true;
			}

		} else if (row == END_BOX) {

			if (mBoard[row][col] == mBoard[row - 1][col]
					&& mBoard[row][col] == mBoard[row - 2][col]) {
				Log.i(TAG, "IS a Win Move: END_BOX vertically for player: "
						+ player);
				return true;
			}

		}

		// check diagonal

		if (row == col) {

			if (CENTER_DIAGNAL == row && col == CENTER_DIAGNAL) {
				if (/*isForwardDiagnalMatches() ||*/ isBackDiagnalMatches())
					return true;

			}
			return isForwardDiagnalMatches();

		}

		// check Backward diagonal

		if (row == BACKWARD_DIAGONAL_ROW && col == BACKWARD_DIAGONAL_COL
				|| col == BACKWARD_DIAGONAL_ROW && row == BACKWARD_DIAGONAL_COL)
			return isBackDiagnalMatches();

		Log.i(TAG, "IS not a Win Move: for player: " + player);

		return false;
	}
	
	
	
	
	
	public int [] getWinMovePosition(int player, int position) {

		int row;
		int col;

		int pos[][] = convertPositionToRowAndColumn(position);

		row = pos[0][0];
		col = pos[0][1];
		
		int winPos [] = new int[3];
		winPos[0] = convertRowColumnToPosition(row, col);

		// Check Horizontally

		if (col == BEGIN_BOX) {
			if (mBoard[row][col] == mBoard[row][col + 1]
					&& mBoard[row][col + 1] == mBoard[row][col + 2]) {
				
				winPos[1] = convertRowColumnToPosition(row, col +1);
				winPos[2] = convertRowColumnToPosition(row, col +2);
				
				return winPos;
			}

		} else if (col == MID_BOX) {
			if (mBoard[row][col] == mBoard[row][col - 1]
					&& mBoard[row][col] == mBoard[row][col + 1]) {
				winPos[1] = convertRowColumnToPosition(row, col -1);
				winPos[2] = convertRowColumnToPosition(row, col +1);
				
				return winPos;
			}
		} else if (col == END_BOX) {

			if (mBoard[row][col] == mBoard[row][col - 1]
					&& mBoard[row][col - 1] == mBoard[row][col - 2]) {
				winPos[1] = convertRowColumnToPosition(row, col -1);
				winPos[2] = convertRowColumnToPosition(row, col -2);
				
				return winPos;
			}

		}

		// Check vertically

		if (row == BEGIN_BOX) {
			if (mBoard[row][col] == mBoard[row + 1][col]
					&& mBoard[row + 1][col] == mBoard[row + 2][col]) {
				winPos[1] = convertRowColumnToPosition(row +1, col);
				winPos[2] = convertRowColumnToPosition(row +2, col);
				
				return winPos;
			}

		} else if (row == MID_BOX) {
			if (mBoard[row][col] == mBoard[row - 1][col]
					&& mBoard[row][col] == mBoard[row + 1][col]) {
				winPos[1] = convertRowColumnToPosition(row - 1, col);
				winPos[2] = convertRowColumnToPosition(row + 1, col);
				
				return winPos;
			}

		} else if (row == END_BOX) {

			if (mBoard[row][col] == mBoard[row - 1][col]
					&& mBoard[row][col] == mBoard[row - 2][col]) {
				winPos[1] = convertRowColumnToPosition(row -1, col);
				winPos[2] = convertRowColumnToPosition(row -2, col);
				
				return winPos;
			}

		}

		// check diagonal

		if (row == col) {

			if (CENTER_DIAGNAL == row && col == CENTER_DIAGNAL) {				
				 if(isBackDiagnalMatches()){
					
					winPos[0] = convertRowColumnToPosition(0, 2);
					winPos[1] = convertRowColumnToPosition(1,1);
					winPos[2] = convertRowColumnToPosition(2, 0);	
					
				}
					
				

			}
			if (isForwardDiagnalMatches() ) {	
				winPos[0] = convertRowColumnToPosition(0, 0);
				winPos[1] = convertRowColumnToPosition(1, 1);
				winPos[2] = convertRowColumnToPosition(2, 2);				
				return winPos;
				} 
		

		}

		// check Backward diagonal

		if (row == BACKWARD_DIAGONAL_ROW && col == BACKWARD_DIAGONAL_COL
				|| col == BACKWARD_DIAGONAL_ROW && row == BACKWARD_DIAGONAL_COL){
			
			winPos[0] = convertRowColumnToPosition(0, 2);
			winPos[1] = convertRowColumnToPosition(1,1);
			winPos[2] = convertRowColumnToPosition(1, 0);	
			
			return winPos;
			
		}

	

		return winPos;
	}
	
	

	private boolean isBackDiagnalMatches() {

		int i = 0;
		int j = 2;

		if (mBoard[i][j] == mBoard[i + 1][j - 1]
				&& mBoard[i + 1][j - 1] == mBoard[i + 2][j - 2])
			return true;
		return false;
	}

	private boolean isForwardDiagnalMatches() {
		int row = 0;
		int col = 0;

		if (mBoard[row][col] == mBoard[row + 1][col + 1]
				&& mBoard[row + 1][col + 1] == mBoard[row + 2][col + 2])
			return true;
		return false;

	}

}
