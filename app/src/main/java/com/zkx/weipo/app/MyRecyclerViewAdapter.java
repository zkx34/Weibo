package com.zkx.weipo.app;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zkx.weipo.app.Util.StringUtil;
import com.zkx.weipo.app.Util.Tools;
import com.zkx.weipo.app.imgCache.SimpleImageLoader;
import com.zkx.weipo.app.openapi.models.StatusList;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/12.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    StatusList testDatas;

    public MyRecyclerViewAdapter(StatusList testDatas) {
        this.testDatas = testDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.content.setText(Html.fromHtml(Tools.atBlue(testDatas.statusList.get(i).text)));
        viewHolder.name.setText(testDatas.statusList.get(i).user.name);
        viewHolder.time.setText(Tools.getTimeStr(Tools.strToDate(testDatas.statusList.get(i).created_at), new Date()));
        viewHolder.source.setText("来自:"+testDatas.statusList.get(i).getTextSource());
        SimpleImageLoader.showImg(viewHolder.userhead, testDatas.statusList.get(i).user.profile_image_url);

        //判断微博中是否有图片
        if (!StringUtil.isEmpty(testDatas.statusList.get(i).thumbnail_pic)){
            viewHolder.content_img.setVisibility(View.VISIBLE);
            SimpleImageLoader.showImg(viewHolder.content_img,testDatas.statusList.get(i).thumbnail_pic);
        }else {
            viewHolder.content_img.setVisibility(View.GONE);
        }

        //转发内容是否为空
        if (testDatas.statusList.get(i).retweeted_status!=null
                &&testDatas.statusList.get(i).retweeted_status.user!=null){
            viewHolder.insideContent.setVisibility(View.VISIBLE);
            viewHolder.retweeted_detail.setText(Html.fromHtml(Tools.atBlue("@"+testDatas.statusList.get(i).retweeted_status.user.name+
                    ":"+testDatas.statusList.get(i).retweeted_status.text)));

            //转发图片是否有图片
            if (!StringUtil.isEmpty(testDatas.statusList.get(i).retweeted_status.thumbnail_pic)){
                viewHolder.retweeted_img.setVisibility(View.VISIBLE);
                SimpleImageLoader.showImg(viewHolder.retweeted_img,testDatas.statusList.get(i).retweeted_status.thumbnail_pic);
            }else {
                viewHolder.retweeted_img.setVisibility(View.GONE);
            }
        }else {
            viewHolder.insideContent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return testDatas==null?0:testDatas.statusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout insideContent;
        CardView cardView;
        TextView content;
        TextView name;
        TextView time;
        TextView source;
        TextView retweeted_detail;
        ImageView userhead;
        ImageView content_img;
        ImageView retweeted_img;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.id_CardView);
            content=(TextView)itemView.findViewById(R.id.id_content);
            userhead=(ImageView)itemView.findViewById(R.id.user_headimg);
            name=(TextView)itemView.findViewById(R.id.id_name);
            time=(TextView)itemView.findViewById(R.id.id_time);
            source=(TextView)itemView.findViewById(R.id.id_source);
            insideContent =(LinearLayout)itemView.findViewById(R.id.inside_content);
            retweeted_detail=(TextView)itemView.findViewById(R.id.id_retweeted_detail);
            content_img=(ImageView)itemView.findViewById(R.id.content_img);
            retweeted_img=(ImageView)itemView.findViewById(R.id.retweeted_img);
        }
    }
}
