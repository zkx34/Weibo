package com.zkx.weipo.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zkx.weipo.app.R;
import com.zkx.weipo.app.app.WeiboApplication;
import com.zkx.weipo.app.openapi.models.Comment;
import com.zkx.weipo.app.openapi.models.CommentList;
import com.zkx.weipo.app.util.Tools;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/21.
 */
public class DetailPageListViewAdapter extends BaseAdapter {

    private List<Comment> mComments;
    private LayoutInflater mInflater;
    private Context context;

    public DetailPageListViewAdapter(Context context,CommentList commentList) {
        this.context=context;
        this.mComments = commentList.commentList;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mComments==null?0:mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments==null?null:mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(mComments.get(position).id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView==null){
            v=mInflater.inflate(R.layout.item_detail,null);
        }
        else {
            v=convertView;
        }
        ViewHolder holder=new ViewHolder();
        holder.user_profile=(de.hdodenhof.circleimageview.CircleImageView)v.findViewById(R.id.user_profile);
        holder.tv_username=(TextView)v.findViewById(R.id.tv_username);
        holder.tv_createdAt=(TextView)v.findViewById(R.id.tv_createdAt);
        holder.de_detail=(TextView)v.findViewById(R.id.de_detail);

        if (mComments!=null){
            holder.tv_username.setText(mComments.get(position).user.name);
            WeiboApplication.IMAGE_CACHE.get(mComments.get(position).user.profile_image_url,holder.user_profile);
            holder.tv_createdAt.setText(Tools.getTimeStr(Tools.strToDate(mComments.get(position).created_at), new Date()));
            holder.de_detail.setText(mComments.get(position).text);
        }
        return v;
    }

    private static class ViewHolder{
        de.hdodenhof.circleimageview.CircleImageView user_profile;
        TextView tv_username;
        TextView tv_createdAt;
        TextView de_detail;
    }
}