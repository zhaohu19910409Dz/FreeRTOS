package john.zhao.arunningman.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AddressManager {

    private LiveData<List<Address>> addressLive;
    private AddressDao addressDao;

    public AddressManager(Context context)
    {
        AddressDataBase.get(context).getAddressDao();
    }

    public LiveData<List<Address>> getAddressLive()
    {
        return addressDao.getAllAddress();
    }


    public void addAddress(Address... addresses)
    {
        new AddAddress(addressDao).execute(addresses);
    }

    public void deleteAddress(Address... addresses)
    {
        new DeleteAddress(addressDao).execute(addresses);
    }

    static class AddAddress extends AsyncTask<Address, Void, Void>
    {
        private AddressDao addressDao;
        public AddAddress(AddressDao addressDao)
        {
            this.addressDao = addressDao;
        }
        @Override
        protected Void doInBackground(Address... addresses) {
            addressDao.addAddress(addresses);
            return null;
        }
    }

    static class DeleteAddress extends AsyncTask<Address, Void, Void>
    {
        private AddressDao addressDao;
        public DeleteAddress(AddressDao addressDao)
        {
            this.addressDao = addressDao;
        }
        @Override
        protected Void doInBackground(Address... addAddresses) {
            addressDao.deleteAddress(addAddresses);
            return null;
        }
    }
}
