package org.icegeneral.rrcallbak;

import android.os.Bundle;
import android.view.View;

import org.icegeneral.rrcallbak.bean.User;
import org.icegeneral.rrcallbak.retrofit.DX168API;
import org.icegeneral.rrcallbak.rxjava.BaseSubscriber;
import org.icegeneral.rrcallbak.rxjava.BindActivityOperator;
import org.icegeneral.rrcallbak.rxjava.DX168Transformer;
import org.icegeneral.rrcallbak.rxjava.RetryWhenNetworkException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        DX168API.get()
                .login("xxx", "xxx")
                .retryWhen(new RetryWhenNetworkException())
                .compose(new DX168Transformer())
                .lift(new BindActivityOperator(this))
                .subscribe(new BaseSubscriber<User>(getApplicationContext()) {
                    @Override
                    public void onNext(User user) {

                    }
                });


        DX168API.get()
                .getRegisterVerifyCode("18888888888")
                .retryWhen(new RetryWhenNetworkException())
                .compose(new DX168Transformer())
                .lift(new BindActivityOperator(this))
                .subscribe(new BaseSubscriber<JSONObject>() {
                    @Override
                    public void onNext(JSONObject jsonObject) {

                    }
                });

    }
}
