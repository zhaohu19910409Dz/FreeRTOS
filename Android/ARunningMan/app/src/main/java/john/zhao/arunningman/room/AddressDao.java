package john.zhao.arunningman.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AddressDao {
    @Insert
    void addAddress(Address... addresses);

    @Delete
    void deleteAddress(Address... addresses);

    @Query("SELECT * FROM Address ORDER BY ID DESC")
    LiveData<List<Address>> getAllAddress();
}
