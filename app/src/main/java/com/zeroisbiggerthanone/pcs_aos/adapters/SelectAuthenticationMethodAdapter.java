package com.zeroisbiggerthanone.pcs_aos.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeroisbiggerthanone.pcs_aos.R;
import com.zeroisbiggerthanone.pcs_aos.models.AuthenticationMethod;

import java.util.ArrayList;
import java.util.List;


public class SelectAuthenticationMethodAdapter extends
        RecyclerView.Adapter<SelectAuthenticationMethodAdapter.SelectAuthenticationMethodViewHolder> {

    private final List<AuthenticationMethod> mAuthenticationMethods;
    private final SparseBooleanArray mSelectedItems;

    public SelectAuthenticationMethodAdapter(List<AuthenticationMethod> friends) {
        mAuthenticationMethods = friends;
        mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public SelectAuthenticationMethodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_authentication_method, parent, false);

        return new SelectAuthenticationMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectAuthenticationMethodViewHolder holder, int position) {
        final AuthenticationMethod authenticationMethod = mAuthenticationMethods.get(position);

        holder.mAuthenticationMethod.setText(authenticationMethod.getName());

        holder.itemView.setActivated(mSelectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return mAuthenticationMethods.size();
    }

    @SuppressWarnings("unused")
    public void toggleSelection(int pos) {
        if (mSelectedItems.get(pos, false)) {
            mSelectedItems.delete(pos);
        } else {
            mSelectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void singleSelection(int pos) {
        clearSelections();

        setSelected(pos);
    }

    private void setSelected(int pos) {
        mSelectedItems.put(pos, true);
        notifyItemChanged(pos);
    }

    @SuppressWarnings("unused")
    public void clearSelection(int pos) {
        if (mSelectedItems.get(pos, false)) {
            mSelectedItems.delete(pos);
        }
        notifyItemChanged(pos);
    }

    private void clearSelections() {
        if (mSelectedItems.size() > 0) {
            mSelectedItems.clear();
            notifyDataSetChanged();
        }
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    private List<Integer> getSelectedItemsPositions() {
        List<Integer> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }

    public List<AuthenticationMethod> getSelectedAuthenticationMethods() {
        final List<Integer> selectedItems = getSelectedItemsPositions();
        final List<AuthenticationMethod> authenticationMethods = new ArrayList<>();

        for (int i = 0; i < selectedItems.size(); i++) {
            authenticationMethods.add(mAuthenticationMethods.get(selectedItems.get(i)));
        }

        return authenticationMethods;
    }

    public List<AuthenticationMethod> getAuthenticationMethods() {
        return mAuthenticationMethods;
    }

    class SelectAuthenticationMethodViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAuthenticationMethod;

        SelectAuthenticationMethodViewHolder(View itemView) {
            super(itemView);

            mAuthenticationMethod = (TextView) itemView.findViewById(R.id.tv_authentication_method);
        }
    }
}
