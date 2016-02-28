package com.example.administrator.schedule;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView Mon_12,Mon_34,Mon_56,Mon_78,Tue_12,Tue_34,Tue_56,Tue_78,Wed_12,Wed_34,Wed_56,Wed_78,Thu_12,Thu_34,Thu_56,Thu_78,Fri_12,Fri_34,Fri_56,Fri_78;
    private Spinner spinner;
    private EditText editText;
    private Button btn;
    private ArrayAdapter<String> adapter;
    private Map map,map1;

    private final  static String[] week={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
  //  public static String schedule=null;
   // public static String Mon=null;
    //public static String Tue;

   // public static  String Wed;
   // public static String Thu;
   // public static String Fri;
    public static String lwx;
   // public static String _12,_34,_56,_78,class_name,teacher_name,classroom,class_length;
   // public static int[] weeks;
    public TextView[][] buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view=findViewById(R.id.ll);
        view.getBackground().setAlpha(70);
        init();
        load();

            }

            void init() {
                editText = (EditText) findViewById(R.id.et);
                btn = (Button) findViewById(R.id.bnt);
                Mon_12 = (TextView) findViewById(R.id.Mon_12);
                Mon_34 = (TextView) findViewById(R.id.Mon_34);
                Mon_56 = (TextView) findViewById(R.id.Mon_56);
                Mon_78 = (TextView) findViewById(R.id.Mon_78);
                Tue_12 = (TextView) findViewById(R.id.Tue_12);
                Tue_34 = (TextView) findViewById(R.id.Tue_34);
                Tue_56 = (TextView) findViewById(R.id.Tue_56);
                Tue_78 = (TextView) findViewById(R.id.Tue_78);
                Wed_12 = (TextView) findViewById(R.id.Wed_12);
                Wed_34 = (TextView) findViewById(R.id.Wed_34);
                Wed_56 = (TextView) findViewById(R.id.Wed_56);
                Wed_78 = (TextView) findViewById(R.id.Wed_78);
                Thu_12 = (TextView) findViewById(R.id.Thu_12);
                Thu_34 = (TextView) findViewById(R.id.Thu_34);
                Thu_56 = (TextView) findViewById(R.id.Thu_56);
                Thu_78 = (TextView) findViewById(R.id.Thu_78);
                Fri_12 = (TextView) findViewById(R.id.Fri_12);
                Fri_34 = (TextView) findViewById(R.id.Fri_34);
                Fri_56 = (TextView) findViewById(R.id.Fri_56);
                Fri_78 = (TextView) findViewById(R.id.Fri_78);
                buttons=new TextView[5][4];
                buttons[0][0]=Mon_12;
                buttons[0][1]=Mon_34;
                buttons[0][2]=Mon_56;
                buttons[0][3]=Mon_78;
                buttons[1][0]=Tue_12;
                buttons[1][1]=Tue_34;
                buttons[1][2]=Tue_56;
                buttons[1][3]=Tue_78;
                buttons[2][0]=Wed_12;
                buttons[2][1]=Wed_34;
                buttons[2][2]=Wed_56;
                buttons[2][3]=Wed_78;
                buttons[3][0]=Thu_12;
                buttons[3][1]=Thu_34;
                buttons[3][2]=Thu_56;
                buttons[3][3]=Thu_78;
                buttons[4][0]=Fri_12;
                buttons[4][1]=Fri_34;
                buttons[4][2]=Fri_56;
                buttons[4][3]=Fri_78;
            }

            public void load() {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://120.27.53.146:5000/api/schedule", new Response.Listener<String>() {
                    @Override
                    public void onResponse( final String s) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new BackGround().execute(s);
                            }
                        });
                    }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("TAG", volleyError.getMessage(), volleyError);
                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map map = new HashMap();
                        map.put("username", "201411106");
                        map.put("password", "116671");
                        map.put("action", "update");
                        return map;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        20000,
                        0,
                        0f));
                requestQueue.add(stringRequest);

        }
    public class BackGround extends AsyncTask<String,Void,String[][][]> {

        @Override
        protected String[][][] doInBackground(String... params) {

            String[][][] information = new String[18][5][4];

            try {
                JSONObject jsonObject = new JSONObject(params[0]);
                String schedule = jsonObject.getString("schedule");
                JSONObject jsonObject1 = new JSONObject(schedule);
                String Mon = jsonObject1.getString("Mon");
                String Tue = jsonObject1.getString("Tue");
                String Wed = jsonObject1.getString("Wed");
                String Thu = jsonObject1.getString("Thu");
                String Fri = jsonObject1.getString("Fri");
                String _12, _34, _56, _78, class_name;
                Map m = new LinkedHashMap();
                m.put("Mon", Mon);
                m.put("Tue", Tue);
                m.put("Wed", Wed);
                m.put("Thu", Thu);
                m.put("Fri", Fri);
                int weekconut = -1, classcount = -1;
                Iterator iterator = m.keySet().iterator();
                while (iterator.hasNext()) {
                    classcount = -1;
                    weekconut++;

                    String weeks = (String) iterator.next();
                    String y = (String) m.get(weeks);
                    JSONObject jsonObject2 = new JSONObject(y);
                    _12 = jsonObject2.getString("1-2");
                    _34 = jsonObject2.getString("3-4");
                    _56 = jsonObject2.getString("5-6");
                    _78 = jsonObject2.getString("7-8");
                    Map n = new LinkedHashMap();
                    n.put("1-2", _12);
                    n.put("3-4", _34);
                    n.put("5-6", _56);
                    n.put("7-8", _78);
                    Iterator iterator1 = n.keySet().iterator();
                    while (iterator1.hasNext()) {

                        classcount++;
                        String x = (String) iterator1.next();
                        String q = (String) n.get(x);
                        if (!q.equals("[null]")) {
                            JSONArray jsonArray = new JSONArray(q);
                            // System.out.println(q);
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject7 = jsonArray.getJSONObject(j);
                                String infos = jsonObject7.getString("weeks");
                                infos = infos.replaceAll("[\\[\\]]", "");
                                String[] info = infos.split(",");
                                Log.d("tag", info.toString());
                                int[] w = new int[info.length];
                                for (int i = 0; i < info.length; i++) {
                                    w[i] = Integer.parseInt(info[i]);
                                }
                                for (int z = 0; z < w.length; z++) {
                                    //    System.out.println(w[z]-1+" "+weekconut+" "+classcount);
                                    information[w[z] - 1][weekconut][classcount] = jsonObject7.getString("class_name");
                                }
                            }
                        }
                    }
                }
                return  information;
            }catch (JSONException e){

            }
            return new String[0][][];
        }

        @Override
        protected void onPreExecute() {
            for (int r = 0; r < 5; r++) {
                for (int o = 0; o < 4; o++) {
                    buttons[r][o].setText("");
                }
            }
        }

        @Override
        protected void onPostExecute(String[][][] strings) {
            int e = Integer.valueOf(editText.getText().toString());
            if (e <= 18 && e >= 1) {
                for (int a = 0; a < 5; a++) {
                    for (int b = 0; b < 4; b++) {
                        buttons[a][b].setText(strings[e - 1][a][b]);
                    }
                }
            }
        }
    }

}
