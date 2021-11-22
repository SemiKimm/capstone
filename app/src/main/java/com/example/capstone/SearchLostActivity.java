package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchLostActivity extends AppCompatActivity {

    ArrayAdapter ad1Adapter;
    ArrayAdapter ad2Adapter;
    ArrayAdapter colorAdapter;
    ArrayAdapter categoryAdapter;

    Button btn_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lost);

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner_regionInfo1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner_regionInfo2);

        final Spinner spinner3=(Spinner)findViewById(R.id.spinner_color);

        final Spinner spinner4=(Spinner)findViewById(R.id.spinner_category);

        btn_get = findViewById(R.id.button);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        colorAdapter = ArrayAdapter.createFromResource(this,R.array.color, android.R.layout.simple_spinner_dropdown_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(colorAdapter);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        categoryAdapter = ArrayAdapter.createFromResource(this,R.array.category, android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(categoryAdapter);

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ad1Adapter = ArrayAdapter.createFromResource(this,R.array.address1, android.R.layout.simple_spinner_dropdown_item);
        ad1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(ad1Adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(ad1Adapter.getItem(position).equals("서울특별시")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.seoul, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                } else if (ad1Adapter.getItem(position).equals("부산광역시")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.busan, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("대구광역시")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.daegu, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("인천광역시")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.incheon, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("광주광역시")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.gwangju, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("대전광역시")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.daejeon, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("울산광역시")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.ulsan, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("경기도")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.gyeonggi, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("강원도")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.gangwon, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("충청북도")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.chungbuk, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("충청남도")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.chungnam, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("전라북도")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.jeonbuk, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("전라남도")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.jeonnam, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("경상북도")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.gyeongbuk, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("경상남도")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.gyeongnam, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }  else if (ad1Adapter.getItem(position).equals("제주특별자치도")) {
                    ad2Adapter = ArrayAdapter.createFromResource(SearchLostActivity.this, R.array.jeju, android.R.layout.simple_spinner_dropdown_item);
                    ad2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(ad2Adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent){
                        }
                    });
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }


    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment2();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        TextView textView_Date;

        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);

        textView_Date = (TextView)findViewById(R.id.text_dateInfo);

        textView_Date.setText(year_string + "년" + month_string + "월" + day_string + "일");

    }



}