package com.zen.android.rx.spy;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TestCaseFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private String[] TITLES  = {"Create", "Timer"};
    private Class[]  CLASSES = new Class[]{RxCreateActivity.class, RxTimerActivity.class};

    public TestCaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_case, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_test);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(new CaseAdapter(Arrays.asList(TITLES), this));
    }

    @Override
    public void onClick(View view) {
        Integer data = (Integer) view.getTag();
        if (data != null) {
            startActivity(new Intent(getActivity(), CLASSES[data]));
        }
    }


    static class CaseAdapter extends RecyclerView.Adapter<CaseViewHolder> {

        List<String>         mData;
        View.OnClickListener mClickListener;

        public CaseAdapter(List<String> data, View.OnClickListener listener) {
            mData = data;
            mClickListener = listener;
        }

        @Override
        public CaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            item.setOnClickListener(mClickListener);
            return new CaseViewHolder(item);
        }

        @Override
        public void onBindViewHolder(CaseViewHolder holder, int position) {
            if (mData == null || mData.size() <= position) {
                return;
            }
            holder.populateView(mData.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }

    static class CaseViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        View     mRoot;

        public CaseViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView;
            mTvTitle = (TextView) itemView.findViewById(android.R.id.text1);
        }

        void populateView(String data, Object tag) {
            mRoot.setTag(tag);
            mTvTitle.setText(data);
        }
    }

}
