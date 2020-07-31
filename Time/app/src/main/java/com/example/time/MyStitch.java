package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class MyStitch extends AppCompatActivity {
    private static final String APP_ID = "1110586725"; //获取的APPID
    private ShareUiListener mIUiListener;
    private Tencent mTencent;// 新建Tencent实例用于调用分享方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stitch);
        //传入参数APPID
        mTencent = Tencent.createInstance(APP_ID, MyStitch.this.getApplicationContext());
    }
    public void qqShare(View v) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "今天要干的事儿");//分享标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"反正有一大堆事情要做。");//要分享的内容摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://www.baidu.com");//内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://avatar.csdn.net/B/3/F/1_sandyran.jpg");//分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试");//应用名称
        mTencent.shareToQQ(MyStitch.this, params, new ShareUiListener());
    }
    private class ShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            //分享成功
        }
        @Override
        public void onError(UiError uiError) {
            //分享失败
        }
        @Override
        public void onCancel() {
            //分享取消
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mTencent) {
            mTencent.onActivityResult(requestCode, resultCode, data);
        }
    }
}
