package com.commonsware.android.frw.filesdemo;

import android.app.Fragment;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.commonsware.android.frw.filesdemo.model.ActionEvent;
import com.commonsware.android.frw.filesdemo.service.BuildTask;
import com.commonsware.android.frw.filesdemo.service.BusHandler;
import com.commonsware.android.frw.filesdemo.service.DeleteTask;
import com.commonsware.android.frw.filesdemo.service.FileHelper;
import com.commonsware.android.frw.filesdemo.service.LoadTask;
import com.commonsware.android.frw.filesdemo.service.SaveTask;
import com.commonsware.android.frw.filesdemo.utils.Logging;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.File;

/**
 * Created by Juan on 5/21/2015.
 */
@EFragment(R.layout.editor)
@OptionsMenu(R.menu.actions)
public class EditorFragment extends Fragment
{
    private static final String FILENAME = "json.txt";
    private CheckBox external = null;

    @ViewById
    EditText editor;

    @Bean
    SaveTask saveTask;

    @Bean
    LoadTask loadTask;

    @Bean
    DeleteTask deleteTask;

    @Bean
    BuildTask buildTask;

    @Bean
    BusHandler busHandler;

    @Override
    public void onResume()
    {
        super.onResume();
        busHandler.register(this);
        buildTask.execute();
        loadTask.execute(getTarget());


    }

    @Override
    public void onPause()
    {
        super.onPause();
        busHandler.unregister(this);
    }

    @OptionsItem
    void saveSelected()
    {
        try
        {
            FileHelper.save(editor.getText().toString(), getTarget());
        }
        catch (Exception e)
        {
            onException(e);
        }
    }

    @OptionsItem
    void saveBackgroundSelected()
    {
        saveTask.execute(editor.getText().toString(), getTarget());
    }

    @OptionsItem
    void deleteBackgroundSelected()
    {
        deleteTask.execute( getTarget() );
    }

    @Subscribe
    public void onValueChanged( String content )
    {
        if( content != null )
        {
            editor.setText( content );
        }
    }

    @Subscribe
    public void onException( Exception e )
    {
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        Logging.print("Exception saving file" + e.getMessage());
    }

    @Subscribe
    public void onActionEvent( ActionEvent e )
    {
        editor.setText( "" );
    }

    private File getTarget()
    {
        File root = null;

        if (external != null && external.isChecked())
        {
            root = getActivity().getExternalFilesDir(null);
        }
        else
        {
            root = getActivity().getFilesDir();
        }

        return (new File(root, FILENAME));
    }
}