package com.dev.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.core.data.local.dao.paging.BaseDao
import com.dev.core.data.local.dao.paging.KeyDao

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class BaseDb: RoomDatabase() {
    abstract fun baseDao1(): BaseDao

    abstract fun baseDao2(): com.dev.core.data.local.dao.nonPaging.BaseDao

    abstract fun keyDao(): KeyDao
}