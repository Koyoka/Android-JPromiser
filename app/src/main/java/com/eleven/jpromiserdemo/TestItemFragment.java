package com.eleven.jpromiserdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.eleven.jpromiser.blockUI.JBlockUI;
import com.eleven.jpromiser.core.JPromiser;
import com.eleven.jpromiser.core.Promiser;
import com.eleven.rvadapter.extend.AutoTestItemFragment;


public class TestItemFragment extends AutoTestItemFragment {

    private static class MyHandle<T,U> extends Handler{
        JPromiser.Deffered<T, U> mD;
        MyHandle(JPromiser.Deffered<T, U> d){
            mD = d;
        }

        @Override
        public void handleMessage(Message msg) {
            mD.reject();
        }
    }

    private void testBlockUI(){

        JBlockUI.newInstance()
                .$dialog(this, new Promiser() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    protected void aync(Deffered<String, String> d) {

//                        new MyHandle(d).sendEmptyMessageDelayed(1,110000);
                        new Handler(){
                            @Override
                            public void handleMessage(Message msg) {

                                //TODO: do something
                                Toast.makeText(getContext(), "执行长时间操作", Toast.LENGTH_SHORT).show();

                                d.resolve();
                            }
                        }.sendEmptyMessageDelayed(1, 1500);
                    }
                });
    }
    @Override
    protected TestFuncs buildTestFuncs() {

        return new TestFuncs() {
            @Override
            public void testItem() {

                addd("测试Toast", () -> {
                    Intent intent = new Intent(getActivity(), Main2Activity.class);
                    startActivity(intent);
                });

                addd("BlockUI", () -> {
                    testBlockUI();
                });
            }
        };
    }
}