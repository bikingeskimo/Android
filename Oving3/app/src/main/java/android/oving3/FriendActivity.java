package android.oving3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Roger on 22.09.2017.
 */


public class FriendActivity extends AppCompatActivity {
    Friend friend;
    private TextView nameView, dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vis_person);

        nameView = (TextView) findViewById(R.id.name_field);
        dateView = (TextView) findViewById(R.id.date_field);

        Intent starterIntent = getIntent();
        if(starterIntent.hasExtra("friend")) {
            friend = (Friend)starterIntent.getExtras().getSerializable("friend");
        } else {
            friend = new Friend();
        }


        nameView.setText(friend.getName());
        dateView.setText(friend.getBirthDate());
    }

    public void onClickSaveButton(View view) {
        friend.setName(nameView.getText().toString());
        friend.setBirthDate(dateView.getText().toString());

        Intent resultIntent = new Intent();
        resultIntent.putExtra("friend", friend);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onClickCancelButton(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
