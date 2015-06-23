package com.art.rss.demoormliteandroid_my.demo;

import android.util.Log;

import com.art.rss.demoormliteandroid_my.demo.domain.App;
import com.art.rss.demoormliteandroid_my.demo.domain.Person;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DemoRepository {
	private final static String LOG_TAG = "DemoRepository";

	private Dao<App, Integer> appDao;
	private Dao<Person, Integer> personDao;

	public DemoRepository(final DatabaseHelper databaseHelper) {
		this.personDao = getPersonDao(databaseHelper);
		this.appDao = getAppDao(databaseHelper);
	}

	public void clearData() {
		final List<Person> persons = getPersons();
		for (final Person person : persons) {
			deletePerson(person);
		}
	}

	public List<Person> getPersons() {
		try {
			return this.personDao.queryForAll();
		}
		catch (final SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Person>();
	}

	public void saveOrUpdatePerson(final Person person) {
		try {
			this.personDao.createOrUpdate(person);
		}
		catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveOrUpdateApp(final App app) {
		try {
			this.appDao.createOrUpdate(app);
		}
		catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletePerson(final Person person) {
		try {
			final ForeignCollection<App> apps = person.getApps();
			for (final App app : apps) {
				this.appDao.delete(app);
			}
			this.personDao.delete(person);
		}
		catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	private Dao<Person, Integer> getPersonDao(final DatabaseHelper databaseHelper) {
		if (null == this.personDao) {
			try {
				this.personDao = databaseHelper.getPersonDao();
			}
			catch (final SQLException e) {
				Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return this.personDao;
	}

	private Dao<App, Integer> getAppDao(final DatabaseHelper databaseHelper) {
		if (null == this.appDao) {
			try {
				this.appDao = databaseHelper.getAppDao();
			}
			catch (final SQLException e) {
				Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return this.appDao;
	}
}
