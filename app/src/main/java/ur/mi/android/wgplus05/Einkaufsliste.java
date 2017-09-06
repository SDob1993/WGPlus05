package ur.mi.android.wgplus05;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

import static ur.mi.android.wgplus05.R.id.parent;

public class Einkaufsliste extends AppCompatActivity {

    private ImageButton addButton;
    private ListView einkaufsliste;
    private ArrayList einkaufslisteArrayList;
    private ArrayAdapter aa;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkaufsliste);
        setTitle("#NudelnUndWasNoch?");

        setupButton();
        setUpListView();
        setUpListView();


    }

    private void setupButton(){
        addButton = (ImageButton) findViewById(R.id.einkaufsliste_plus);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupAdd(v);
            }
        });

    }

    private void setUpListView(){
        einkaufsliste = (ListView) findViewById(R.id.einkaufsliste);
        einkaufsliste.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = einkaufsliste.getPositionForView(v);
                einkaufslisteArrayList.remove(pos);
                aa.notifyDataSetChanged();
                showPopupRemove(v);
                return true;
            }
        });
    }

    private void setUpListViewAdapter(){
        einkaufslisteArrayList = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,einkaufslisteArrayList);
        einkaufsliste.setAdapter(aa);

    }

    public void showPopupAdd(View anchorView) {

        final View popupView = getLayoutInflater().inflate(R.layout.layout_user_popup_einkaufsliste, null);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);


        // Example: If you have a TextView inside `popup_layout.xml`
        final EditText editText1 = (EditText) popupView.findViewById(R.id.edit_einkaufsliste);

        final Button button = (Button) popupView.findViewById(R.id.einkaufsliste_popup_button);





        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);
        popupWindow.update();


        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText1.getText().toString().length() != 0) {
                    einkaufslisteArrayList.add(editText1.getText().toString());
                    aa.notifyDataSetChanged();
                    popupWindow.dismiss();
                }
                else Toast.makeText(getApplicationContext(),"Dann gibts halt wieder Nudeln",Toast.LENGTH_LONG).show();
            }
        });

    }public void showPopupRemove(View anchorView) {

        ViewGroup parent = (ViewGroup) findViewById(R.id.activity_einkaufsliste_id);

        final View popupView = getLayoutInflater().inflate(R.layout.layout_user_popup_einkaufsliste_remove, parent, false );
        final EditText preis = (EditText) popupView.findViewById(R.id.edit_einkaufsliste_preis);
        final Button button = (Button) popupView.findViewById(R.id.einkaufsliste_popup_button_remove);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        anchorView.getLocationOnScreen(location);

        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preis.getText().toString().length() != 0) {
                    Finanzen finanzen = new Finanzen();
                    finanzen.addGuthabenDouble(Double.parseDouble(preis.getText().toString()));
                }
                else Toast.makeText(getApplicationContext(),"Dann gibts halt wieder Nudeln",Toast.LENGTH_LONG).show();
            }
        });

    }
}
