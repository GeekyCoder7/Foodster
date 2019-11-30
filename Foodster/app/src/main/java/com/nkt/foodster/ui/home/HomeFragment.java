package com.nkt.foodster.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nkt.foodster.MainActivity;
import com.nkt.foodster.R;
import com.nkt.foodster.RecipesActivity;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });





        CardView americanCV = (CardView) root.findViewById(R.id.americanCard);
        americanCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences.Editor preferenceEditor=mPreferences.edit();
//                preferenceEditor.putString("ID","201701143");
//                preferenceEditor.apply();
                Intent intent = new Intent(view.getContext(), RecipesActivity.class);
                intent.putExtra("cuisine_type", "American");
                view.getContext().startActivity(intent);
            }
        });

        CardView italianCV = (CardView) root.findViewById(R.id.italianCard);
        italianCV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Connect to Provider")
                        .setMessage("Hello Alert!")
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){
                                //cancel
                            }
                        })
                        .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){
                                //OK
                            }
                        });

                alertDialog.show();

            }
        });


        CardView armenianCV = (CardView) root.findViewById(R.id.armenianCard);
        armenianCV.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Uri uri = Uri.parse("tel:71531019");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);

            }
        });

        return root;
    }



}