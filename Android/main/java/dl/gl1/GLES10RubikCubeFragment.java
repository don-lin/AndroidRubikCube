package dl.gl1;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import dl.gl1.ObjectsSample.MyGLSurfaceView;
import dl.gl1.RubikCubeObjects.RubikCubeGLSurfaceView;

public class GLES10RubikCubeFragment extends Fragment {
    public static RubikCubeGLSurfaceView mGLView;
    public static int layer;

    public GLES10RubikCubeFragment(){
    }

    public GLES10RubikCubeFragment(int layer){
        this.layer=layer;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mGLView = new RubikCubeGLSurfaceView(getContext(),layer);
        return mGLView;
    }


    @Override
    public void onPause() {
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        super.onPause();
        mGLView.onPause();
    }

    @Override
    public void onResume() {
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        super.onResume();
        mGLView.onResume();
    }
}
