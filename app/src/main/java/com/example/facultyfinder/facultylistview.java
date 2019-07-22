package com.example.facultyfinder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class facultylistview extends ArrayAdapter<FacultyInfo> {
    private Activity context;
    private List<FacultyInfo> flist;

    facultylistview(Activity context,List<FacultyInfo> flist)
    {
        super(context,R.layout.facultylistitem,flist);
        this.context=context;
        this.flist=flist;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=context.getLayoutInflater();
        View flistview=inflater.inflate(R.layout.facultylistitem,null);
        TextView facultynametext=(TextView)flistview.findViewById(R.id.faculty_name);
        TextView facultyphonetext=(TextView)flistview.findViewById(R.id.faculty_phoneno);
        FacultyInfo facultyInfo=flist.get(i);
        facultyphonetext.setText(facultyInfo.getFacultyphoneno());
        facultynametext.setText(facultyInfo.getFacultyname());
        return flistview;
    }
}
