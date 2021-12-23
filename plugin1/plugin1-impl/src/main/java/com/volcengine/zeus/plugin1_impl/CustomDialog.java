package com.volcengine.zeus.plugin1_impl;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author xuekai
 * @date 7/29/21
 */
public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context) {
        super(context);
    }
//    public CustomDialog(@NonNull Context context) {
//        super(context);
//        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_plugin1, null);
//        setContentView(inflate);
//        RecyclerView viewById = (RecyclerView) findViewById(R.id.recyclerview);
//        viewById.setLayoutManager(new LinearLayoutManager(context));
//        viewById.setAdapter(new RecyclerView.Adapter() {
//            @NonNull
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                return new RecyclerView.ViewHolder(new Button(viewGroup.getContext())) {
//                    @Override
//                    public String toString() {
//                        return super.toString();
//                    }
//                };
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//                ((Button) viewHolder.itemView).setText(i + "aaa");
//            }
//
//            @Override
//            public int getItemCount() {
//                return 10;
//            }
//        });
//    }
}
