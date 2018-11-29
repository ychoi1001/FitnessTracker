package example.com.fitnesstracker;

import android.app.DialogFragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AnimationDialog extends DialogFragment implements View.OnClickListener{

    ImageView imgView;
    public static final String TAG = AnimationDialog.class.getSimpleName();

    public static AnimationDialog newInstant (int picIdToPassToFragment){

        AnimationDialog m = new AnimationDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("animation",picIdToPassToFragment);
        m.setArguments(bundle);

        return m;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.animation_dialog, container,false);
        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");

        imgView = (ImageView)view.findViewById(R.id.picture);
        imgView.setBackgroundResource(R.drawable.frame_animation_old2);
        AnimationDrawable frameAnimation = (AnimationDrawable)imgView.getBackground();
        frameAnimation.start();
        return view;


    }

    @Override
    public void onClick(View v) {
      dismiss();
    }
}
