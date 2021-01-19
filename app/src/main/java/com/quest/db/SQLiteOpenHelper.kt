package com.quest.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.quest.model.Hero


class SQLiteOpenHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    DBHandler {


    override fun onCreate(db: SQLiteDatabase) {
        val sql = ("CREATE TABLE " + TABLE_HEROES + "("
                + KEY_ID + " INTEGER,"
                + KEY_STRENGTH + " INTEGER,"
                + KEY_DEFENCE + " INTEGER,"
                + KEY_FAME + " INTEGER,"
                + KEY_COINS + " INTEGER,"
                + KEY_GEMS + " INTEGER,"
                + KEY_CELL_INDEX + " INTEGER,"
                + KEY_EPISODE_INDEX + " INTEGER,"
                + KEY_CHAPTER_INDEX + " INTEGER,"
                + KEY_T1 + " INTEGER,"
                + KEY_T2 + " INTEGER,"
                + KEY_T3 + " INTEGER,"
                + KEY_D1 + " INTEGER,"
                + KEY_D2 + " INTEGER,"
                + KEY_D3 + " INTEGER,"
                + KEY_ID_FON + " INTEGER,"
                + KEY_MARGIN + " INTEGER,"
                + KEY_TIME_MILLS + " LONG)")
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HEROES")
        onCreate(db)
    }

    override fun saveHero(hero: Hero) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ID, hero.getId())
        values.put(KEY_STRENGTH, hero.strength)
        values.put(KEY_DEFENCE, hero.defence)
        values.put(KEY_FAME, hero.fame)
        values.put(KEY_COINS, hero.coins)
        values.put(KEY_GEMS, hero.gems)
        values.put(KEY_CELL_INDEX, hero.cellIndex)
        values.put(KEY_EPISODE_INDEX, hero.episodeIndex)
        values.put(KEY_CHAPTER_INDEX, hero.chapterIndex)
        values.put(KEY_T1, hero.t.tStat1)
        values.put(KEY_T2, hero.t.tStat2)
        values.put(KEY_T3, hero.t.tStat3)
        values.put(KEY_D1, hero.d.dStat1)
        values.put(KEY_D2, hero.d.dStat2)
        values.put(KEY_D3, hero.d.dStat3)
        values.put(KEY_ID_FON, hero.idFon)
        values.put(KEY_MARGIN, hero.margin)
        values.put(KEY_TIME_MILLS, hero.timeMills)
        db.insert(TABLE_HEROES, null, values)
        db.close()
    }

    override fun loadHero(id: Int): Hero? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from $TABLE_HEROES where id=$id", null)
        if (cursor is Cursor) {
            cursor.moveToLast()
            val c = cursor as Cursor
            val args = arrayListOf<Number>()
            Log.i("SQL_DB", "columnCount = ${c.columnCount}")
            Log.i("SQL_DB", "count = ${c.count}")
            Hero::class.constructors.forEach { Log.i("SQL_DB", "size of parameters = ${it.parameters.size}") }

            return if (Hero::class.constructors.find { c.columnCount - 1 == it.parameters.size} != null && c.count > 0) {
                for (i in 1 until c.columnCount) {
                    args.add(c.getLong(i))
                }
                Log.i("SQL_DB_OBJECT", "$args")
                val hero = Hero(args)
                cursor.close()
                Log.i("SQL_DB", "Hero has been loaded")
                hero
            } else {
                Log.i("SQL_DB", "DB is empty")
                null
            }
        }
        Log.i("SQL_DB", "DB is empty")
        return null
    }

    override fun updateHero(hero: Hero): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_STRENGTH, hero.strength)
        values.put(KEY_DEFENCE, hero.defence)
        values.put(KEY_FAME, hero.fame)
        values.put(KEY_COINS, hero.coins)
        values.put(KEY_GEMS, hero.gems)
        values.put(KEY_CELL_INDEX, hero.cellIndex)
        values.put(KEY_EPISODE_INDEX, hero.episodeIndex)
        values.put(KEY_CHAPTER_INDEX, hero.chapterIndex)
        values.put(KEY_T1, hero.t.tStat1)
        values.put(KEY_T2, hero.t.tStat2)
        values.put(KEY_T3, hero.t.tStat3)

        values.put(KEY_D1, hero.d.dStat1)
        values.put(KEY_D2, hero.d.dStat2)
        values.put(KEY_D3, hero.d.dStat3)

        values.put(KEY_ID_FON, hero.idFon)
        values.put(KEY_MARGIN, hero.margin)
        values.put(KEY_TIME_MILLS, hero.timeMills)

        return db.update(TABLE_HEROES, values, "$KEY_ID = ?", arrayOf((hero.getId()).toString()))
    }

    override fun deleteHero(hero: Hero) {
        val db = this.writableDatabase
        db.delete(TABLE_HEROES, "$KEY_ID = ?", arrayOf(hero.getId().toString()))
        db.close()
    }

    override fun deleteAll() {
        val db = this.writableDatabase
        db.delete(TABLE_HEROES, null, null)
        db.close()
    }

    override val heroesCount: Int
        get() {
            val countQuery = "SELECT  * FROM $TABLE_HEROES"
            val db = this.readableDatabase
            val cursor: Cursor = db.rawQuery(countQuery, null)
            cursor.close()
            return cursor.count
        }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "saveManager"
        private const val TABLE_HEROES = "heroes"

        private const val KEY_ID = "id"

        private const val KEY_STRENGTH = "st"
        private const val KEY_DEFENCE = "def"
        private const val KEY_FAME = "fame"

        private const val KEY_COINS = "coins"
        private const val KEY_GEMS = "gems"

        private const val KEY_CELL_INDEX = "cell_index"
        private const val KEY_EPISODE_INDEX = "episode_index"
        private const val KEY_CHAPTER_INDEX = "chapter_index"

        private const val KEY_T1 = "t1"
        private const val KEY_T2 = "t2"
        private const val KEY_T3 = "t3"

        private const val KEY_D1 = "d1"
        private const val KEY_D2 = "d2"
        private const val KEY_D3 = "d3"

        private const val KEY_ID_FON = "id_fon"
        private const val KEY_MARGIN = "margin"
        private const val KEY_TIME_MILLS = "tm"
    }
}