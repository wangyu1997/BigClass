package com.njtech.bigclass.PopUpWindow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.njtech.bigclass.R;
import com.njtech.bigclass.utils.MyApplication;


/**
 * Created by wangyu on 8/27/16.
 */

public class Action_PopUp extends PopupWindow {
    private Activity mContext;
    private View mMenuView;
    protected Button statue_btn,change_btn,delete_btn, cancel_btn;

    public Action_PopUp(String state,final Activity mcontext, View.OnClickListener itemsOnClick) {
        this.mContext = mcontext;
        backgroundMengban(mcontext, 0.4f);
        mMenuView = LayoutInflater.from(mcontext).inflate(R.layout.action_popup, null);
        cancel_btn = (Button) mMenuView.findViewById(R.id.cancel_btn);
        statue_btn = (Button) mMenuView.findViewById(R.id.statue_btn);
        change_btn = (Button) mMenuView.findViewById(R.id.change_btn);
        delete_btn = (Button) mMenuView.findViewById(R.id.delete_btn);
        statue_btn.setOnClickListener(itemsOnClick);
        change_btn.setOnClickListener(itemsOnClick);
        delete_btn.setOnClickListener(itemsOnClick);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if(state.equals("0"))
            statue_btn.setText("上课");
        if(state.equals("1"))
            statue_btn.setText("下课");
        if(state.equals("2")) {
            statue_btn.setText("已结束");
            statue_btn.setTextColor(MyApplication.getGlobalContext().getResources().getColor(R.color.textcolor4));
            statue_btn.setEnabled(false);
        }
        this.setOutsideTouchable(true);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float height = mMenuView.findViewById(R.id.command_popup).getTop();
                float y = event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
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


    public void backgroundMengban(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
