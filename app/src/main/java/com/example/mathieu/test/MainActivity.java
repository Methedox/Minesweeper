package com.example.mathieu.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    protected MinesweeperGrid myGrid = new MinesweeperGrid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portrait);
        final LinearLayout mainGrid = (LinearLayout) findViewById(R.id.Grid);
        final TextView mineLeftText = (TextView) findViewById(R.id.mineLeft);

        myGrid.InitGrid(mainGrid, mineLeftText);

        Button newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myGrid.InitGrid(mainGrid, mineLeftText);
            }
        });
    }
}
