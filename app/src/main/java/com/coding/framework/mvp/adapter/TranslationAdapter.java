package com.coding.framework.mvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coding.framework.mvp.R;
import com.coding.framework.mvp.data.network.model.TranslationResponse;
import com.coding.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jesus Morales on 10-08-2018.
 */

public class TranslationAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<TranslationResponse> mTranslationResponse;

    public TranslationAdapter(List<TranslationResponse> translationResponse) {
        mTranslationResponse = translationResponse;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTranslationResponse != null && mTranslationResponse.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mTranslationResponse != null && mTranslationResponse.size() > 0) {
            return mTranslationResponse.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<TranslationResponse> translationResponse) {
        mTranslationResponse.clear();
        mTranslationResponse.addAll(translationResponse);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onBlogEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.target)
        TextView targetTextView;

        @BindView(R.id.translation)
        TextView translationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            targetTextView.setText("");
            translationTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final TranslationResponse translationResponse = mTranslationResponse.get(position);


            if (targetTextView.getText() != null) {
                targetTextView.setText(translationResponse.getLang());
            }

            if (translationTextView.getText() != null) {
                translationTextView.setText(translationResponse.getData().get(0));
            }

        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_message)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

    }
}
