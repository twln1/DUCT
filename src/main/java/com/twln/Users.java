package com.twln;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.listener.user.UserChangeNicknameListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Theodore Newton on 6/10/2017.
 */
public class Users {

    HashMap<Integer, Account> memberMap = new HashMap();
    int i = 1;
    public void getUsers(DiscordAPI api, String sID) {
        // TODO Implement database storage for usernicks


        Server s = api.getServerById(sID);
        Collection<User> memberCollection = api.getServerById(sID).getMembers();
        System.out.println(memberCollection.size() + "\n\n\n");
        for (User u : memberCollection) {
            if (u.getNickname(s) == null) {
                Account account = new Account(u.getId(), u.getName(), u.getNickname(s));
                memberMap.put(i, account);
                System.out.printf("%s:\t%s | %s%n", account.uID, account.uName,  account.uNick);
            } else {
                Account account = new Account(u.getId(), u.getName(), null);
                memberMap.put(i, account);
                System.out.printf("%s:\t%s%n | %s%n", account.uID, account.uName,  account.uNick);
            }
            i++;
        }
        ;
        printList(api);
    }

    public void listen(DiscordAPI api) {
        api.registerListener(new UserChangeNicknameListener() {
            @Override
            public void onUserChangeNickname(DiscordAPI discordAPI, Server server, User user, String s) {
                try {
                    if (user.getNickname(server) != null) {
                        Account account = new Account(user.getId(), user.getName(), user.getNickname(server));
                        memberMap.put(i, account);
                        System.out.printf("%s:\t%s%n", user.getId(), user.getNickname(server));
                    } else {
                        Account account = new Account(user.getId(), user.getName(), null);
                        memberMap.put(i, account);
                        System.out.printf("%s:\t%s%n", user.getId(), user.getName());
                        System.out.println(account.uID + " : "  + account.uName + " | " +  account.uNick);
                    }
                    i++;
                    System.out.printf("%s has changed their name to %s%n on server %s%n", oldNick(user, s, user.getId()), user.getName(), server.getName());
                    printList(api);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private String oldNick(User u, String name, String uID) {
        if (name == null) {
            return u.getName();
        }
        return name;
    }

    public void printList(DiscordAPI api) {
        String output = "userNicks.txt";
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            BufferedWriter bw = new BufferedWriter(new FileWriter(output));
            for (Map.Entry<Integer, Account> user : memberMap.entrySet()) {
                String out = user.getKey() + " : " + user.getValue().toString() + " " + dtf.format(now);
                bw.write(out + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
