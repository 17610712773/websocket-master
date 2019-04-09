package wandroid.group4.com.myapplication.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wandroid.group4.com.myapplication.R;
import wandroid.group4.com.myapplication.bean.LoginBean;
import wandroid.group4.com.myapplication.bean.MessageBean;

/**
 * @author T-sy
 * @description:
 * @date :2019/4/9 19:11
 */
public class Recyadapter extends RecyclerView.Adapter {

    Context context;
    List<Object> mObjectList;

    public Recyadapter(Context context, List<Object> objectList) {
        this.context = context;
        mObjectList = objectList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recy, viewGroup, false);
        My1 my1 = new My1(view);
        return my1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        My1 my1 = (My1) viewHolder;

        Object o = mObjectList.get(i);
        MessageBean messageBean = (MessageBean) mObjectList.get(i);
        my1.mTv1.setText(messageBean.getUsername() + ":");
        my1.mTv.setText(messageBean.getContent());
    }

    @Override
    public int getItemCount() {
        return mObjectList.size();
    }

    class My1 extends RecyclerView.ViewHolder {

        private final TextView mTv;
        private final TextView mTv1;

        public My1(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.recy_tv);
            mTv1 = itemView.findViewById(R.id.recy_tv1);
        }
    }
}
