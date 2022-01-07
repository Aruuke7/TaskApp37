package kg.geektech.taskapp37.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kg.geektech.taskapp37.models.News;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<News> getAll();

    @Query("SELECT * FROM news ORDER BY title")
    List<News> getAllByTitle();

    @Insert
    void insert(News news);

    @Delete
    void delete(News news);

    @Update
    void update(News news);
}
