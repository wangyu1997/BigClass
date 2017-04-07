package com.njtech.bigclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.njtech.bigclass.AddClassActivity;
import com.njtech.bigclass.HomePageActivity;
import com.njtech.bigclass.R;
import com.njtech.bigclass.Regist1Activity;
import com.njtech.bigclass.entity.CourseEntity.DataBean;
import com.njtech.bigclass.utils.ListViewUtil;

import java.util.ArrayList;
import java.util.List;

public class ListCourseGroupItemAdapter extends BaseAdapter {

    private List<DataBean> objects = new ArrayList<DataBean>();

    private LayoutInflater layoutInflater;
    private AppCompatActivity context;
    private ListAcademyListItemAdapter adapter;


    public ListCourseGroupItemAdapter(Context context, List<DataBean> objects) {
        this.context = (AppCompatActivity) context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public DataBean getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_course_group_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((DataBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(DataBean object, ViewHolder holder) {
        //TODO implement
        holder.tvCourseGroup.setText(object.getKey());
        adapter = new ListAcademyListItemAdapter(context, object.getInfo());
        final List<DataBean.InfoBean> objects = object.getInfo();
        holder.courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataBean.InfoBean bean = objects.get(position);
                int course_id = Integer.parseInt(bean.getId());
                String course_name = bean.getName();
                String gpa = bean.getGpa();
                Intent intent = new Intent(context, AddClassActivity.class);
                intent.putExtra("course_id", course_id);
                intent.putExtra("course_name", course_name);
                intent.putExtra("course_gpa", gpa);
                context.setResult(AddClassActivity.Course_res, intent);
                context.finish();
            }
        });
        holder.courseList.setAdapter(adapter);
        ListViewUtil.setListViewHeightBasedOnChildren(holder.courseList);
    }

    protected class ViewHolder {
        private TextView tvCourseGroup;
        private ListView courseList;

        public ViewHolder(View view) {
            tvCourseGroup = (TextView) view.findViewById(R.id.tv_course_group);
            courseList = (ListView) view.findViewById(R.id.course_list);
        }
    }

    public class ListAcademyListItemAdapter extends BaseAdapter {

        private List<DataBean.InfoBean> objects = new ArrayList<DataBean.InfoBean>();

        private Context context;
        private LayoutInflater layoutInflater;

        public ListAcademyListItemAdapter(Context context, List<DataBean.InfoBean> objects) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.objects = objects;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public DataBean.InfoBean getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_course_list_item, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews((DataBean.InfoBean) getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(DataBean.InfoBean object, ViewHolder holder) {
            //TODO implement
            holder.tvCourseGroupItem.setText(object.getName());
        }

        protected class ViewHolder {
            private TextView tvCourseGroupItem;

            public ViewHolder(View view) {
                tvCourseGroupItem = (TextView) view.findViewById(R.id.tv_course_group_item);
            }
        }
    }

}

