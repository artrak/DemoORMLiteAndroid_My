package com.art.rss.demoormliteandroid_my.demo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.art.rss.demoormliteandroid_my.R;
import com.art.rss.demoormliteandroid_my.demo.entity.Module_entity;

import java.util.List;

public class ModuleAdaptor extends ArrayAdapter<Module_entity> {

	private final String LOG_TAG = "PersonAdapter";

	private List<Module_entity> modules = null;
	private Activity context = null;

	public ModuleAdaptor(final Activity context, final int textViewResourceId, final List<Module_entity> modules) {
		super(context, textViewResourceId, modules);
		this.context = context;
		this.modules = modules;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			final LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.person_data_row, null);

			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.numberOfApps = (TextView) view.findViewById(R.id.number_of_apps);
			view.setTag(holder);
		}
		else {
			holder = (ViewHolder) view.getTag();
		}

		final Module_entity module = this.modules.get(position);
		Log.d(this.LOG_TAG, module.getName());

		if (module != null) {
			final TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(module.getName());

			final TextView numOfApps = (TextView) view.findViewById(R.id.number_of_apps);
			numOfApps.setText(Integer.toString(module.getCategories()
					.size()));
		}

		return view;
	}

	class ViewHolder {
		TextView name;
		TextView numberOfApps;
	}

}
