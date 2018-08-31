/**
 * 
 */
package com.mad.trafficclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mad.trafficclient.fragment.FragmentHome;
import com.mad.trafficclient.fragment.Fragment_1;
import com.mad.trafficclient.fragment.Fragment_2;
import com.mad.trafficclient.fragment.Fragment_3;
import com.mad.trafficclient.fragment.Fragment_4;



/**
 * @author zhaowei
 *
 */
public class MainActivity extends FragmentActivity 
{
	private SlidingPaneLayout slidepanel;

	private Fragment fragment;
	
	private ListView listView;	
	SimpleAdapter simpleAdapter;
	
    ArrayList<HashMap<String, Object>> actionItems;
    SimpleAdapter actionAdapter;
    
    TextView tV_title;
	ImageView iVSliding;
    ImageView ivHome;
	
	private android.app.FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		slidepanel = (SlidingPaneLayout) findViewById(R.id.slidingPL);

		listView = (ListView)findViewById(R.id.listView1);
		ivHome = (ImageView)findViewById(R.id.imageView_home);
		ivHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setHome();
			}
		});

		iVSliding = (ImageView)findViewById(R.id.imageView_Sliding) ;
		tV_title = (TextView)findViewById(R.id.tv_title);
		tV_title.setText(getString(R.string.app_title));


		iVSliding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (slidepanel.isOpen()) {
					slidepanel.closePane();
				} else {
					slidepanel.openPane();
				}
			}
		});
        
      
        final String[] actionTexts = new String[]{
                getString(R.string.res_left_wodezhanghu),
                getString(R.string.res_left_zhanghuguanli),
                getString(R.string.res_left_honglvdengguanli),
                getString(R.string.res_left_chuanyi),
                getString(R.string.res_left_exit)

        };
        int[]  actionImages = new int[]{
                R.drawable.btn_l_star,
                R.drawable.btn_l_book,
                R.drawable.btn_l_slideshow,
                R.drawable.btn_l_target,
                R.drawable.btn_l_download
        };

        actionItems = new ArrayList<HashMap<String, Object>>();
        actionAdapter = new SimpleAdapter(getApplicationContext(), actionItems, R.layout.left_list_fragment_item,
                new String[]{"action_icon", "action_name"},
                new int[]{R.id.recharge_method_icon, R.id.recharge_method_name});

        for(int i = 0; i < actionImages.length; ++i) {
            HashMap<String, Object> item1 = new HashMap<String, Object>();
            item1.put("action_icon", actionImages[i]);
            item1.put("action_name", actionTexts[i]);
            actionItems.add(item1);
        }
        listView.setAdapter(actionAdapter);        
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_1()).commit();
					tV_title.setText(actionTexts[arg2]);

					break;

				case 1:
					getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_2()).commit();
					tV_title.setText(actionTexts[arg2]);

					break;

				case 2:
					getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_3()).commit();
					tV_title.setText(actionTexts[arg2]);

					break;

				case 3:
					getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_4()).commit();
					tV_title.setText(actionTexts[arg2]);

					break;

				case 4:
					exitAppDialog();

					break;
	
				default:
					break;
				}
				
			}
		});
        
        fragmentManager = getFragmentManager();
        
        setHome();

		

	}
	
	public void setHome() {
		getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new FragmentHome()).commit();
		tV_title.setText(R.string.app_title);

	}
	
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

        int[] listImg = new int[] { R.drawable.icon_trafic, R.drawable.icon_busstop, R.drawable.icon_lamp, R.drawable.icon_parking, R.drawable.icon_light,R.drawable.icon_etc, R.drawable.icon_speed };
        String[] listName = new String[] { "城市交通", "公交站点", "城市环境", "找车位", "红绿灯管理", "ETC管理", "高速车速监控" };
        for (int i = 0; i < listImg.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("itemImage", listImg[i]);
            item.put("itemName", listName[i]);
            items.add(item);
        }
        return items;
    }

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			exitAppDialog();

			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	public void exitAppDialog() {
		new AlertDialog.Builder(this)
				// .setIcon(android.R.drawable.ic_menu_info_detailsp)
				.setTitle("提示").setMessage("你确定要退出吗").setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).setPositiveButton("确定", new DialogInterface.OnClickListener()

		{
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		}).show();

	}





}
