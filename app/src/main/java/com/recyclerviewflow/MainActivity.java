package com.recyclerviewflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<FlowModel> datas;
    private Random random;
    String d = "abcdefjhijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ的将搭建接口；安静AIIB安静就爱爱复活卡里的看到";
    private int[] fruits = {R.drawable.apple, R.drawable.banana,
            R.drawable.orange, R.drawable.watermelon,
            R.drawable.pear, R.drawable.grape,
            R.drawable.pineapple, R.drawable.strawberry,
            R.drawable.cherry, R.drawable.mango};
    String tag = "MainActivity";
    private StaggeredGridLayoutManager layoutManager;
    private FlowAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datas = new ArrayList<>();
        random = new Random();


        recyclerView = findViewById(R.id.rv);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//防止跳动，禁止动画
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);//reverselayout是true倒序
        recyclerView.setLayoutManager(layoutManager);

//        CommonAdapter<String> adapter = new CommonAdapter<String>(this,R.layout.textview,datas) {
//
//            @Override
//            protected void convert(ViewHolder holder, String s, int position) {
//                holder.setText(R.id.textView,s);
//            }
//        };
        adapter = new FlowAdapter(this,datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                layoutManager.invalidateSpanAssignments();

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                layoutManager.invalidateSpanAssignments();
//                invokeCheckForGaps();
            }
        });

        prepareData();
        adapter.notifyItemRangeChanged(0,datas.size());

    }

    /**
     * StaggerGridLayoutManager组织item之间gaps存在bug，（大概的意思是，不能完全确定，源码太难看）检查gaps在measure child之前就预确定了。
     * checkForGaps是在onScrollStateChanged里调用的。可见谷歌开发者知道在滚动时由于回收机制会导致gaps错乱（就是顶端留白），然后写了这么个方法去检查弥补
     * 但光在onScrollStateChanged里调用是不够的，滚动时还是会跳动切换，所以onScrolled里也要调用
     *
     * 这都是弥补措施。使用ScaleImageView替代原生ImageView能够更好的解决
     */
    private void invokeCheckForGaps(){
        Class stag = layoutManager.getClass();
        try {
            Method checkForGaps = stag.getDeclaredMethod("checkForGaps",null);
            checkForGaps.setAccessible(true);
            checkForGaps.invoke(layoutManager,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void prepareData(){

        for (int i = 0; i < 100; i++) {
            FlowModel flowModel = new FlowModel();
            flowModel.type = getType();
            flowModel.title = getStr(random.nextInt(100));
            if (flowModel.type == FlowModel.PIC){
                flowModel.resId = fruits[random.nextInt(fruits.length)];
            }
            Log.i(tag, "prepareData: type "+flowModel.type);
            Log.i(tag, "prepareData: title "+flowModel.title);
            Log.i(tag, "prepareData: res "+flowModel.resId);
            datas.add(flowModel);
        }
    }

    private String getStr(int sum){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sum; i++) {
            sb.append(d.charAt(random.nextInt(d.length())));
        }
        return sb.toString();
    }

    private int getType(){
        return random.nextInt(2);
    }
}
