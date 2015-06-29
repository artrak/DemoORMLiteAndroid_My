package com.art.rss.demoormliteandroid_my.SQLite_ORM;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.art.rss.demoormliteandroid_my.R;
import com.art.rss.demoormliteandroid_my.SQLite_ORM.entity.Category_entity;
import com.art.rss.demoormliteandroid_my.SQLite_ORM.entity.Module_entity;
import com.art.rss.demoormliteandroid_my.SQLite_ORM.entity.Source_entity;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.j256.ormlite.dao.ForeignCollection;

import java.util.List;

public class ORMLiteActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

	private ListView listView;
	private ModuleAdaptor listAdapter;
	private Repository repository;

	private List<Module_entity> modules;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.repository = new Repository(getHelper());

		// Simply clear down all test data on every run
		this.repository.clearData();

		createFakeEntriesTEST();										 // add test Persons

		this.modules = this.repository.getModules();

		findAndCreateAllViews();

		this.listAdapter = new ModuleAdaptor(this, R.layout.person_data_row, this.modules);

		this.listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long duration) {
				final Module_entity module = ORMLiteActivity.this.listAdapter.getItem(position);
				final ForeignCollection<Category_entity> categories = module.getCategories();
				final StringBuilder appList = new StringBuilder();
				for (final Category_entity category : categories) {
					appList.append(category.getName())
							.append("\n");
				}
				new AlertDialog.Builder(ORMLiteActivity.this).setTitle(
						String.format("%s has a total of %s Apps", module.getName(), categories.size()))
						.setMessage(appList.toString())
						.show();
			}
		});

		this.listView.setAdapter(this.listAdapter);
		registerForContextMenu(this.listView);
	}

	private static final int MENU_ADD_APP = Menu.FIRST;
	private static final int MENU_DELETE_PERSON = Menu.FIRST + 1;
	private static final int MENU_EDIT_PERSON = Menu.FIRST + 2;

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenuInfo menuInfo) {
		if (v.getId() == getListView().getId()) {
			final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			final Module_entity module = this.modules.get(info.position);
			menu.setHeaderTitle(module.getId() + " | " + module.getName());
			menu.add(Menu.NONE, MENU_ADD_APP, MENU_ADD_APP, "Add App");
			menu.add(Menu.NONE, MENU_DELETE_PERSON, MENU_DELETE_PERSON, "Delete Person");
			menu.add(Menu.NONE, MENU_EDIT_PERSON, MENU_EDIT_PERSON, "Edit Person");
		}
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		final int menuItemIndex = item.getItemId();
		final Module_entity module = this.modules.get(info.position);

		final LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, null);
		final EditText editText = (EditText) textEntryView.findViewById(R.id.edit_text_dialog);

		switch (menuItemIndex) {
			case MENU_ADD_APP:
				new AlertDialog.Builder(this).setTitle("Add App")
						.setView(textEntryView)
						.setPositiveButton("Add", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog, final int whichButton) {
								final Category_entity category = new Category_entity();
								category.setModule(module);
								category.setName(editText.getText()
										.toString());
								ORMLiteActivity.this.repository.saveOrUpdateCategory(category);
								ORMLiteActivity.this.listAdapter.notifyDataSetChanged();
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog, final int whichButton) {
							/* User clicked cancel so do some stuff */
							}
						})
						.show();
				break;
			case MENU_DELETE_PERSON:
				this.modules.remove(info.position);
				this.repository.deleteModule(module);
				this.listAdapter.notifyDataSetChanged();
				break;
			case MENU_EDIT_PERSON:
				// Set name for editing
				editText.setText(module.getName());

				new AlertDialog.Builder(this).setTitle("Edit Person")
						.setView(textEntryView)
						.setPositiveButton("Update", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog, final int whichButton) {
								module.setName(editText.getText()
										.toString());
								ORMLiteActivity.this.repository.saveOrUpdateModule(module);
								ORMLiteActivity.this.listAdapter.notifyDataSetChanged();
							}
						})
						.show();
				break;
			default:
				break;
		}
		return true;
	}

	//--------------------------------------------test-----------------------------
	private void createFakeEntriesTEST() {
		// Create Two test Module
		final Module_entity module = new Module_entity();
		module.setName("James");
		this.repository.saveOrUpdateModule(module);

		final Module_entity module2 = new Module_entity();
		module2.setName("Jimmy");
		this.repository.saveOrUpdateModule(module2);

		// Create two test categors
		final Category_entity category = new Category_entity();
		category.setName("categoryName");
		category.setModule(module);
		this.repository.saveOrUpdateCategory(category);

		final Category_entity category2 = new Category_entity();
		category2.setName("category2Name");
		category2.setModule(module2);
		this.repository.saveOrUpdateCategory(category2);

		final Category_entity category3 = new Category_entity();
		category3.setName("category2Name");
		category3.setModule(module2);
		this.repository.saveOrUpdateCategory(category3);

		// Create two test srcs
		final Source_entity sourceEntity = new Source_entity();
		sourceEntity.setName("sourceName");
		sourceEntity.setUrl("www.android.com");
		sourceEntity.setXml("http://");
		sourceEntity.setTitle("NewsMy");
		sourceEntity.setDescription("Description");
		sourceEntity.setCategory(category);
		this.repository.saveOrUpdateSourse(sourceEntity);
	}
	//--------------------------------------------test-----------------------------

	public void findAndCreateAllViews() {
		this.listView = getListView();
		registerForContextMenu(this.listView);
	}
}
