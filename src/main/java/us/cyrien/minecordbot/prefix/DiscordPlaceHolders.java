package us.cyrien.minecordbot.prefix;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import us.cyrien.minecordbot.entity.MCBUser;

import java.util.*;

public enum DiscordPlaceHolders {
    CHANNEL {
        @Override
        public String toString() {
            return mre.getTextChannel().getName();
        }
    },
    GUILD {
        @Override
        public String toString() {
            return mre.getGuild().getName();
        }
    },
    SENDER {
        @Override
        public String toString() {
            return mre.getAuthor().getName();
        }
    },
    NAME {
        @Override
        public String toString() {
            MCBUser user = new MCBUser(mre);
            return user.getName();
        }
    },
    ROLE {

        @Override
        public String toString() {
            Member member = mre.getGuild().getMember(mre.getAuthor());
            Role role = getPrimaryRole(member);
            return role == null ? "" : role.getName();
        }

        private Role getPrimaryRole(Member member) {
           List<Role> roles = member.getRoles();
           if(roles == null) return null;
           if(roles.size() == 0) return null;
           Role role = roles.get(0);
           for(Role r : roles)
               if(role.getPosition() < r.getPosition())
                   role = r;
           return role;
        }

    },
    ENAME {
        @Override
        public String toString() {
            return mre.getMember().getEffectiveName();
        }
    };

    private static MessageReceivedEvent mre;

    public void init(MessageReceivedEvent mre) {
        DiscordPlaceHolders.mre = mre;
    }

}
