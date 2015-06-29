package com.art.rss.demoormliteandroid_my.SQLite_ORM.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "categotys")
public class Category_entity {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = true)
	private String name;

	//<-------------<-------------<---------------<---------------<
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "module_id")
	private Module_entity module;
	//<-------------<-------------<---------------<---------------<

	//>------------->------------->--------------->--------------->
	@ForeignCollectionField
	private ForeignCollection<Source_entity> sources;

	public ForeignCollection<Source_entity> getSources() {
		return this.sources;
	}
	//>------------->------------->--------------->--------------->

	public Category_entity() {
		// all persisted classes must define a no-arg constructor with at least package visibility
	}

	public Category_entity(final String name, final Module_entity module) {
		this.module = module;
		this.name = name;
	}



	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setModule(final Module_entity module) {
		this.module = module;
	}

	public Module_entity getModule() {
		return this.module;
	}

	public final boolean hasId() {
		return 0 != this.id;
	}
}
