package android.oving3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Friend> friendsAdapter;
    private ListView friendsView;
    private ArrayList<Friend> friends;
    private final int NEW_FRIEND = 101;
    private final int EDIT_FRIEND = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friends = new ArrayList<>();
        friendsView = (ListView) findViewById(R.id.friendsView);
        friendsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                friends
        );

        friendsView.setAdapter(friendsAdapter);
        friendsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Friend selectedFriend = (Friend)  adapterView.getItemAtPosition(i);
                Intent editIntent = new Intent(MainActivity.this, FriendActivity.class );

                editIntent.putExtra("friend", selectedFriend);
                startActivityForResult(editIntent, EDIT_FRIEND);
            }
        });

        friendsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Friend selectedFriend = (Friend)adapterView.getItemAtPosition(i);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setMessage("Delete " + selectedFriend.getName() + "?");
                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Iterator<Friend> iterator = friends.iterator();
                        Friend friend;
                        while(iterator.hasNext()) {
                            friend = iterator.next();
                            if(friend.getID() == selectedFriend.getID()) {
                                iterator.remove();
                                break;
                            }
                        }
                        friendsAdapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                });

                dialogBuilder.create().show();
                return true;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case EDIT_FRIEND:
                    Friend editedFriend = (Friend) data.getExtras().getSerializable("friend");
                    for(Friend friend : friends) {
                        if(friend.getID() == editedFriend.getID()) {
                            friend.setName(editedFriend.getName());
                            friend.setBirthDate(editedFriend.getBirthDate());
                        }
                        break;
                    }
                    break;

                case NEW_FRIEND:
                    Friend newFriend = (Friend) data.getExtras().getSerializable("friend");
                    friends.add(newFriend);
                    friendsAdapter.notifyDataSetChanged();
                    break;

                default:
                    Log.i("onActivityResult", "Unknown requestCode");
                    break;
            }
        }
    }

    public void onNewButtonClick(View view) {
        startActivityForResult(new Intent(this, FriendActivity.class), NEW_FRIEND);
    }
}
