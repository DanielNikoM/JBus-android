package com.DanielNikoMJbusIO.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.DanielNikoMJbusIO.jbus_android.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 12;
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    public BusArrayAdaptor numbersArrayAdapter;
    public ListView numbersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        numbersArrayAdapter = new BusArrayAdaptor(this, Bus.sampleBusList(20),0);

        numbersListView = findViewById(R.id.list);

        numbersListView.setAdapter(numbersArrayAdapter);
        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);
        busListView = findViewById(R.id.list);

        listBus = Bus.sampleBusList(20);
        listSize = listBus.size();

        paginationFooter();
        goToPage(currentPage);

        prevButton.setOnClickListener(v -> {
            currentPage = currentPage != 0? currentPage-1 : 0;
            goToPage(currentPage);
        });
        nextButton.setOnClickListener(v -> {
            currentPage = currentPage != noOfPages -1? currentPage+1 : currentPage;
            goToPage(currentPage);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.person_button) {

            Intent intent = new Intent(this, AboutMeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0:1;
        noOfPages = listSize / pageSize + val;

        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];
        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity = Gravity.CENTER;
        }
        for (int i = 0; i < noOfPages; i++) {
            btns[i]=new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));
            btns[i].setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 150);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }
    private void goToPage(int index) {
        for (int i = 0; i< noOfPages; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }

    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) / 2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    private void viewPaginatedList(List<Bus> listBus, int page) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listBus.size());
        List<Bus> paginatedList = listBus.subList(startIndex, endIndex);
        numbersArrayAdapter = (BusArrayAdaptor) numbersListView.getAdapter();
        numbersArrayAdapter.clear();
        numbersArrayAdapter.addAll(paginatedList);

    }

}