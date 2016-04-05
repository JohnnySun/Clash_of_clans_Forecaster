package moe.johnny.clashofclansforecaster.stats;

/**
 * Created by JohnnySun on 2016/4/2.
 */
public class JsonStruct {
    public Double LootIndex;
    public int totalPlayers;
    public int playersOnline;
    public int shieldedPlayers;
    public  int attackablePlayers;

    public String bgColor;
    public String mainColorShadeNow;


    public void setMainColorShadeNow(String mainColorShadeNow) {
        this.mainColorShadeNow = "#" + mainColorShadeNow;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = "#" + bgColor;
    }

    public void setLootIndex(double lootIndex) {
        LootIndex = lootIndex;
    }

    public void setTotalPlayers(int totalPlayerst) {
        this.totalPlayers = totalPlayerst;
    }

    public void setPlayersOnline(int playersOnline) {
        this.playersOnline = playersOnline;
    }

    public void setShieldedPlayers(int shieldedPlayers) {
        this.shieldedPlayers = shieldedPlayers;
    }

    public void setAttackablePlayers(int attackablePlayers) {
        this.attackablePlayers = attackablePlayers;
    }
}
