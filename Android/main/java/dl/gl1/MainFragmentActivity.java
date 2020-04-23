package dl.gl1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import dl.gl1.AugmentReaility.ImageDetection.BaseAugmentRealityActivity;
import dl.gl1.AugmentReaility.SurfaceTracking.SrufaceTrackingActivity;
import dl.gl1.GravitySensorService.GravitySensor;

public class MainFragmentActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        Button basicSceneButton=findViewById(R.id.basicScene);
        basicSceneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeFragment(new GLES10HelloFragment());
            }
        });
        Button rubikSceneButton3=findViewById(R.id.rubikCubeScene3);
        rubikSceneButton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeFragment(new GLES10RubikCubeFragment(3));
            }
        });


        Button rubikSceneButton4=findViewById(R.id.rubikCubeScene4);
        rubikSceneButton4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeFragment(new GLES10RubikCubeFragment(4));
            }
        });


        Button rubikSceneButton5=findViewById(R.id.rubikCubeScene5);
        rubikSceneButton5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeFragment(new GLES10RubikCubeFragment(5));
            }
        });

        Button rubikSceneButton13=findViewById(R.id.rubikCubeScene13);
        rubikSceneButton13.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeFragment(new GLES10RubikCubeFragment(13));
            }
        });

        Button helpButton=findViewById(R.id.help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new HelpFragment());
            }
        });
        Button settingButton=findViewById(R.id.setting);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new SettingFragment());
            }
        });
        Button arImageButton=findViewById(R.id.arImage);
        arImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainFragmentActivity.this, BaseAugmentRealityActivity.class);
                startActivity(intent);
            }
        });
        Button arSurfaceButton=findViewById(R.id.arFace);
        arSurfaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainFragmentActivity.this, SrufaceTrackingActivity.class);
                startActivity(intent);
            }
        });
        changeFragment(new GLES10RubikCubeFragment(3));
    }

    public void changeFragment(Fragment fragment) {
        if(fragment instanceof GLES10RubikCubeFragment){
            GravitySensor.regeister(getApplicationContext());
        }else{
            GravitySensor.unregeister();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out);
        fragmentTransaction.replace(R.id.testFragment, fragment);
        fragmentTransaction.commit();
    }
}
