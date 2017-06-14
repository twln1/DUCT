package com.twln;

// Discord Username Collection Tool

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;


public class DUCT {
    public static void main(String [] args) {
        Users users = new Users();
        String username = "", password = "";
        DiscordAPI api = Javacord.getApi(username, password); //TODO Take credentials from external source

        api.connect(new FutureCallback<DiscordAPI>() {
            @Override
            public void onSuccess(DiscordAPI result) {
                // Do whatever
                String serverID = "81384788765712384";
                System.out.printf("MEMBER COUNT: %d%n", api.getServerById(serverID).getMemberCount());
                users.getUsers(api, serverID);
                users.listen(api);

            }
            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}
