package com.art.rss.demoormliteandroid_my.demo;

import android.util.Log;

import com.art.rss.demoormliteandroid_my.demo.entity.Category_entity;
import com.art.rss.demoormliteandroid_my.demo.entity.Item_entity;
import com.art.rss.demoormliteandroid_my.demo.entity.Module_entity;
import com.art.rss.demoormliteandroid_my.demo.entity.Source_entity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Repository {
    private final static String LOG_TAG = "DemoRepository";

    private Dao<Module_entity, Integer> moduleDao;
    private Dao<Category_entity, Integer> categDao;
    private Dao<Source_entity, Integer> srcDao;
    private Dao<Item_entity, Integer> itemDao;

    public Repository(final DatabaseHelper databaseHelper) {
        this.moduleDao = getModuleDao(databaseHelper);
        this.categDao = getAppDao(databaseHelper);
        this.srcDao = getSrcDao(databaseHelper);
        this.itemDao = getItemDao(databaseHelper);
    }

    public void clearData() {
        final List<Module_entity> modules = getModules();
        for (final Module_entity module : modules) {
            deleteModule(module);
        }
        final List<Category_entity> categorys = getCategorys();
        for (final Category_entity category : categorys) {
            deleteCategory(category);
        }
        final List<Source_entity> sources = getSources();
        for (final Source_entity source : sources) {
            deleteSource(source);
        }
    }

    public List<Module_entity> getModules() {
        try {
            return this.moduleDao.queryForAll();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Module_entity>();
    }

    public List<Category_entity> getCategorys() {
        try {
            return this.categDao.queryForAll();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Category_entity>();
    }

    public List<Source_entity> getSources() {
        try {
            return this.srcDao.queryForAll();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Source_entity>();
    }


    public void saveOrUpdateModule(final Module_entity module) {
        try {
            this.moduleDao.createOrUpdate(module);
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateCategory(final Category_entity category) {
        try {
            this.categDao.createOrUpdate(category);
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateSourse(final Source_entity source) {
        try {
            this.srcDao.createOrUpdate(source);
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteModule(final Module_entity module) {
        try {
            final ForeignCollection<Category_entity> categories = module.getCategories();
            for (final Category_entity category : categories) {
                this.categDao.delete(category);
            }
            this.moduleDao.delete(module);
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(final Category_entity category) {
        try {
            final ForeignCollection<Source_entity> sources = category.getSources();
            for (final Source_entity source : sources) {
                this.srcDao.delete(source);
            }
            this.categDao.delete(category);
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSource(final Source_entity source) {
        try {
            final ForeignCollection<Item_entity> items = source.getItem();
            for (final Item_entity item : items) {
                this.itemDao.delete(item);
            }
            this.srcDao.delete(source);
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }



    private Dao<Module_entity, Integer> getModuleDao(final DatabaseHelper databaseHelper) {
        if (null == this.moduleDao) {
            try {
                this.moduleDao = databaseHelper.getModuleDao();
            }
            catch (final SQLException e) {
                Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return this.moduleDao;
    }

    private Dao<Category_entity, Integer> getAppDao(final DatabaseHelper databaseHelper) {
        if (null == this.categDao) {
            try {
                this.categDao = databaseHelper.getCategoryDao();
            }
            catch (final SQLException e) {
                Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return this.categDao;
    }

    private Dao<Source_entity, Integer> getSrcDao(final DatabaseHelper databaseHelper) {
        if (null == this.srcDao) {
            try {
                this.srcDao = databaseHelper.getSourceDao();
            }
            catch (final SQLException e) {
                Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return this.srcDao;
    }

    private Dao<Item_entity, Integer> getItemDao(final DatabaseHelper databaseHelper) {
        if (null == this.itemDao) {
            try {
                this.itemDao = databaseHelper.getItemDao();
            }
            catch (final SQLException e) {
                Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return this.itemDao;
    }
}
