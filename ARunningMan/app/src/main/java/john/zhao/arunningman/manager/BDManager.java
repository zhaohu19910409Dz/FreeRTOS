package john.zhao.arunningman.manager;

import android.icu.lang.UScript;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;

import john.zhao.arunningman.model.SelectInfo;

public class BDManager {

    private ReverseGeoCodeOption reverseGeoCodeOption;
    private GeoCoder geocoder;

    private SuggestionSearch suggestionSearch;
    private SuggestionSearchOption suggestionSearchOption;

    private List<SelectInfo> list = new ArrayList<>();

    public BDManager(){
        reverseGeoCodeOption = new ReverseGeoCodeOption();
        geocoder = GeoCoder.newInstance();

        suggestionSearchOption = new SuggestionSearchOption();
        suggestionSearch = SuggestionSearch.newInstance();
    }

    private OnResultListener onResultListener;

    public void startPoi(String key)
    {
        suggestionSearch.requestSuggestion(suggestionSearchOption.citylimit(true).city("深圳").keyword(key));

    }

    public void setOnResultListener(OnResultListener listener)
    {
        this.onResultListener = listener;
        OnGetSuggestionResultListener onGetSuggestionResultListener = new OnGetSuggestionResultListener()
        {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult)
            {
                List<SuggestionResult.SuggestionInfo> allSuggestions = suggestionResult.getAllSuggestions();
                if(allSuggestions.size() > 0)
                {
                    for (int i = 0; i < allSuggestions.size(); i++)
                    {
                        SuggestionResult.SuggestionInfo suggestionInfo = allSuggestions.get(i);
                        SelectInfo info = new SelectInfo();
                        info.setAddress(suggestionInfo.key);
                        info.setCity(suggestionInfo.city + suggestionInfo.district);
                        info.setLatitude(suggestionInfo.pt.latitude);
                        info.setLongtitude(suggestionInfo.pt.longitude);
                        list.add(info);
                    }
                    onResultListener.resut(list);
                }
            }
        };
        suggestionSearch.setOnGetSuggestionResultListener(onGetSuggestionResultListener);
    }

    public interface OnResultListener
    {
        void resut(List<SelectInfo> list);
    }
    private OnGetGeoCoderResultListener onGetGeoCoderResultListener;

    public void setOnGetGeoCoderResultListener(OnGetGeoCoderResultListener onGetGeoCoderResultListener)
    {
        this.onGetGeoCoderResultListener = onGetGeoCoderResultListener;
    }

    public void setLatLng(LatLng latLng)
    {
        reverseGeoCodeOption.location(latLng);
        geocoder.reverseGeoCode(reverseGeoCodeOption);
        geocoder.setOnGetGeoCodeResultListener(onGetGeoCoderResultListener);
    }
}
