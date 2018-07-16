package com.example.mathieu.test;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Random;

public class MinesweeperGrid implements Serializable
{
    GridButton[][] gridButtons;
    TextView mineLeftText;
    public int xSize = 10;
    public int ySize = 10;
    public int mineCount = 10;
    public int mineLeft = 10;
    public int totalCells = 100;
    public boolean gameCompleted = false;

    public void InitGrid(LinearLayout linearLayout, TextView mineText)
    {
        gridButtons = new GridButton[ySize][xSize];
        mineLeft = mineCount;
        mineLeftText = mineText;
        mineLeftText.setText("Mine Left: " + String.valueOf(mineLeft));
        int id = 0;
        for (int y = 0; y < ySize; y++)
        {
            LinearLayout _tempLayout = (LinearLayout) linearLayout.getChildAt(y);
            for (int x = 0; x < xSize; x++)
            {
                GridButton _tempButton = new GridButton();
                _tempButton.id = id;
                _tempButton.minesweeperGrid = this;
                _tempButton.xPos = x;
                _tempButton.yPos = y;
                _tempButton.button = (Button) _tempLayout.getChildAt(x);
                _tempButton.buttonStatus = GridButton.ButtonStatus.UNREVEALED;
                _tempButton.revealedStatus = GridButton.ButtonStatus.UNREVEALED;
                _tempButton.button.setBackgroundResource(R.drawable.btn_idle);
                _tempButton.setListeners();
                _tempButton.setLongPushListener();
                gridButtons[y][x] = _tempButton;
                id++;
            }
        }
        setMineOnGrid(mineCount);
        setRevealedStatus();
        //RevealAllGrid();
    }

    private void setMineOnGrid(int count)
    {
        for (int k = 0; k < count; )
        {
            Random rand = new Random();
            int randY = rand.nextInt(ySize);
            int randX = rand.nextInt(xSize);
            //Log.d("My Activity", "Random Y: " + String.valueOf(randY) + " - Random X: " + String.valueOf(randX));
            if (!gridButtons[randY][randX].hasMine)
            {
                gridButtons[randY][randX].revealedStatus = GridButton.ButtonStatus.MINE;
                gridButtons[randY][randX].hasMine = true;
                k++;
                totalCells--;
            }
            else
            {
                continue;
            }
        }
    }

    private void setRevealedStatus()
    {
        for (int j = 0; j < ySize; j++)
        {
            for (int i = 0; i < xSize; i++)
            {
                if(gridButtons[j][i].hasMine)
                    continue;
               gridButtons[j][i].ScanButtonAround(GridButton.ButtonStatus.MINE);
            }
        }
    }

    public void RevealAllGrid()
    {
        for (int j = 0; j < ySize; j++)
        {
            for (int i = 0; i < xSize; i++)
            {
                if(gridButtons[j][i].buttonStatus == GridButton.ButtonStatus.FLAG)
                {
                    if(gridButtons[j][i].revealedStatus != GridButton.ButtonStatus.MINE)
                    {
                        gridButtons[j][i].revealedStatus = GridButton.ButtonStatus.X;
                    }
                    else
                        gridButtons[j][i].revealedStatus = GridButton.ButtonStatus.FLAG;
                }
                gridButtons[j][i].ShowRevealedStatus();
                gridButtons[j][i].button.setOnClickListener(null);
            }
        }
    }

    public int RevealedCount()
    {
        int count = 0;
        for (int j = 0; j < ySize; j++)
        {
            for (int i = 0; i < xSize; i++)
            {
                if (gridButtons[j][i].isRevealed)
                {
                    count++;
                }
            }
        }
        return count;
    }
}
