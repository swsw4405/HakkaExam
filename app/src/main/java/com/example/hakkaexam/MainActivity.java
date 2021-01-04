package com.example.hakkaexam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.RobotUtil;
import com.asus.robotframework.API.SpeakConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;

public class MainActivity extends RobotActivity {
    public final static String TAG = "HakkaExam";
    public final static String DOMAIN = "9C293319D39D4EEBA97B79A62B8FC0BF";
    private static TextView mTextView;
    private static TextView textView1;
    private static TextView option1;
    private static TextView option2;
    private static TextView option3;
    private static TextView option4;
    private static TextView textView2;
    private static Context context;
    private static int answerpos;
    private static int randomnum;
    private static int nowcategory;

    //LayoutInflater inflater = LayoutInflater.from(context);
    //LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    //LayoutInflater inflater = this.getLayoutInflater();
    //final View view4 = inflater.inflate(R.layout.prize, null);


    //華語日常生活
    private static String category1[] = {"自己", "早安", "父母", "再見", "婦人", "自己人", "西洋人", "小孩子", "頭", "口水", "淚水", "眼睛", "耳朵", "肩膀", "背部", "身體", "腎臟", "腳面", "腸子", "鼻子", "膝蓋", "脖子",
            "手掌", "眉毛", "頭髮", "醋", "餅", "蔥", "香蕉", "牛奶", "龍眼", "米漿", "肉類", "涼水", "韭菜", "李子", "豆漿", "醬油", "煮茶", "蔬菜", "橘子", "吃飯", "桃子", "金桔", "飲酒", "南瓜", "花生", "芒果",
            "地瓜", "絲瓜", "鳳梨", "烤乾", "粽子", "大蒜", "熱水", "福菜", "酸菜", "鹼粽", "茄子", "豌豆", "粢粑", "辣椒", "紅龜粄", "吃晚飯", "喝喜酒", "吃中飯", "吃早飯", "甜年糕", "熱開水", "小糕餅", "空心菜",
            "冷開水", "百香果", "大頭菜", "梅干菜", "水煮青菜", "燜烤番薯", "除夕", "中元節", "中秋節", "端午節", "元宵節", "除夕夜", "大年初一", "媳婦", "丈夫", "弟弟", "妹妹", "伯母", "女兒", "姊夫", "祖父", "伯父",
            "叔叔", "姊姊", "姑媽", "爸爸", "姨媽", "哥哥", "嫂嫂", "舅舅", "姨父", "姪子", "兒子", "孫子", "公公", "婆婆", "女婿", "舅媽", "岳父", "岳母", "外甥", "妹夫", "伯祖母", "小舅子", "弟媳婦", "堂兄弟" };
    //華語休閒娛樂
    private static String category2[] = {"流行", "滑雪", "打拳", "談天", "下棋", "賽跑", "拍球", "釣魚", "撞球", "跳繩", "雜耍", "踢球", "躲避球", "猜謎語", "盪鞦韆", "雨衣", "夾克", "袖子", "衣服", "外衣", "袋子", "帽子",
            "靴子", "襪子", "領帶", "指環", "手套", "雨鞋", "皮包", "口罩", "長筒靴", "短筒靴", "毛線衣" };
    //華語時間空間
    private static String category3[] = {"天上", "太陽", "月亮", "水渠", "地震", "池塘", "颱風", "山崩", "下雨", "暖和", "火災", "地上", "小雨", "陰天", "打雷", "站牌", "纜車", "飛機", "火車站","尾班車", "第一班車", "土地公",
            "玉皇大帝","媽祖生日","觀音娘娘", "廚房", "廁所", "家裏", "房子", "茅坑", "毛巾", "臉盆", "桌子", "床鋪", "蚊帳", "梯子", "樓梯", "鄉下", "電燈", "凳子", "臺階", "沙發", "衣櫥", "鑰匙", "鏡子", "手機", "牙刷",
            "水泥", "飯廳", "鐵窗", "冷氣機", "自來水", "漱口杯", "洗衣機", "照相機", "客廳", "上面", "下面", "下午", "外面", "前天", "後天", "後面", "時候", "上方", "冬天", "早晨", "中午", "夏天", "整天", "去年", "今天",
            "明天", "白天", "昨天", "晚上", "一大早", "大後天", "天未亮" };
    //華語動植物
    private static String category4[] = {"牛", "羊", "狗", "馬", "蛇", "魚", "鳥", "象", "蝦", "豬", "貓", "鴨", "雞", "鵝", "蠶", "蒼蠅", "蚊子", "猴子", "蟑螂", "獅子", "翅膀", "蟲子", "螞蟻", "老鷹", "水蟻", "鴿子", "烏鶖",
            "蜻蜓", "蝴蝶", "蝙蝠", "泥鰍", "白鷺鷥", "貓頭鷹", "樹", "玉米", "稻子", "竹子", "艾草", "茼蒿", "芋頭", "菠菜", "柚子", "梅子", "瓠瓜", "麥子", "芝麻", "萵苣", "桐花", "黃豆", "龍眼樹", "雞冠花" };
    //客語日常生活
    private static String category5[] = {"自家", "恁早", "爺哀", "正來尞", "婦人家", "自家人", "阿琢仔", "細人仔", "頭那", "口涎", "目汁", "目珠", "耳公", "肩頭", "背囊", "圓身", "腰仔", "腳盤", "腸仔", "鼻公", "膝頭",
            "頸根", "手巴掌", "目眉毛", "頭那毛", "酸醋", "餅仔", "蔥仔", "弓蕉", "牛乳", "牛眼", "米乳", "肉氣", "冷水", "快菜", "李仔", "豆乳", "豆油", "炙茶", "青菜", "柑仔", "食飯", "桃仔", "桔仔", "啉酒", "番瓜",
            "番豆", "番檨", "番薯", "菜瓜", "黃梨", "熇燥", "粽仔", "蒜仔", "燒水", "覆菜", "鹹菜", "焿粽", "吊菜仔", "荷蘭豆", "粢粑仔", "辣椒仔", "紅粄", "食夜", "食酒", "食晝", "食朝", "甜粄", "滾水", "糕仔", "蕹菜",
            "冷滾水", "時計果", "結頭菜", "鹹菜乾", "煠菜", "煨番薯", "年三十", "七月半", "八月半", "五月節", "正月半", "年三十暗晡", "年初一", "心臼", "老公", "老弟", "老妹", "伯姆", "妹仔", "姊丈", "阿公", "阿伯",
            "阿叔", "阿姊", "阿姑", "阿爸", "阿姨", "阿哥", "阿嫂", "阿舅", "姨丈", "姪仔", "倈仔", "孫仔", "家官", "家娘", "婿郎", "舅姆", "丈人老", "丈人哀", "外甥仔", "老妹婿", "伯婆", "妻舅仔", "老弟心臼", "叔伯兄弟"};
    //客語休閒娛樂
    private static String category6[] = {"時行", "溜雪", "打拳頭", "打嘴鼓", "行棋仔", "走相逐", "拍球仔", "釣魚仔", "揰球仔", "跳索仔", "撮把戲", "踢球仔", "閃避球", "揣令仔", "吊晃槓仔", "水衣", "尖把", "衫袖",
            "衫褲", "面衫", "袋仔", "帽仔", "靴仔", "襪仔", "內固帶", "手指落", "手落仔", "水靴筒", "皮包仔", "嘴落仔", "靴筒", "短靴筒", "膨線衫"};
    //客語時間空間
    private static String category7[] = {"天頂", "日頭", "月光", "水圳", "地動", "陂塘", "風搓", "崩山", "落雨", "燒暖", "火燒屋", "地泥項", "雨毛仔", "烏陰天", "響雷公", "車牌", "流籠", "飛行機", "火車頭", "尾枋車",
            "頭枋車", "伯公", "天公", "媽祖生", "觀音娘", "灶下", "便所", "屋下", "屋仔", "屎缸", "面帕", "面盆", "桌仔", "眠床", "眠帳", "梯仔", "梯排", "莊下", "電火", "凳仔", "碫仔", "膨凳", "簞笥", "鎖匙", "鏡仔",
            "手機仔", "牙搓仔", "紅毛泥", "食飯間", "鐵窗仔", "冷氣", "水道水", "牙确仔", "洗衫機", "翕相機", "廳下", "上背", "下背", "下晝", "外背", "前日", "後日", "後背", "時節", "頂高", "寒天", "朝晨", "當晝",
            "熱天", "歸日", "舊年", "今晡日", "天光日", "日時頭", "昨晡日", "暗晡頭", "打早", "大後日", "天吂光"};
    //客語動植物
    private static String category8[] = {"牛仔", "羊仔", "狗仔", "馬仔", "蛇哥", "魚仔", "鳥仔", "象仔", "蝦公", "豬仔", "貓仔", "鴨仔", "雞仔", "鵝仔", "蠶仔", "烏蠅", "蚊仔", "猴仔", "黃蚻", "獅仔", "翼胛", "蟲仔",
            "蟻公", "鷂婆", "大水蟻", "月鴿仔", "阿啾箭", "揚尾仔", "揚蝶仔", "蝠婆仔", "鰗鰍仔", "白鶴仔", "貓頭鳥", "樹仔", "包粟", "禾仔", "竹仔", "艾仔", "艾菜", "芋仔", "角菜", "柚仔", "梅仔", "瓠仔", "麥仔",
            "麻仔", "蕒仔", "油桐花", "黃豆仔", "牛眼樹", "雞公髻花",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        final View view1 = inflater.inflate(R.layout.start, null);
        final View view2 = inflater.inflate(R.layout.sound_category, null);
        final View view3 = inflater.inflate(R.layout.text_category, null);
        final View view4 = inflater.inflate(R.layout.prize, null);
        final View view5 = inflater.inflate(R.layout.question, null);
        final View view6 = inflater.inflate(R.layout.answer, null);
        final View view7 = inflater.inflate(R.layout.sound_wait_answer, null);
        textView1 = (TextView) view5.findViewById(R.id.textView1);
        option1 =(TextView) view6.findViewById(R.id.option1);
        option2 =(TextView) view6.findViewById(R.id.option2);
        option3 =(TextView) view6.findViewById(R.id.option3);
        option4 =(TextView) view6.findViewById(R.id.option4);
        textView2 = (TextView) view7.findViewById(R.id.answer_hint);
        setContentView(view1);
        //語音版
        Button button1 = (Button) view1.findViewById(R.id.btn1);
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                /*try {
                    Uri uri = Uri.parse("https://hakka2speech.smarthome.csie.nuu.edu.tw/?text=歡迎光臨，我是聯大院寶，歡迎來到大學市集，挑戰折價劵大猜謎");
                    MediaPlayer player = new MediaPlayer();
                    player.reset();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(context, uri);
                    player.prepare();
                    player.start();
                    player.setVolume(1.0f, 1.0f);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }*/
                setContentView(view2);
            }
        });

        //文字版
        Button button2 = (Button) view1.findViewById(R.id.btn2);
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                try {
                    Uri uri = Uri.parse("https://hakka2speech.smarthome.csie.nuu.edu.tw/?text=歡迎光臨，我是聯大院寶，歡迎來到大學市集，挑戰折價劵大猜謎");
                    MediaPlayer player = new MediaPlayer();
                    player.reset();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(context, uri);
                    player.prepare();
                    player.start();
                    player.setVolume(1.0f, 1.0f);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                setContentView(view3);
            }
        });

        //語音版_題型日常生活
        Button button3 = (Button) view2.findViewById(R.id.category1);
        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                nowcategory = 1;
                randomnum = (int)(Math.random() * 117);
                Log.d(TAG, "題型: " + nowcategory);
                Log.d(TAG, "目前答案: " + category1[randomnum]);
                try {
                    Uri uri = Uri.parse("https://hakka2speech.smarthome.csie.nuu.edu.tw/?text=" + "請問客語的" + category1[randomnum] + "是什麼意思?");
                    MediaPlayer player = new MediaPlayer();
                    player.reset();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(context, uri);
                    player.prepare();
                    player.start();
                    player.setVolume(1.0f, 1.0f);
                    Thread.sleep(5000);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                setContentView(view7);
                robotAPI.robot.speakAndListen("",new SpeakConfig().waitFactor(5));
                //robotAPI.robot.speakAndListen("",new SpeakConfig().MODE_FOREVER);
            }
        });
        //語音版_題型休閒娛樂
        Button button4 = (Button) view2.findViewById(R.id.category2);
        button4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                nowcategory = 2;
                randomnum = (int)(Math.random() * 33);
                Log.d(TAG, "題型: " + nowcategory);
                Log.d(TAG, "目前答案: " + category2[randomnum]);
                try {
                    Uri uri = Uri.parse("https://hakka2speech.smarthome.csie.nuu.edu.tw/?text=" + "請問客語的" + category2[randomnum] + "是什麼意思?");
                    MediaPlayer player = new MediaPlayer();
                    player.reset();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(context, uri);
                    player.prepare();
                    player.start();
                    player.setVolume(1.0f, 1.0f);
                    Thread.sleep(5000);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                setContentView(view7);
                robotAPI.robot.speakAndListen("",new SpeakConfig().waitFactor(5));
                //robotAPI.robot.speakAndListen("",new SpeakConfig().MODE_FOREVER);
            }
        });
        //語音版_題型時間空間
        Button button5 = (Button) view2.findViewById(R.id.category3);
        button5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                nowcategory = 3;
                randomnum = (int)(Math.random() * 79);
                Log.d(TAG, "題型: " + nowcategory);
                Log.d(TAG, "目前答案: " + category3[randomnum]);
                try {
                    Uri uri = Uri.parse("https://hakka2speech.smarthome.csie.nuu.edu.tw/?text=" + "請問客語的" + category3[randomnum] + "是什麼意思?");
                    MediaPlayer player = new MediaPlayer();
                    player.reset();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(context, uri);
                    player.prepare();
                    player.start();
                    player.setVolume(1.0f, 1.0f);
                    Thread.sleep(5000);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                setContentView(view7);
                robotAPI.robot.speakAndListen("",new SpeakConfig().waitFactor(5));
                //robotAPI.robot.speakAndListen("",new SpeakConfig().MODE_FOREVER);
            }
        });
        //語音版_題型動植物
        Button button6 = (Button) view2.findViewById(R.id.category4);
        button6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                nowcategory = 4;
                randomnum = (int)(Math.random() * 51);
                Log.d(TAG, "題型: " + nowcategory);
                Log.d(TAG, "目前答案: " + category4[randomnum]);
                try {
                    Uri uri = Uri.parse("https://hakka2speech.smarthome.csie.nuu.edu.tw/?text=" + "請問客語的" + category4[randomnum] + "是什麼意思?");
                    MediaPlayer player = new MediaPlayer();
                    player.reset();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(context, uri);
                    player.prepare();
                    player.start();
                    player.setVolume(1.0f, 1.0f);
                    Thread.sleep(5000);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                setContentView(view7);
                robotAPI.robot.speakAndListen("",new SpeakConfig().waitFactor(5));
                //robotAPI.robot.speakAndListen("",new SpeakConfig().MODE_FOREVER);
            }
        });
        //語音版_重新聆聽
        Button button15 = (Button) view7.findViewById(R.id.relisten);
        button15.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                robotAPI.robot.speakAndListen("",new SpeakConfig().MODE_FOREVER);
            }
        });
        //語音版_提示
        Button button16 = (Button) view7.findViewById(R.id.hint);
        button16.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nowcategory==1)
                {
                    textView2.setText("答案提示"+category5[randomnum]);
                }
                if(nowcategory==2)
                {
                    textView2.setText("答案提示"+category6[randomnum]);
                }
                if(nowcategory==3)
                {
                    textView2.setText("答案提示"+category7[randomnum]);
                }
                if(nowcategory==4)
                {
                    textView2.setText("答案提示"+category8[randomnum]);
                }
            }
        });
        //文字版_題型日常生活
        Button button7 = (Button) view3.findViewById(R.id.category1);
        button7.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                nowcategory = 1;
                randomnum = (int)(Math.random() * 117);
                Log.d(TAG, "題型: " + nowcategory);
                Log.d(TAG, "目前答案: " + category1[randomnum]);
                setContentView(view5);
                textView1.setText("請問客語个「" + category5[randomnum]  + "」係甚麼意思");
            }
        });

        //文字版_題型休閒娛樂
        Button button8 = (Button) view3.findViewById(R.id.category2);
        button8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                nowcategory = 2;
                randomnum = (int)(Math.random() * 33);
                Log.d(TAG, "題型: " + nowcategory);
                Log.d(TAG, "目前答案: " + category2[randomnum]);
                textView1.setText("請問客語个「" + category6[randomnum]  + "」係甚麼意思");
                setContentView(view5);
            }
        });
        //文字版_題型時間空間
        Button button9 = (Button) view3.findViewById(R.id.category3);
        button9.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                nowcategory = 3;
                randomnum = (int)(Math.random() * 79);
                Log.d(TAG, "題型: " + nowcategory);
                Log.d(TAG, "目前答案: " + category3[randomnum]);
                textView1.setText("請問客語个「" + category7[randomnum]  + "」係甚麼意思");
                setContentView(view5);
            }
        });
        //文字版_題型動植物
        Button button10 = (Button) view3.findViewById(R.id.category4);
        button10.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                nowcategory = 4;
                randomnum = (int)(Math.random() * 51);
                Log.d(TAG, "題型: " + nowcategory);
                Log.d(TAG, "目前答案: " + category4[randomnum]);
                textView1.setText("請問客語个「" + category8[randomnum]  + "」係甚麼意思");
                setContentView(view5);
            }
        });
        //文字版出題
        Button answerbtn = (Button) view5.findViewById(R.id.next);
        answerbtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                int ans[] = {0,0,0};
                int cnt=0;
                if(nowcategory == 1) {//生活
                    answerpos = (int) (Math.random() * 4 + 1);
                    if (answerpos == 1) {
                        option1.setText(category1[randomnum]);
                        ans[cnt] = (int) (Math.random() * 117);
                        while(ans[cnt]==randomnum)
                        {
                            ans[cnt] = (int) (Math.random() * 117);
                        }
                        option2.setText(category1[ans[cnt]]);
                        cnt++;
                        ans[cnt] = (int) (Math.random() * 117);
                        for( int i = 0 ; i < cnt ; ++i )
                        {
                            while(ans[cnt]==ans[i])
                            {
                                ans[cnt] = (int) (Math.random() * 117);
                            }
                        }
                        option3.setText(category1[ans[cnt]]);
                        cnt++;
                        ans[cnt] = (int) (Math.random() * 117);
                        for( int i = 0 ; i < cnt ; ++i )
                        {
                            while(ans[cnt]==ans[i])
                            {
                                ans[cnt] = (int) (Math.random() * 117);
                            }
                        }
                        option4.setText(category1[ans[cnt]]);
                    }
                    if (answerpos == 2) {
                        option2.setText(category1[randomnum]);
                        ans[cnt] = (int) (Math.random() * 117);
                        while(ans[cnt]==randomnum)
                        {
                            ans[cnt] = (int) (Math.random() * 117);
                        }
                        option1.setText(category1[ans[cnt]]);
                        cnt++;
                        ans[cnt] = (int) (Math.random() * 117);
                        for( int i = 0 ; i < cnt ; ++i )
                        {
                            while(ans[cnt]==ans[i])
                            {
                                ans[cnt] = (int) (Math.random() * 117);
                            }
                        }
                        option3.setText(category1[ans[cnt]]);
                        cnt++;
                        ans[cnt] = (int) (Math.random() * 117);
                        for( int i = 0 ; i < cnt ; ++i )
                        {
                            while(ans[cnt]==ans[i])
                            {
                                ans[cnt] = (int) (Math.random() * 117);
                            }
                        }
                        option4.setText(category1[ans[cnt]]);
                    }
                    if (answerpos == 3) {
                        option3.setText(category1[randomnum]);
                        ans[cnt] = (int) (Math.random() * 117);
                        while(ans[cnt]==randomnum)
                        {
                            ans[cnt] = (int) (Math.random() * 117);
                        }
                        option2.setText(category1[ans[cnt]]);
                        cnt++;
                        ans[cnt] = (int) (Math.random() * 117);
                        for( int i = 0 ; i < cnt ; ++i )
                        {
                            while(ans[cnt]==ans[i])
                            {
                                ans[cnt] = (int) (Math.random() * 117);
                            }
                        }
                        option1.setText(category1[ans[cnt]]);
                        cnt++;
                        ans[cnt] = (int) (Math.random() * 117);
                        for( int i = 0 ; i < cnt ; ++i )
                        {
                            while(ans[cnt]==ans[i])
                            {
                                ans[cnt] = (int) (Math.random() * 117);
                            }
                        }
                        option4.setText(category1[ans[cnt]]);
                    }
                    if (answerpos == 4) {
                        option4.setText(category1[randomnum]);
                        ans[cnt] = (int) (Math.random() * 117);
                        while(ans[cnt]==randomnum)
                        {
                            ans[cnt] = (int) (Math.random() * 117);
                        }
                        option2.setText(category1[ans[cnt]]);
                        cnt++;
                        ans[cnt] = (int) (Math.random() * 117);
                        for( int i = 0 ; i < cnt ; ++i )
                        {
                            while(ans[cnt]==ans[i])
                            {
                                ans[cnt] = (int) (Math.random() * 117);
                            }
                        }
                        option3.setText(category1[ans[cnt]]);
                        cnt++;
                        ans[cnt] = (int) (Math.random() * 117);
                        for( int i = 0 ; i < cnt ; ++i )
                        {
                            while(ans[cnt]==ans[i])
                            {
                                ans[cnt] = (int) (Math.random() * 117);
                            }
                        }
                        option1.setText(category1[ans[cnt]]);
                    }

                }
                if(nowcategory == 2) {//娛樂
                        answerpos = (int)(Math.random() * 4 + 1);
                        if(answerpos == 1)
                        {
                            option1.setText(category2[randomnum]);
                            ans[cnt] = (int) (Math.random() * 33);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 33);
                            }
                            option2.setText(category2[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 33);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 33);
                                }
                            }
                            option3.setText(category2[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 33);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 33);
                                }
                            }
                            option4.setText(category2[ans[cnt]]);
                        }
                        if(answerpos == 2)
                        {
                            option2.setText(category2[randomnum]);
                            ans[cnt] = (int) (Math.random() * 33);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 33);
                            }
                            option1.setText(category2[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 33);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 33);
                                }
                            }
                            option3.setText(category2[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 33);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 33);
                                }
                            }
                            option4.setText(category2[ans[cnt]]);
                        }
                        if(answerpos == 3)
                        {
                            option3.setText(category2[randomnum]);
                            ans[cnt] = (int) (Math.random() * 33);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 33);
                            }
                            option2.setText(category2[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 33);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 33);
                                }
                            }
                            option1.setText(category2[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 33);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 33);
                                }
                            }
                            option4.setText(category2[ans[cnt]]);
                        }
                        if(answerpos == 4)
                        {
                            option4.setText(category2[randomnum]);
                            ans[cnt] = (int) (Math.random() * 33);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 33);
                            }
                            option2.setText(category2[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 33);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 33);
                                }
                            }
                            option3.setText(category2[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 33);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 33);
                                }
                            }
                            option1.setText(category2[ans[cnt]]);
                        }
                }
                if(nowcategory == 3) {//時空
                        answerpos = (int)(Math.random() * 4 + 1);
                        if(answerpos == 1)
                        {
                            option1.setText(category3[randomnum]);
                            ans[cnt] = (int) (Math.random() * 79);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 79);
                            }
                            option2.setText(category3[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 79);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 79);
                                }
                            }
                            option3.setText(category3[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 79);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 79);
                                }
                            }
                            option4.setText(category3[ans[cnt]]);
                        }
                        if(answerpos == 2)
                        {
                            option2.setText(category3[randomnum]);
                            ans[cnt] = (int) (Math.random() * 79);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 79);
                            }
                            option1.setText(category3[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 79);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 79);
                                }
                            }
                            option3.setText(category3[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 79);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 79);
                                }
                            }
                            option4.setText(category3[ans[cnt]]);
                        }
                        if(answerpos == 3)
                        {
                            option3.setText(category3[randomnum]);
                            ans[cnt] = (int) (Math.random() * 79);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 79);
                            }
                            option2.setText(category3[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 79);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 79);
                                }
                            }
                            option1.setText(category3[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 79);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 79);
                                }
                            }
                            option4.setText(category3[ans[cnt]]);
                        }
                        if(answerpos == 4)
                        {
                            option4.setText(category3[randomnum]);
                            ans[cnt] = (int) (Math.random() * 79);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 79);
                            }
                            option2.setText(category3[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 79);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 79);
                                }
                            }
                            option3.setText(category3[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 79);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 79);
                                }
                            }
                            option1.setText(category3[ans[cnt]]);
                        }
                }
                if(nowcategory == 4) {//動植
                        answerpos = (int)(Math.random() * 4 + 1);
                        if(answerpos == 1)
                        {
                            option1.setText(category4[randomnum]);
                            ans[cnt] = (int) (Math.random() * 51);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 51);
                            }
                            option2.setText(category4[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 51);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 51);
                                }
                            }
                            option3.setText(category4[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 51);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 51);
                                }
                            }
                            option4.setText(category4[ans[cnt]]);
                        }
                        if(answerpos == 2)
                        {
                            option2.setText(category4[randomnum]);
                            ans[cnt] = (int) (Math.random() * 51);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 51);
                            }
                            option1.setText(category4[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 51);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 51);
                                }
                            }
                            option3.setText(category4[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 51);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 51);
                                }
                            }
                            option4.setText(category4[ans[cnt]]);
                        }
                        if(answerpos == 3)
                        {
                            option3.setText(category4[randomnum]);
                            ans[cnt] = (int) (Math.random() * 51);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 51);
                            }
                            option2.setText(category4[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 51);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 51);
                                }
                            }
                            option1.setText(category4[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 51);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 51);
                                }
                            }
                            option4.setText(category4[ans[cnt]]);
                        }
                        if(answerpos == 4)
                        {
                            option4.setText(category4[randomnum]);
                            ans[cnt] = (int) (Math.random() * 51);
                            while(ans[cnt]==randomnum)
                            {
                                ans[cnt] = (int) (Math.random() * 51);
                            }
                            option2.setText(category4[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 51);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 51);
                                }
                            }
                            option3.setText(category4[ans[cnt]]);
                            cnt++;
                            ans[cnt] = (int) (Math.random() * 51);
                            for( int i = 0 ; i < cnt ; ++i )
                            {
                                while(ans[cnt]==ans[i])
                                {
                                    ans[cnt] = (int) (Math.random() * 51);
                                }
                            }
                            option1.setText(category4[ans[cnt]]);
                        }
                }
                setContentView(view6);
            }
        });
        //文字版回答 日常
        Button button11 = (Button) view6.findViewById(R.id.option1);
        button11.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                if(answerpos==1)
                {
                    robotAPI.robot.speak("恭喜！答對了！！請用手機掃描QR Code獲得優惠券一張");
                    setContentView(view4);
                }
                else
                {
                    robotAPI.robot.speak("答錯了請再接再厲");
                }
            }
        });
        //文字版回答 娛樂
        Button button12 = (Button) view6.findViewById(R.id.option2);
        button12.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                if(answerpos==2)
                {
                    robotAPI.robot.speak("恭喜！答對了！！請用手機掃描QR Code獲得優惠券一張");
                    setContentView(view4);
                }
                else
                {
                    robotAPI.robot.speak("答錯了請再接再厲");
                }
            }
        });
        //文字版回答 時空
        Button button13 = (Button) view6.findViewById(R.id.option3);
        button13.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                if(answerpos==3)
                {
                    robotAPI.robot.speak("恭喜！答對了！！請用手機掃描QR Code獲得優惠券一張");
                    setContentView(view4);
                }
                else
                {
                    robotAPI.robot.speak("答錯了請再接再厲");
                }
            }
        });
        //文字版回答 動植物
        Button button14 = (Button) view6.findViewById(R.id.option4);
        button14.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                if(answerpos==4)
                {
                    robotAPI.robot.speak("恭喜！答對了！！請用手機掃描QR Code獲得優惠券一張");
                    setContentView(view4);
                }
                else
                {
                    robotAPI.robot.speak("答錯了請再接再厲");
                }
            }
        });
        Button bbtn = (Button) view4.findViewById(R.id.backtomain);
        bbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //robotAPI.robot.setExpression(RobotFace.EXPECTING);
                Log.d(TAG, "回主畫面");
                setContentView(view1);
            }
        });

        context = getApplicationContext();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //mTextView = (TextView) findViewById(R.id.fullscreen_content);
    }
    public static Context getAppContext() {
        return context;
    }


    @Override
    protected void onResume() {
        super.onResume();
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);
        robotAPI.robot.jumpToPlan(DOMAIN, "Launch_HakkaExam");
        // show hint
        //mTextView.setText(getResources().getString(R.string.dialog_example))
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stop listen user utterance
        robotAPI.robot.stopSpeakAndListen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static RobotCallback robotCallback = new RobotCallback() {
        @Override
        public void onResult(int cmd, int serial, RobotErrorCode err_code, Bundle result) {
            super.onResult(cmd, serial, err_code, result);
            //MainActivity onResult = new MainActivity();
            //LayoutInflater inflater =  onResult.getLayoutInflater();
            //View view4 = inflater.inflate(R.layout.prize, null);
            //this.inflater = context.getLayoutInflater();

        }

        @Override
        public void onStateChange(int cmd, int serial, RobotErrorCode err_code, RobotCmdState state) {
            super.onStateChange(cmd, serial, err_code, state);
            Log.d(TAG, "onStateChange: " + state);
        }

        @Override
        public void initComplete() {
            super.initComplete();

        }
    };

    public static final RobotCallback.Listen robotListenCallback = new RobotCallback.Listen() {
        @Override
        public void onFinishRegister() {

        }

        @Override
        public void onVoiceDetect(JSONObject jsonObject) {

        }

        @Override
        public void onSpeakComplete(String s, String s1) {

        }

        @Override
        public void onEventUserUtterance(JSONObject jsonObject) {
            String text;
            text = "onEventUserUtterance: " + jsonObject.toString();
            Log.d(TAG, text);

        }

        String sSluResultword = null;

        @Override
        public void onResult(JSONObject jsonObject) {
            MainActivity onResult = new MainActivity();
            //this.context = context;
            //LayoutInflater inflater =  onResult.getLayoutInflater();

            View view4 = LayoutInflater.from(context).inflate(R.layout.prize, null);
            View view7 = LayoutInflater.from(context).inflate(R.layout.sound_wait_answer, null);
            String text;
            text = "onResult: " + jsonObject.toString();
            Log.d(TAG, text);
            String sIntentionID = RobotUtil.queryListenResultJson(jsonObject, "IntentionId");
            Log.d(TAG, "Intention Id = " + sIntentionID);
            if (sIntentionID.equals("Answer")) {
                //robotAPI.robot.setExpression(RobotFace.HAPPY);
                sSluResultword = RobotUtil.queryListenResultJson(jsonObject, "answer", null);
                Log.d(TAG, "sSluResultword: " + sSluResultword);
                if(sSluResultword != null) {
                    Log.d(TAG, "answer: " + sSluResultword);
                    if (nowcategory == 1) {
                        if (sSluResultword.equals(category1[randomnum])) {
                            Log.d(TAG, "題目: " + category1[randomnum]);
                            robotAPI.robot.speak("恭喜！答對了！！請用手機掃描QR Code獲得優惠券一張");
                            onResult.setContentView(view4);
                        }
                    }
                    if (nowcategory == 2) {
                        if (sSluResultword.equals(category2[randomnum])) {
                            Log.d(TAG, "題目: " + category2[randomnum]);
                            robotAPI.robot.speak("恭喜！答對了！！請用手機掃描QR Code獲得優惠券一張");
                            onResult.setContentView(view4);
                        }
                    }
                    if (nowcategory == 3) {
                        if (sSluResultword.equals(category3[randomnum])) {
                            Log.d(TAG, "題目: " + category3[randomnum]);
                            robotAPI.robot.speak("恭喜！答對了！！請用手機掃描QR Code獲得優惠券一張");
                            onResult.setContentView(view4);
                        }
                    }
                    if (nowcategory == 4) {
                        if (sSluResultword.equals(category4[randomnum])) {
                            Log.d(TAG, "題目: " + category4[randomnum]);
                            robotAPI.robot.speak("恭喜！答對了！！請用手機掃描QR Code獲得優惠券一張");
                            onResult.setContentView(view4);
                        }
                    }
                }
                else
                {
                    robotAPI.robot.speakAndListen("",new SpeakConfig().MODE_FOREVER);
                }
            }
        }

        @Override
        public void onRetry(JSONObject jsonObject) {
            robotAPI.robot.speak("答錯了請再接再厲");
            Log.d(TAG, "答錯");
        }
    };
    public MainActivity() {
        super(robotCallback, robotListenCallback);
    }
}

