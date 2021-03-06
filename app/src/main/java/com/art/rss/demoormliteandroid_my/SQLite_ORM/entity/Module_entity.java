package com.art.rss.demoormliteandroid_my.SQLite_ORM.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "modeles")
public class Module_entity {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = true)
	private String name;

	//>------------->------------->--------------->--------------->
	@ForeignCollectionField
	private ForeignCollection<Category_entity> categories;

	public ForeignCollection<Category_entity> getCategories() {
		return this.categories;
	}
	//>------------->------------->--------------->--------------->

	public Module_entity() {
		// all persisted classes must define a no-arg constructor with at least package visibility
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

	public final boolean hasId() {
		return 0 != this.id;
	}
}
