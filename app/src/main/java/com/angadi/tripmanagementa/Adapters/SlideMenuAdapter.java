package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Model.SlideMenuItem;
import com.angadi.tripmanagementa.R;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by alexey.herashchenko on 09.07.2015.
 */
public class SlideMenuAdapter extends GenericAdapter<SlideMenuItem> {

    private Context mContext;
    private ViewHolder viewHolder;
    private ISlideMenuItemClickListener slideMenuItemClickListener;



    public SlideMenuAdapter(Context context, List<SlideMenuItem> objects,ISlideMenuItemClickListener listener) {
        super(context, objects);
        this.mContext = context;
        this.slideMenuItemClickListener = listener;

    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_slide_menu, parent, false);
            ViewHolder holder = new ViewHolder(convertView);

            holder.menuClick = new MenuClick();
            convertView.setOnClickListener(holder.menuClick);
            convertView.setTag(holder);
            viewHolder = holder;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SlideMenuItem menuItem = getItem(position);
        viewHolder.menuItemIcon.setImageResource(menuItem.getItemImage());
        if (!menuItem.getItemTitle().isEmpty())
        {
            viewHolder.menuItemTitle.setText(menuItem.getItemTitle());
        }


        setItemSelector(convertView, position);
        viewHolder.menuClick.setPosition(position);

        return convertView;
    }

    private void setItemSelector(View view, final int position)
    {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                SlideMenuItem item = getItem(position);
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    viewHolder = (ViewHolder) v.getTag();
                    viewHolder.menuItemIcon.setImageResource(item.getImagePressed());
                    viewHolder.menuItemTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    viewHolder = (ViewHolder) v.getTag();
                    viewHolder.menuItemIcon.setImageResource(item.getItemImage());
                    viewHolder.menuItemTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    viewHolder = (ViewHolder) v.getTag();
                    viewHolder.menuItemIcon.setImageResource(item.getItemImage());
                    viewHolder.menuItemTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                    v.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    static class ViewHolder
    {

        @BindView(R.id.slide_menu_item_icon)
        ImageView menuItemIcon;

        @BindView(R.id.slide_menu_item_title)
        TextView menuItemTitle;

        MenuClick menuClick;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public class MenuClick implements View.OnClickListener
    {

        private int mPosition;

        public void setPosition(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(@NonNull View v)
        {
            if (slideMenuItemClickListener != null)
            {
                slideMenuItemClickListener.onMenuItemClick(mPosition);
            }
        }
    }


    public interface ISlideMenuItemClickListener {
        void onMenuItemClick(int position);
    }





}
