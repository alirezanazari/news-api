package ir.alirezanazari.newsapi.data.db

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter


object DateConverter {

    @TypeConverter
    @JvmStatic
    fun stringToDate(str : String?) = str?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(date: LocalDateTime?) = date?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

}