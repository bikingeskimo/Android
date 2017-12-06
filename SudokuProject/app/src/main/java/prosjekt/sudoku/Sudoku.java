package prosjekt.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;


/*
   Lag en Sudoku-app (selv om det finnes haugevis av dem fra før). Se f.eks.
   http://www.matematikk.org/sudoku/ for regler og eksempel (På dette web-stedet har man både 6X6
   og 9X9-brett. Det er nok at du lager 9X9.)

   Appen skal ha (minst) to deler, en der du kan hente fram et startbrett og fylle ut, og en der du kan
   legge inn startbrett i et lager. Startbrettene skal klassifiseres som enten lett, middels eller vanskelig,
   og spilleren skal kunne velge kategori og få ut et tilfeldig brett i denne kategorien. (Startbrett: Det
   delvis utfylte skjemaet man starter med.)

   I den enkleste versjonen kan lageret ligge på mobil-enheten, men hvis du vil, kan du prøve deg på en
   løsning der lageret ligger på en server som appen kommuniserer med. Sørg i så fall for at det er mulig
   for den som skal vurdere innleveringen din å teste det.

   Minimumskrav for å få løsningen godkjent:
        Mulig å hente fram start-brett i en bestemt kategori. Lageret må derfor inneholde minst ett startbrett av hver type.
        Mulig å legge inn et nytt start-brett av en bestemt type.
        Mulig å velge en rute og sette en verdi der.
        Mulig å slette verdien i en rute.
        Mulig å skifte farge på en verdi, f.eks. for å markere at man er usikker og kanskje må stryke den igjen.
        Få beskjed om det er riktig eller galt når alle rutene er fylt ut.
        Kunne velge mellom norsk og engelsk.
        En liten bruksanvisning.

 */

import java.util.Locale;

import static prosjekt.sudoku.Board.DIFFICULTY_EASY;
import static prosjekt.sudoku.Board.KEY_DIFFICULTY;

public class Sudoku extends Activity{
    private static final String TAG = "Sudoku";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.difficulty:
                        openNewGameDialog();
                        break;

                    case R.id.new_board:
                        openNewBoardDialog();
                        break;

                    case R.id.new_game:
                        int i = getIntent().getIntExtra(KEY_DIFFICULTY,DIFFICULTY_EASY);
                        startGame(i);
                        break;

                    case R.id.usermanual_button:
                        openUserManual();
                        break;

                    case R.id.language_button:
                        openLanguageDialog();
                        break;

                    case R.id.exit_button:
                        finish();
                        break;
                }
                return true;
            }
        });

    }

    private void startGame(int i) {
        Log.d(TAG, "clicked on " + i);
        Intent intent = new Intent(Sudoku.this, Board.class);
        intent.putExtra(KEY_DIFFICULTY, i);
        startActivity(intent);
    }

    private void createBoard(int i){
        Intent intent = new Intent(Sudoku.this, Board.class);
        intent.putExtra(KEY_DIFFICULTY, 3); //3 means empty board in this case
        startActivity(intent);


    }

    /** Ask the user what difficulty level they want */
    private void openNewGameDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.new_game_title)
                .setItems(R.array.difficulty,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                startGame(i);
                            }
                        }).show();
    }
    private void openNewBoardDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.new_game_title)
                .setItems(R.array.difficulty,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                createBoard(i);
                            }
                        }).show();
    }
    private void openUserManual(){
        new AlertDialog.Builder(this).setTitle(R.string.usermanual_label).setMessage(R.string.usermanual).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        }).show();
    }

    private void openLanguageDialog(){

        new AlertDialog.Builder(this).setMessage(R.string.language_label).setPositiveButton("Norsk", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /* Deprecated methods, but the only ones that did somewhat work... */
                Locale locale = new Locale("no", "NO");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = locale;
                res.updateConfiguration(conf, dm);


            }
        }).setNegativeButton("English", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Locale locale = new Locale("en", "US");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = locale;
                res.updateConfiguration(conf, dm);
            }
        }).show();
    }



}
