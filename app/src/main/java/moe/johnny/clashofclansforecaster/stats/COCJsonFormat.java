package moe.johnny.clashofclansforecaster.stats;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import moe.johnny.clashofclansforecaster.stats.JsonStruct;
import rx.subjects.PublishSubject;

/**
 * Created by JohnnySun on 2016/4/2.
 */
public class COCJsonFormat {
    private static final String TAG = "COCJsonFormat";
    public static PublishSubject<JsonStruct> COCJson_Observable = PublishSubject.create();
    public static void JsonFormat(String jsonString) {
        JsonStruct mJsonStruct = new JsonStruct();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            mJsonStruct.setLootIndex(jsonObject.getDouble("lootIndexString"));
            mJsonStruct.setMainColorShadeNow(jsonObject.getString("mainColorShadeNow"));
            mJsonStruct.setBgColor(jsonObject.getString("bgColor"));

            JSONObject currentLoot = new JSONObject(jsonObject.getString("currentLoot"));

            mJsonStruct.setPlayersOnline(currentLoot.getInt("playersOnline"));
            mJsonStruct.setAttackablePlayers(currentLoot.getInt("attackablePlayers"));
            mJsonStruct.setShieldedPlayers(currentLoot.getInt("shieldedPlayers"));
            mJsonStruct.setTotalPlayers(currentLoot.getInt("totalPlayers"));

            COCJson_Observable.onNext(mJsonStruct);
        }catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
