/**
 * 
 */
package com.mad.trafficclient.fragment;

import com.mad.trafficclient.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Fragment_4 extends Fragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fragment_layout04, container, false);
		TextView textview = (TextView) view.findViewById(R.id.textview);
		textview.setText("this is fragment 4");
		return view;
	}

}
