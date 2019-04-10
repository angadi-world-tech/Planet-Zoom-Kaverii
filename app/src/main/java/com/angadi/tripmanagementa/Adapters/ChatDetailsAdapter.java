package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Model.ChatDetailsPojo;
import com.angadi.tripmanagementa.R;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatDetailsAdapter extends BaseAdapter
{

    TextView TexTviewSenderMsg;

    ArrayList<ChatDetailsPojo> pojo_listArrayList;
    Context context;
    LayoutInflater layoutInflater;

    String id,chat_id,user_id,type,message,status,created_at;

    LinearLayout LinlayoutReciever,LinLayoutSenderMsg,LinLayoutSenderTime,LinlayoutSenderAttachFiles,LinLayoutSenderSeen;

    CircleImageView ImageViewRecieverPhoto;



    public ChatDetailsAdapter(Context context,ArrayList<ChatDetailsPojo> pojo_listArrayList,String id,String chat_id,String user_id,String type, String message,String status,String created_at)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.pojo_listArrayList = pojo_listArrayList;
        this.id = id;
        this.chat_id = chat_id;
        this.user_id = user_id;
        this.type = type;
        this.message = message;
        this.status = status;
        this.created_at = created_at;
    }

    @Override
    public int getCount()
    {
        return pojo_listArrayList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return i;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        view = layoutInflater.inflate(R.layout.chat_details_adapter_layout, null);

        TextView TextViewSendertime = (TextView)view.findViewById(R.id.TextViewSendertime);
        TextView TextviewReciverTime = (TextView)view.findViewById(R.id.TextviewReciverTime);
        TextView TextviewRecieverMsg = (TextView)view.findViewById(R.id.TextviewRecieverMsg);
        ImageViewRecieverPhoto = (CircleImageView)view.findViewById(R.id.ImageViewRecieverPhoto);
        Typeface montserrat_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");


        TexTviewSenderMsg = (TextView)view.findViewById(R.id.TexTviewSenderMsg);
        LinlayoutReciever = (LinearLayout)view.findViewById(R.id.LinlayoutReciever);
        LinLayoutSenderMsg = (LinearLayout)view.findViewById(R.id.LinLayoutSenderMsg);
        LinLayoutSenderTime = (LinearLayout)view.findViewById(R.id.LinLayoutSenderTime);
        LinLayoutSenderSeen = (LinearLayout)view.findViewById(R.id.LinLayoutSenderSeen);
        LinlayoutSenderAttachFiles = (LinearLayout)view.findViewById(R.id.LinlayoutSenderAttachFiles);
        LinearLayout  LinlayoutRecieverAttachFiles = (LinearLayout)view.findViewById(R.id.LinlayoutRecieverAttachFiles);


        ImageView ImageviewForSenderattachedFiles = (ImageView)view.findViewById(R.id.ImageviewForSenderattachedFiles);
        ImageView ImageviewForRecieverattachedFiles = (ImageView)view.findViewById(R.id.ImageviewForRecieverattachedFiles);



        TexTviewSenderMsg.setTypeface(montserrat_regular);
        TextViewSendertime.setTypeface(montserrat_regular);
        TextviewRecieverMsg.setTypeface(montserrat_regular);
        TextviewReciverTime.setTypeface(montserrat_regular);

        String Sender = TripManagement.readValueFromPreferences(context,"USERID","");


        String TimeStamp = pojo_listArrayList.get(i).getCreated_at();

        Log.e("TimeStamp1",TimeStamp.substring(0, TimeStamp.indexOf(' ')));
        Log.e("TimeStamp2",TimeStamp.substring(TimeStamp.indexOf(' ') + 1));



        try {
            String _24HourTime = pojo_listArrayList.get(i).getCreated_at();
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("yy-MM-DD HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            Log.d("TIME",_24HourDt.toString());
            Log.d("TIMEE",_12HourSDF.format(_24HourDt));
            Log.d("Balayya",new SimpleDateFormat("hh:mm a").format(_24HourDt));
            TextViewSendertime.setText(new SimpleDateFormat("hh:mm a").format(_24HourDt));
            TextviewReciverTime.setText(new SimpleDateFormat("hh:mm a").format(_24HourDt));

            // System.out.println(_12HourSDF.format(_24HourDt));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (Sender.equals(pojo_listArrayList.get(i).getUser_id()))
        {
            LinlayoutReciever.setVisibility(View.GONE);


            if (pojo_listArrayList.get(i).getType().matches("1"))
            {

                LinlayoutSenderAttachFiles.setVisibility(View.VISIBLE);
                LinLayoutSenderTime.setVisibility(View.VISIBLE);
                Picasso.with(context).load(pojo_listArrayList.get(i).getMessage()).placeholder(R.mipmap.gallery_backgroung).into(ImageviewForSenderattachedFiles);
                Log.e("LINK",pojo_listArrayList.get(i).getMessage());
                LinLayoutSenderMsg.setVisibility(View.GONE);
                TexTviewSenderMsg.setVisibility(View.GONE);

            }
            else if (pojo_listArrayList.get(i).getType().matches("0"))
            {

                String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava((pojo_listArrayList.get(i).getMessage()));
                TexTviewSenderMsg.setText(fromServerUnicodeDecoded);
                ImageviewForSenderattachedFiles.setVisibility(View.GONE);
                LinlayoutSenderAttachFiles.setVisibility(View.GONE);
                LinLayoutSenderMsg.setVisibility(View.VISIBLE);
                TexTviewSenderMsg.setVisibility(View.VISIBLE);
                LinLayoutSenderTime.setVisibility(View.VISIBLE);

                if (pojo_listArrayList.get(i).getRead_at() != "")
                {
                    LinLayoutSenderSeen.setVisibility(View.VISIBLE);
                }

            }
            }
        else
        {
            LinlayoutReciever.setVisibility(View.VISIBLE);
            LinLayoutSenderMsg.setVisibility(View.GONE);
            LinLayoutSenderTime.setVisibility(View.GONE);

            if (pojo_listArrayList.get(i).getType().matches("1"))
            {
                LinlayoutRecieverAttachFiles.setVisibility(View.VISIBLE);
                Picasso.with(context).load(pojo_listArrayList.get(i).getMessage()).placeholder(R.mipmap.gallery_backgroung).into(ImageviewForRecieverattachedFiles);
                TextviewRecieverMsg.setVisibility(View.GONE);
            }
            else
                {
                    TextviewRecieverMsg.setVisibility(View.VISIBLE);
                    LinlayoutRecieverAttachFiles.setVisibility(View.GONE);
                    String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(pojo_listArrayList.get(i).getMessage());
                    TextviewRecieverMsg.setText(fromServerUnicodeDecoded);
            }
        }

        return view;
    }

}
