package com.twln;

// Discord Username Collection Tool

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;

import java.util.AbstractMap;
import java.util.ArrayList;


public class DUCT {
    public static void main(String [] args) {
        Users users = new Users();
        Config config = Config.loadConfig();
        DiscordAPI api = Javacord.getApi(config.getEmail(), config.getPassword());
        api.connect(new FutureCallback<DiscordAPI>() {
            @Override
            public void onSuccess(DiscordAPI result) {
                ArrayList<String> whitelist = new ArrayList<>();
                for (AbstractMap.SimpleEntry serverInfo: config.getServers()) {
                    String serverID = serverInfo.getKey().toString();
                    whitelist.add(serverID);
                    users.getUsers(api, serverID);
                    System.out.printf("MEMBER COUNT: %d%n", api.getServerById(serverID).getMemberCount());
                }
                users.listen(api, whitelist);
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}
