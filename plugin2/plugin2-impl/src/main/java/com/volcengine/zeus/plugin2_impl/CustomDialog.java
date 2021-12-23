package com.volcengine.zeus.plugin2_impl;

import android.app.Dialog;
import android.content.Context;

/**
 * @author xuekai
 * @date 7/29/21
 */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }
//    public CustomDialog( Context context) {
//        super(context);
//        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_plugin2, null);
//        setContentView(inflate);
//        RecyclerView viewById = (RecyclerView) findViewById(R.id.recyclerview);
//        viewById.setLayoutManager(new LinearLayoutManager(context));
//        viewById.setAdapter(new RecyclerView.Adapter() {
//            
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
//                return new RecyclerView.ViewHolder(new Button(viewGroup.getContext())) {
//                    @Override
//                    public String toString() {
//                        return super.toString();
//                    }
//                };
//            }
//
//            @Override
//            public void onBindViewHolder( RecyclerView.ViewHolder viewHolder, int i) {
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
