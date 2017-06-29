package com.example.badgedemo;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener {
  Button btnHide;
  Button btnAppear;
  boolean isSupportedBade = false;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    btnHide = (Button)this.findViewById(R.id.badge_hide);
    btnAppear = (Button)this.findViewById(R.id.badge_appear);
    btnHide.setOnClickListener(this);
    btnAppear.setOnClickListener(this);
    checkIsSupportedByVersion();
  }
  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    if(v.getId() == R.id.badge_hide){
      handleBadge(0); //0:hide badge
    }
    if(v.getId() == R.id.badge_appear){
      handleBadge(15); //15:testdata
    }
  }

  public void checkIsSupportedByVersion(){
    try {
      PackageManager manager = getPackageManager();
      PackageInfo info = manager.getPackageInfo("com.huawei.android.launcher", 0);
      if(info.versionCode>=63029){
        isSupportedBade = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleBadge(int num){
    if(!isSupportedBade){
      Log.i("badgedemo", "not supported badge!");
      return;
    }
    try{
      Bundle bunlde =new Bundle();
      bunlde.putString("package", "com.example.badgedemo");
      bunlde.putString("class", "com.example.badgedemo.MainActivity");
      bunlde.putInt("badgenumber",num);
      ContentResolver t=this.getContentResolver();
      Bundle result=t.call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_launcher_badge", "", bunlde);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
