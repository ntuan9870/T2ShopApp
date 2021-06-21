package com.example.t2shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Model.Comment;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseMessage;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<Comment> arrComment;
    private User user;

    public CommentAdapter(Context context, List<Comment> arrComment) {
        this.context = context;
        this.arrComment = arrComment;
        this.user = T2ShopDatabase.getInstance(context).userDAO().getItems();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_name_customer.setText(arrComment.get(position).getUser_name());
        holder.txt_content_comment.setText(arrComment.get(position).getComment_content());
        if (user!=null){
            holder.btn_delete.setVisibility(View.VISIBLE);
        }else{
            holder.btn_delete.setVisibility(View.INVISIBLE);
        }
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.compositeDisposable.add(Common.it2ShopAPI.removeComment(arrComment.get(position).getComment_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(ResponseMessage message) throws Exception {
                        if (message.getMessage()!=null){
                            Common2.showDialogAutoClose(context, "Xóa thành công!");
                            arrComment.remove(position);
                            notifyDataSetChanged();
                        }else{
                            Common2.showDialogAutoClose(context, "Thất bại!");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Common2.showDialogAutoClose(context, "Vui lòng kiểm tra kết nối Internet!");
                    }
                }));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrComment.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name_customer, txt_content_comment, btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_customer = itemView.findViewById(R.id.txt_name_customer);
            txt_content_comment = itemView.findViewById(R.id.txt_content_comment);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
