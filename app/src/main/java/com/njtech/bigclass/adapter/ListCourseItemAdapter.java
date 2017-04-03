package com.njtech.bigclass.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.njtech.bigclass.R;
import com.njtech.bigclass.entity.courseShowEntity.DataBean;
import com.njtech.bigclass.utils.MyApplication;

public class ListCourseItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataBean> objects = new ArrayList<DataBean>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ListCourseItemAdapter(Context context, List<DataBean> objects) {
        this.objects = objects;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<DataBean> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_course_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataBean object = objects.get(position);
        String courseName = object.getC_name();
        String headUrl = object.getHeader();
        String teacherName = object.getTeacher();
        String place = object.getPlace();
        String time = object.getTime();
        String grade = object.getGpa();
        String number = object.getNumber();
        if (holder instanceof ViewHolder) {
            if (headUrl == null || !headUrl.contains("http://") || headUrl.isEmpty()) {
                ((ViewHolder) holder).courseIcon.setImageResource(R.mipmap.logo);
            } else {
                Uri imgUri = Uri.parse(headUrl);
                ((ViewHolder) holder).courseIcon.setImageURI(imgUri);
            }
            ((ViewHolder) holder).tvCoursename.setText(courseName);
            ((ViewHolder) holder).tvTeacher.setText(teacherName);
            ((ViewHolder) holder).tvGrade.setText(grade);
            ((ViewHolder) holder).tvPlace.setText(place);
            ((ViewHolder) holder).tvTime.setText(time);
            ((ViewHolder) holder).tvStudentnumber.setText(number);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCoursename;
        private SimpleDraweeView courseIcon;
        private TextView tvTeacher;
        private TextView tvPlace;
        private TextView tvTime;
        private TextView tvGrade;
        private TextView tvStudentnumber;
        private SimpleDraweeView courseState;

        public ViewHolder(View view) {
            super(view);
            tvCoursename = (TextView) view.findViewById(R.id.tv_coursename);
            courseIcon = (SimpleDraweeView) view.findViewById(R.id.course_icon);
            tvTeacher = (TextView) view.findViewById(R.id.tv_teacher);
            tvPlace = (TextView) view.findViewById(R.id.tv_place);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvGrade = (TextView) view.findViewById(R.id.tv_grade);
            tvStudentnumber = (TextView) view.findViewById(R.id.tv_studentnumber);
            courseState = (SimpleDraweeView) view.findViewById(R.id.course_state);
        }
    }
}
