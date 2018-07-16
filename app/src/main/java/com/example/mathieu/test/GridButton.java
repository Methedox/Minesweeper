package com.example.mathieu.test;

import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GridButton implements Serializable
{
    public int id;
    public MinesweeperGrid minesweeperGrid;
    public Button button;
    public int xPos = 0;
    public int yPos = 0;
    public boolean hasMine = false;
    public boolean isRevealed = false;

    public enum ButtonStatus
    {
        EMPTY(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), FLAG(9), MINE(10), UNREVEALED(11), X(12), ONCLICK(13);

        private int status;

        private static Map<Integer, ButtonStatus> map = new HashMap<Integer, ButtonStatus>();

        static
        {
            for (ButtonStatus statusEnum : ButtonStatus.values())
            {
                map.put(statusEnum.status, statusEnum);
            }
        }

        ButtonStatus(final int status)
        {
            this.status = status;
        }

        public static ButtonStatus valueOf(int status)
        {
            return map.get(status);
        }
    }

    public ButtonStatus buttonStatus;
    public ButtonStatus revealedStatus;


    public void setListeners()
    {
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!minesweeperGrid.gameCompleted)
                {
                    if (buttonStatus != ButtonStatus.FLAG)
                    {
                        if (revealedStatus != ButtonStatus.MINE)
                        {
                            ShowRevealedStatus();
                            isRevealed = true;
                            if (revealedStatus == ButtonStatus.EMPTY) ShowButtonAround();
                            if (minesweeperGrid.RevealedCount() == 90)
                            {
                                minesweeperGrid.mineLeftText.setText("You won!");
                            }
                        } else
                        {
                            minesweeperGrid.RevealAllGrid();
                            minesweeperGrid.mineLeftText.setText("You lost!");
                        }
                    }
                }
            }
        });
    }

    public void setLongPushListener()
    {
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if (!minesweeperGrid.gameCompleted)
                {
                    if (!isRevealed)
                    {
                        if (buttonStatus != ButtonStatus.FLAG)
                        {
                            if (minesweeperGrid.mineLeft > 0) SetFlag();
                        } else
                        {
                            RemoveFlag();
                        }
                        button.setBackgroundResource(getStatusImage(buttonStatus));
                    }
                }
                return true;
            }
        });
    }

    private int getStatusImage(ButtonStatus btnStatus)
    {
        switch (btnStatus)
        {
            case ONE:
                return R.drawable.one;
            case TWO:
                return R.drawable.two;
            case THREE:
                return R.drawable.three;
            case FOUR:
                return R.drawable.four;
            case FIVE:
                return R.drawable.five;
            case SIX:
                return R.drawable.six;
            case SEVEN:
                return R.drawable.seven;
            case EIGHT:
                return R.drawable.eight;
            case MINE:
                return R.drawable.mine;
            case EMPTY:
                return R.drawable.empty;
            case UNREVEALED:
                return R.drawable.unrevealed;
            case FLAG:
                return R.drawable.flag;
            default:
                return R.drawable.x;
        }
    }

    public void ShowRevealedStatus()
    {
        button.setBackgroundResource(getStatusImage(revealedStatus));
    }

    public void ScanButtonAround(ButtonStatus status)
    {
        int adjMineCount = 0;
        if (yPos > 0 && xPos > 0 && minesweeperGrid.gridButtons[yPos - 1][xPos - 1].revealedStatus == status)
            adjMineCount++;
        if (yPos > 0 && minesweeperGrid.gridButtons[yPos - 1][xPos].revealedStatus == status)
            adjMineCount++;
        if (yPos > 0 && xPos < 9 && minesweeperGrid.gridButtons[yPos - 1][xPos + 1].revealedStatus == status)
            adjMineCount++;
        if (xPos > 0 && minesweeperGrid.gridButtons[yPos][xPos - 1].revealedStatus == status)
            adjMineCount++;
        if (xPos < 9 && minesweeperGrid.gridButtons[yPos][xPos + 1].revealedStatus == status)
            adjMineCount++;
        if (yPos < 9 && xPos > 0 && minesweeperGrid.gridButtons[yPos + 1][xPos - 1].revealedStatus == status)
            adjMineCount++;
        if (yPos < 9 && minesweeperGrid.gridButtons[yPos + 1][xPos].revealedStatus == status)
            adjMineCount++;
        if (yPos < 9 && xPos < 9 && minesweeperGrid.gridButtons[yPos + 1][xPos + 1].revealedStatus == status)
            adjMineCount++;

        revealedStatus = ButtonStatus.valueOf(adjMineCount);
    }

    public void ShowButtonAround()
    {

        if (yPos > 0 && xPos > 0 && minesweeperGrid.gridButtons[yPos - 1][xPos - 1].revealedStatus == ButtonStatus.EMPTY)
        {
            if (!minesweeperGrid.gridButtons[yPos - 1][xPos - 1].isRevealed)
            {
                if(minesweeperGrid.gridButtons[yPos - 1][xPos - 1].buttonStatus != ButtonStatus.FLAG)
                {
                    minesweeperGrid.gridButtons[yPos - 1][xPos - 1].ShowRevealedStatus();
                    minesweeperGrid.gridButtons[yPos - 1][xPos - 1].isRevealed = true;
                    minesweeperGrid.gridButtons[yPos - 1][xPos - 1].ShowButtonAround();
                }
            }
        }
        else if (yPos > 0 && xPos > 0)
        {
            minesweeperGrid.gridButtons[yPos - 1][xPos - 1].ShowRevealedStatus();
            minesweeperGrid.gridButtons[yPos - 1][xPos - 1].isRevealed = true;
        }
        if (yPos > 0 && minesweeperGrid.gridButtons[yPos - 1][xPos].revealedStatus == ButtonStatus.EMPTY)
        {
            if (!minesweeperGrid.gridButtons[yPos - 1][xPos].isRevealed)
            {
                if(minesweeperGrid.gridButtons[yPos - 1][xPos].buttonStatus != ButtonStatus.FLAG)
                {
                    minesweeperGrid.gridButtons[yPos - 1][xPos].ShowRevealedStatus();
                    minesweeperGrid.gridButtons[yPos - 1][xPos].isRevealed = true;
                    minesweeperGrid.gridButtons[yPos - 1][xPos].ShowButtonAround();
                }
            }
        }
        else if (yPos > 0)
        {
            if(minesweeperGrid.gridButtons[yPos - 1][xPos].buttonStatus != ButtonStatus.FLAG)
            {
                minesweeperGrid.gridButtons[yPos - 1][xPos].ShowRevealedStatus();
                minesweeperGrid.gridButtons[yPos - 1][xPos].isRevealed = true;

            }
        }
        if (yPos > 0 && xPos < 9 && minesweeperGrid.gridButtons[yPos - 1][xPos + 1].revealedStatus == ButtonStatus.EMPTY)
        {
            if (!minesweeperGrid.gridButtons[yPos - 1][xPos + 1].isRevealed)
            {
                if(minesweeperGrid.gridButtons[yPos - 1][xPos + 1].buttonStatus != ButtonStatus.FLAG)
                {
                    minesweeperGrid.gridButtons[yPos - 1][xPos + 1].ShowRevealedStatus();
                    minesweeperGrid.gridButtons[yPos - 1][xPos + 1].isRevealed = true;
                    minesweeperGrid.gridButtons[yPos - 1][xPos + 1].ShowButtonAround();
                }
            }
        }
        else if (yPos > 0 && xPos < 9)
        {
            if(minesweeperGrid.gridButtons[yPos - 1][xPos + 1].buttonStatus != ButtonStatus.FLAG)
            {
                if(minesweeperGrid.gridButtons[yPos - 1][xPos + 1].buttonStatus != ButtonStatus.FLAG)
                {
                    minesweeperGrid.gridButtons[yPos - 1][xPos + 1].ShowRevealedStatus();
                    minesweeperGrid.gridButtons[yPos - 1][xPos + 1].isRevealed = true;
                }
            }
        }
        if (xPos > 0 && minesweeperGrid.gridButtons[yPos][xPos - 1].revealedStatus == ButtonStatus.EMPTY)
        {
            if (!minesweeperGrid.gridButtons[yPos][xPos - 1].isRevealed)
            {
                if(minesweeperGrid.gridButtons[yPos][xPos - 1].buttonStatus != ButtonStatus.FLAG)
                {
                    minesweeperGrid.gridButtons[yPos][xPos - 1].ShowRevealedStatus();
                    minesweeperGrid.gridButtons[yPos][xPos - 1].isRevealed = true;
                    minesweeperGrid.gridButtons[yPos][xPos - 1].ShowButtonAround();
                }
            }
        }
        else if (xPos > 0)
        {
            if(minesweeperGrid.gridButtons[yPos][xPos - 1].buttonStatus != ButtonStatus.FLAG)
            {
                minesweeperGrid.gridButtons[yPos][xPos - 1].ShowRevealedStatus();
                minesweeperGrid.gridButtons[yPos][xPos - 1].isRevealed = true;
            }
        }
        if (xPos < 9 && minesweeperGrid.gridButtons[yPos][xPos + 1].revealedStatus == ButtonStatus.EMPTY)
        {
            if (!minesweeperGrid.gridButtons[yPos][xPos + 1].isRevealed)
            {
                if(minesweeperGrid.gridButtons[yPos][xPos + 1].buttonStatus != ButtonStatus.FLAG)
                {
                    minesweeperGrid.gridButtons[yPos][xPos + 1].ShowRevealedStatus();
                    minesweeperGrid.gridButtons[yPos][xPos + 1].isRevealed = true;
                    minesweeperGrid.gridButtons[yPos][xPos + 1].ShowButtonAround();
                }
            }
        }
        else if (xPos < 9)
        {
            if(minesweeperGrid.gridButtons[yPos][xPos + 1].buttonStatus != ButtonStatus.FLAG)
            {
                minesweeperGrid.gridButtons[yPos][xPos + 1].ShowRevealedStatus();
                minesweeperGrid.gridButtons[yPos][xPos + 1].isRevealed = true;
            }
        }
        if (yPos < 9 && xPos > 0 && minesweeperGrid.gridButtons[yPos + 1][xPos - 1].revealedStatus == ButtonStatus.EMPTY)
        {
            if (!minesweeperGrid.gridButtons[yPos + 1][xPos - 1].isRevealed)
            {
                if(minesweeperGrid.gridButtons[yPos + 1][xPos - 1].buttonStatus != ButtonStatus.FLAG)
                {
                    minesweeperGrid.gridButtons[yPos + 1][xPos - 1].ShowRevealedStatus();
                    minesweeperGrid.gridButtons[yPos + 1][xPos - 1].isRevealed = true;
                    minesweeperGrid.gridButtons[yPos + 1][xPos - 1].ShowButtonAround();
                }
            }
        }
        else if (yPos < 9 && xPos > 0)
        {
            if(minesweeperGrid.gridButtons[yPos + 1][xPos - 1].buttonStatus != ButtonStatus.FLAG)
            {
                minesweeperGrid.gridButtons[yPos + 1][xPos - 1].ShowRevealedStatus();
                minesweeperGrid.gridButtons[yPos + 1][xPos - 1].isRevealed = true;
            }
        }
        if (yPos < 9 && minesweeperGrid.gridButtons[yPos + 1][xPos].revealedStatus == ButtonStatus.EMPTY)
        {
            if (!minesweeperGrid.gridButtons[yPos + 1][xPos].isRevealed)
            {
                if(minesweeperGrid.gridButtons[yPos + 1][xPos].buttonStatus != ButtonStatus.FLAG)
                {
                    minesweeperGrid.gridButtons[yPos + 1][xPos].ShowRevealedStatus();
                    minesweeperGrid.gridButtons[yPos + 1][xPos].isRevealed = true;
                    minesweeperGrid.gridButtons[yPos + 1][xPos].ShowButtonAround();
                }
            }
        }
        else if (yPos < 9)
        {
            if(minesweeperGrid.gridButtons[yPos + 1][xPos].buttonStatus != ButtonStatus.FLAG)
            {
                minesweeperGrid.gridButtons[yPos + 1][xPos].ShowRevealedStatus();
                minesweeperGrid.gridButtons[yPos + 1][xPos].isRevealed = true;
            }
        }
        if (yPos < 9 && xPos < 9 && minesweeperGrid.gridButtons[yPos + 1][xPos + 1].revealedStatus == ButtonStatus.EMPTY)
        {
            if (!minesweeperGrid.gridButtons[yPos + 1][xPos + 1].isRevealed)
            {
                if(minesweeperGrid.gridButtons[yPos + 1][xPos + 1].buttonStatus != ButtonStatus.FLAG)
                {
                    minesweeperGrid.gridButtons[yPos + 1][xPos + 1].ShowRevealedStatus();
                    minesweeperGrid.gridButtons[yPos + 1][xPos + 1].isRevealed = true;
                    minesweeperGrid.gridButtons[yPos + 1][xPos + 1].ShowButtonAround();
                }
            }
        }
        else if (yPos < 9 && xPos < 9)
        {
            if(minesweeperGrid.gridButtons[yPos + 1][xPos + 1].buttonStatus != ButtonStatus.FLAG)
            {
                minesweeperGrid.gridButtons[yPos + 1][xPos + 1].ShowRevealedStatus();
                minesweeperGrid.gridButtons[yPos + 1][xPos + 1].isRevealed = true;
            }
        }
    }

    private void SetFlag()
    {
        buttonStatus = ButtonStatus.FLAG;
        minesweeperGrid.mineLeft--;
        minesweeperGrid.mineLeftText.setText("Mine Left: " + String.valueOf(minesweeperGrid.mineLeft));
    }

    private void RemoveFlag()
    {
        buttonStatus = ButtonStatus.UNREVEALED;
        minesweeperGrid.mineLeft++;
        minesweeperGrid.mineLeftText.setText("Mine Left: " + String.valueOf(minesweeperGrid.mineLeft));
    }
}
