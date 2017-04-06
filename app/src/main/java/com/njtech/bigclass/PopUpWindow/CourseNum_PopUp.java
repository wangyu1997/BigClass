package com.njtech.bigclass.PopUpWindow;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.njtech.bigclass.R;
import com.njtech.bigclass.utils.MyApplication;

import java.lang.reflect.Field;

import butterknife.BindView;

/**
 * Created by wangyu on 8/27/16.
 */

public class CourseNum_PopUp extends PopupWindow {
    private View mMenuView;
    private NumberPicker grade_picker;
    public static String[] data;
    public static final int DEFAULT_VALUE = 0;
    private TextView title;
    private TextView cancel_tv, confirm_tv;

    public CourseNum_PopUp(boolean flag, final Activity mcontext, View.OnClickListener itemClickListener, NumberPicker.OnValueChangeListener onValueChangeListener) {
        mMenuView = LayoutInflater.from(mcontext).inflate(R.layout.week_popup, null);
        data = mcontext.getResources().getStringArray(R.array.num);
        backgroundMengban(mcontext, 0.4f);
        cancel_tv = (TextView) mMenuView.findViewById(R.id.pop_cancel);
        confirm_tv = (TextView) mMenuView.findViewById(R.id.pop_confirm);
        grade_picker = (NumberPicker) mMenuView.findViewById(R.id.picker);
        title = (TextView) mMenuView.findViewById(R.id.textView3);
        grade_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        if (flag){
            title.setText("请选择开始时间");
        }else {
            title.setText("请选择结束时间");
        }
        grade_picker.setDisplayedValues(data);
        setNumberPickerDividerColor(grade_picker);
        grade_picker.setMinValue(0);
        grade_picker.setMaxValue(data.length - 1);
        grade_picker.setValue(DEFAULT_VALUE);
        cancel_tv.setOnClickListener(itemClickListener);
        confirm_tv.setOnClickListener(itemClickListener);
        grade_picker.setOnValueChangedListener(onValueChangeListener);
        this.setOutsideTouchable(true);
        this.setContentView(mMenuView);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.mypopwindow_anim_style);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundMengban(mcontext, 1f);
            }
        });
    }

    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值 透明
                    pf.set(picker, new ColorDrawable(MyApplication.getGlobalContext().getResources().getColor(android.R.color.transparent)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void backgroundMengban(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

}
