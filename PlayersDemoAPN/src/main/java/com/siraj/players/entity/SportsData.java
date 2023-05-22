package com.siraj.players.entity;



public class SportsData {

    private String sportsName;

    private PlayersData playersInfo;

    private String errorMessage;

    public String getSportsName() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }

    public PlayersData getPlayersInfo() {
        return playersInfo;
    }

    public void setPlayersInfo(PlayersData playersInfo) {
        this.playersInfo = playersInfo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
