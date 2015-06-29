package com.art.rss.demoormliteandroid_my.SQLite_ORM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.art.rss.demoormliteandroid_my.SQLite_ORM.entity.Category_entity;
import com.art.rss.demoormliteandroid_my.SQLite_ORM.entity.Item_entity;
import com.art.rss.demoormliteandroid_my.SQLite_ORM.entity.Module_entity;
import com.art.rss.demoormliteandroid_my.SQLite_ORM.entity.Source_entity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "DemoORM.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 2;

	public DatabaseHelper(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// the DAO object we use to access the SimpleData table
	private Dao<Module_entity, Integer> moduleDao = null;
	private Dao<Category_entity, Integer> categDao = null;
	private Dao<Source_entity, Integer> srcDao = null;
	private Dao<Item_entity, Integer> itemDao = null;

	@Override
	public void onCreate(final SQLiteDatabase db, final ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Category_entity.class);
			TableUtils.createTable(connectionSource, Module_entity.class);
			TableUtils.createTable(connectionSource, Source_entity.class);
			TableUtils.createTable(connectionSource, Item_entity.class);
		}
		catch (final SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust the various data to
	 * match the new version number.
	 */
	@Override
	public void onUpgrade(final SQLiteDatabase db, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Category_entity.class, true);
			TableUtils.dropTable(connectionSource, Module_entity.class, true);
			TableUtils.dropTable(connectionSource, Source_entity.class, true);
			TableUtils.dropTable(connectionSource, Item_entity.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		}
		catch (final SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}



	public Dao<Module_entity, Integer> getModuleDao() throws SQLException {
		if (this.moduleDao == null) {
			this.moduleDao = getDao(Module_entity.class);
		}
		return this.moduleDao;
	}

	public Dao<Category_entity, Integer> getCategoryDao() throws SQLException {
		if (this.categDao == null) {
			this.categDao = getDao(Category_entity.class);
		}
		return this.categDao;
	}

	public Dao<Source_entity, Integer> getSourceDao() throws SQLException {
		if (this.srcDao == null) {
			this.srcDao = getDao(Source_entity.class);
		}
		return this.srcDao;
	}

	public Dao<Item_entity, Integer> getItemDao() throws SQLException {
		if (this.itemDao == null) {
			this.itemDao = getDao(Item_entity.class);
		}
		return this.itemDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		this.categDao = null;
		this.moduleDao = null;
		this.srcDao = null;
		this.itemDao = null;
	}

}
