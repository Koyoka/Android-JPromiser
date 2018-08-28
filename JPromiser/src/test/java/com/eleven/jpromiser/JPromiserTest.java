package com.eleven.jpromiser;


import com.eleven.jpromiser.core.JPromiser;
import com.eleven.jpromiser.core.JPromiserQueue;
import com.eleven.jpromiser.core.Promiser;

import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;

import static com.eleven.jpromiser.core.JPromiser.DEBUG_SHOW_STACK;
import static org.junit.Assert.assertEquals;

/**
 * Created by 正 on 2017/6/8.
 */

public class JPromiserTest {
    @Test
    public void main() {
        DEBUG_SHOW_STACK = true;
        fooQueue();
        assertEquals(4, 2 + 2);
    }

    private static String Q_KEY_INT = "int";
    private static String Q_KEY_STR = "string";
    private static String Q_KEY_DOU = "double";
    private static String Q_KEY_FLT = "float";
    private static String Q_KEY_BOL = "bool";
    private static String Q_KEY_STR_TEMP = "string_temp";
    private static String Q_KEY_erializable = "erializable";
    private static String Q_KEY_erializableList = "erializableList";
    private static String Q_KEY_MERGE_STR = "mergeStr";
    private  void fooSignQ(){
        JPromiser<String,Integer> p =  new JPromiser<String,Integer>(){
            @Override
            protected void aync(Deffered<String, Integer> d) {
                Integer intVal = null;
                try {
                    intVal = d .getPromiser()
                        .getWallet()
                        .getInt("Q_KEY_INT");
                    print("...getWallet:"+intVal);

                } catch (Exception e) {
                    e.printStackTrace();
                    print("...getWallet:"+e.getMessage());
                }


                d.notify("",1);
                d.resolve("");
                d.reject(2);

                Object runtimeObj =
                        d.getPromiser().getRuntimeListens("001").getInjectObject();
            }
        };

        p.setRunTimeListens("001",new JPromiser.OnRuntimeInjectListens<String>(){

            @Override
            public String getInjectObject() {
                return "";
            }
        });
        p.getWallet().putExtra("Q_KEY_INT",111);
        //region #finally
        p.then(new JPromiser.OnPromiseThen() {
            @Override
            public void onResult(String key, Object obj) {
                print("...then:"+obj);
            }
        }).error(new JPromiser.OnPromiseResult<Integer>() {
            @Override
            public void onResult(Integer result) {
                print("...error:"+result);
            }
        }).success(new JPromiser.OnPromiseResult<String>() {
            @Override
            public void onResult(String result) {
                print("...success"+result);
            }
        });
        //endregion
        p.run();

    }

