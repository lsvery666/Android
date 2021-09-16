package com.bytedance.gridlayouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private String[] mStrings = {"0", "AC", "退格", "/", "*", "7", "8", "9", "—", "4", "5", "6", "+", "1", "2", "3", "=", "%", "0", "."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = new GridLayout(this);

        // 6行   4列
        gridLayout.setColumnCount(4);
        gridLayout.setRowCount(6);

        for (int i = 0; i < mStrings.length; i++) {
            TextView textView = new TextView(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            if (i == 0) {
                // 设置行列下标， 所占行列  ，比重
                // 对应： layout_row  , layout_rowSpan , layout_rowWeight
                // 如下代表： item坐标（0,0）， 占 1 行，比重为 3 ； 占 4 列，比重为 1
                params.rowSpec = GridLayout.spec(0, 1, 3f);
                params.columnSpec = GridLayout.spec(0, 4, 1f);
                textView.setBackground(getResources().getDrawable(R.color.black));
                textView.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
            } else {
                // 设置行列下标，和比重
                params.rowSpec = GridLayout.spec((i + 3) / 4, 1f);
                params.columnSpec = GridLayout.spec((i + 3) % 4, 1f);

                // 背景
                textView.setBackgroundColor(Color.WHITE);

                // 字体颜色
                if ("AC".equals(mStrings[i])) {
                    textView.setTextColor(Color.parseColor("#f68904"));
                }

                if ("=".equals(mStrings[i])) {
                    textView.setBackgroundColor(Color.parseColor("#f68904"));
                    textView.setTextColor(Color.WHITE);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, MainActivity2.class));
                        }
                    });
                    params.rowSpec = GridLayout.spec((i + 3) / 4, 2, 1f);
                }

                // 居中显示
                textView.setGravity(Gravity.CENTER);

                // 设置边距
                params.setMargins(4, 4, 0, 0);
            }

            // 设置文字
            textView.setText(mStrings[i]);

            // 添加item
            gridLayout.addView(textView, params);
        }

        addContentView(gridLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
