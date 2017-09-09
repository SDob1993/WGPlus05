package ur.mi.android.wgplus05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {


    private ImageButton finanzenButton;
    private ImageButton einkaufslisteButton;
    private ImageButton kalenderButton;
    private ImageButton einstellungenButton;
    private ImageButton blackboardButton;
    private ImageButton putzplanButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();
        initOnClickListener();

    }

    private void initButtons(){
        finanzenButton = (ImageButton) findViewById(R.id.finanzen_button);
        einkaufslisteButton = (ImageButton) findViewById(R.id.einkaufsliste_button);
        kalenderButton = (ImageButton) findViewById(R.id.kalender_button);
        einstellungenButton = (ImageButton) findViewById(R.id.einstellungen_button);
        blackboardButton = (ImageButton) findViewById(R.id.blackboard_button);
        putzplanButton = (ImageButton) findViewById(R.id.putzplan_button);

    }

    private void initOnClickListener(){
        finanzenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Finanzen.class);
                startActivity(intent);
            }
        });

        einkaufslisteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Einkaufsliste2.class);
                startActivity(intent);
            }
        });

        kalenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Kalender.class);
                startActivity(intent);
            }
        });

        einstellungenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        blackboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FotoWand.class);
                startActivity(intent);
            }
        });
        putzplanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Putzplan.class);
                startActivity(intent);
            }
        });
    }





}
