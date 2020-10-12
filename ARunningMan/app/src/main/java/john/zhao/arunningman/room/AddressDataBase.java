package john.zhao.arunningman.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Address.class}, version = 1, exportSchema = false)
public abstract class AddressDataBase extends RoomDatabase {

    private static AddressDataBase addressDataBase;

    public static AddressDataBase get(Context context)
    {
        if(addressDataBase == null)
        {
            addressDataBase = Room.databaseBuilder(context.getApplicationContext(), AddressDataBase.class, "address").build();
        }
        return addressDataBase;
    }

    abstract AddressDao getAddressDao();
}