    private static void fooQueue(){
//        ComStatic.setOnApplicationBuildConfig(new ComStatic.OnApplicationBuildConfig() {
//            @Override
//            public boolean getBuildConfig() {
//                return true;
//            }
//        });
        ArrayList<JPromiser> tasks = new ArrayList<>();

        //region 1.
        {
            Promiser $q = new Promiser() {
                @Override
                protected void aync(Deffered<String, String> d) {
                    String name = "$q_1:";
                    try {
                        Integer intVal =
                            d.getPromiser()
                                    .getWallet()
                                    .getInt(Q_KEY_INT);
//                        String strVal =
//                                d.getPromiser()
//                                .getWallet()
//                                .getString(Q_KEY_STR);
//                        Double doubleVal =
//                                d.getPromiser()
//                                .getWallet()
//                                .getDouble(Q_KEY_DOU);
//                        Float floatVal =
//                                d.getPromiser()
//                                .getWallet()
//                                .getFloat(Q_KEY_FLT);
//                        Boolean boolVal =
//                                d.getPromiser()
//                                .getWallet()
//                                .getBoolean(Q_KEY_BOL);
//                        String strTempVal =
//                                d.getPromiser()
//                                        .getWallet()
//                                        .getString(Q_KEY_STR_TEMP);
//                        MySerializable obj =
//                                (MySerializable) d.getPromiser()
//                                        .getWallet()
//                                        .getSerializable(Q_KEY_erializable);
//                        ArrayList<MySerializable> obj1 =
//                                (ArrayList<MySerializable>) d.getPromiser()
//                                        .getWallet()
//                                        .getSerializable(Q_KEY_erializableList);
                        print(name+"int:"+intVal);
//                        print(name+"str:"+strVal);
//                        print(name+"double:"+doubleVal);
//                        print(name+"float:"+floatVal);
//                        print(name+"boolean:"+boolVal);
//                        print(name+"strTemp:"+strTempVal);
//                        print(name+"MySerializable:"+obj.name);
//                        print(name+"MySerializableList size:"+obj1.size() + " name:" + obj1.get(0).name);
//
//                        d.getPromiser()
//                                .getWallet()
//                                .putExtra(Q_KEY_STR,strVal+":"+name);

                        //region merge
                        String strVal =
                                d.getPromiser()
                                        .getWallet()
                                        .getString(Q_KEY_MERGE_STR);
                        print(name+"mergeStr"+strVal);
                        //endregion

                        //region list

                        ArrayList<MySerializable> ms =
                        getWallet()
                                .getSerializableArray(Q_KEY_erializableList,MySerializable.class);
//                        String json = new Gson().toJson(ms);
//                        print("arraylist"+json);

                        //endregion
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    d.resolve();
                }
            };
            $q.getWallet().putExtra(Q_KEY_MERGE_STR,"合并...");
            tasks.add($q);
        }
        //endregion
        //region 2.
        {
            Promiser $q = new Promiser() {
                @Override
                protected void aync(Deffered<String, String> d) {
                    String name = "$q_2:";
                    try {
                        Integer intVal =
                                d.getPromiser()
                                        .getWallet()
                                        .getInt(Q_KEY_INT);
//                        String strVal =
//                                d.getPromiser()
//                                        .getWallet()
//                                        .getString(Q_KEY_STR);
////                        String strTempVal =
////                                d.getPromiser()
////                                        .getWallet()
////                                        .getString(Q_KEY_STR_TEMP);
                        print(name+"int:"+intVal);
//                        print(name+"str:"+strVal);
//                        print(name+"strTemp:"+strTempVal);
//                        d.getPromiser()
//                                .getWallet()
//                                .putExtra(Q_KEY_STR,strVal+name)
//                                .putExtra(Q_KEY_STR_TEMP,"ahahahah");

                        //region merge
                        String strVal =
                                d.getPromiser()
                                        .getWallet()
                                        .getString(Q_KEY_MERGE_STR);
                        print(name+"mergeStr"+strVal);
                        //endregion
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    d.resolve();
                }
            };
            tasks.add($q);
        }
        //endregion
        //region 3.
        {
            Promiser $q = new Promiser() {
                @Override
                protected void aync(Deffered<String, String> d) {
                    String name = "$q_3:";
                    try {
                        Integer intVal =
                                d.getWallet()
                                .getInt(Q_KEY_INT);
                        String strVal =
                                d.getWallet()
                                .getString(Q_KEY_STR);
                        String strTempVal =
                                d.getWallet()
                                .getString(Q_KEY_STR_TEMP);

                        print(name+"int:"+intVal);
                        print(name+"str:"+strVal);
                        print(name+"strTemp:"+strTempVal);
                        d.getPromiser()
                                .getWallet()
                                .putExtra(Q_KEY_STR,strVal+name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    d.resolve();
                }
            };

//            tasks.add(
//                    JPromiserQueue.newInstance().sync($q)
//            );
        }
        //endregion

        ArrayList<MySerializable> ms = new ArrayList<>();
        ms.add(new MySerializable());
        ms.add(new MySerializable("tttttttttt"));

        Promiser queueQ = JPromiserQueue.newInstance()
                .sync(tasks);
        queueQ.getWallet()
                .putExtra(Q_KEY_INT,100)
                .putExtra(Q_KEY_erializableList,ms);

//                .putExtra(Q_KEY_STR,"eleven")
//                .putExtra(Q_KEY_DOU,1.1)
//                .putExtra(Q_KEY_FLT,2.1f)
//                .putExtra(Q_KEY_BOL,true)
//                .putExtra(Q_KEY_STR_TEMP,"komo")
//                .putExtra(Q_KEY_erializable,new MySerializable())
//                .putExtra(Q_KEY_erializableList,new ArrayList<MySerializable>(){
//                    {
//                        add(new MySerializable("........"));
//                        add(new MySerializable("<<<<<<<<"));
//                        add(new MySerializable(">>>>>>>>>>>"));
//                    }
//
//                });
        queueQ
                .success(new JPromiser.OnWalletResult<String>() {
                    @Override
                    public void onResult(String result) {
//                        DebugWatcher.foo();

                        getPromiser().getWallet().debugPrint();
                        try {
                            Integer intVal  =
                            getPromiser().getWallet()
                                    .getInt(Q_KEY_INT);
                            print("success int:"+intVal);
                            print("success Q_KEY_MERGE_STR:"+
                            getPromiser().getWallet().getString(Q_KEY_MERGE_STR));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                        getPromiser().getWallet().debugPrint();

                    }
                })
                .error(new JPromiser.OnWalletResult<String>() {
                    @Override
                    public void onResult(String result) {
                        try {
                            Integer intVal  =
                                    getPromiser().getWallet()
                                            .getInt(Q_KEY_INT);
                            print("error int:"+intVal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .then(new JPromiser.OnWalletThen() {
                    @Override
                    public void onResult(String key, Object obj) {
                        try {
                            Integer intVal  =
                                    getPromiser().getWallet()
                                            .getInt(Q_KEY_INT);
                            print("then int:"+intVal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .run();

    }

    public static class MySerializable implements Serializable{
        public String name = "___elelel_____";
        public MySerializable(){}
        public MySerializable(String n){
            name = n;
        }
    }
    private static void print(String msg){
        System.out.print("\r\n");
        System.out.print(msg);
    }

    private JPromiser<String,Integer> test(){

        JPromiser<String,Integer> p =  new JPromiser<String,Integer>(){
            @Override
            protected void aync(Deffered<String, Integer> d) {
                try {
                    d
                            .getPromiser()
                            .getWallet()
                            .getInt("");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                d.notify("",1);
                d.resolve("");
                d.reject(2);

                Object runtimeObj =
                        d.getPromiser().getRuntimeListens("001").getInjectObject();
            }
        };

        p.setRunTimeListens("001",new JPromiser.OnRuntimeInjectListens<String>(){

            @Override
            public String getInjectObject() {
                return "";
            }
        });
        return p;
    }
    private void doTest(){
        JPromiser<String,Integer> p = test();

        p.getWallet().putExtra("w001",1);

        p.run(new JPromiser.OnPromiseThen() {
            @Override
            public void onResult(String s,Object obj) {

            }
        },new JPromiser.OnPromiseResult<String>(){
            @Override
            public void onResult(String s) {

            }
        },new JPromiser.OnPromiseResult<Integer>() {
            @Override
            public void onResult(Integer s) {

            }
        });
    }
}
