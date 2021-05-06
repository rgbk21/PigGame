package com.rgbk21.model;

import com.rgbk21.utils.ErrorInfo;

import java.util.ArrayList;
import java.util.List;

public class OpenGames {

    List<String> openGames;
    List<ErrorInfo> errorInfoList;

    public OpenGames() {
        openGames = new ArrayList<>();
        errorInfoList = new ArrayList<>();
    }

    public List<String> getOpenGames() {
        return openGames;
    }

    public OpenGames setOpenGames(List<String> openGames) {
        this.openGames = openGames;
        return this;
    }

    public List<ErrorInfo> getErrorInfoList() {
        return errorInfoList;
    }

    public OpenGames setErrorInfoList(List<ErrorInfo> errorInfoList) {
        this.errorInfoList = errorInfoList;
        return this;
    }
}
