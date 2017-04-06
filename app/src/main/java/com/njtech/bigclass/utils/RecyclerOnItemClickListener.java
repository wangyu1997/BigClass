package com.njtech.bigclass.utils;

import android.view.View;

/**
 * Created by ritchiehuang on 4/6/17.
 */

public interface RecyclerOnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
