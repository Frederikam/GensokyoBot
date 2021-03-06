/*
 * MIT License
 *
 * Copyright (c) 2017 Frederik Ar. Mikkelsen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.frederikam.gensokyobot.command.music.control;

import com.frederikam.gensokyobot.audio.GuildPlayer;
import com.frederikam.gensokyobot.audio.PlayerRegistry;
import com.frederikam.gensokyobot.commandmeta.abs.Command;
import com.frederikam.gensokyobot.feature.I18n;
import com.frederikam.gensokyobot.commandmeta.abs.IMusicCommand;
import net.dv8tion.jda.core.entities.*;

import java.text.MessageFormat;

public class JoinCommand extends Command implements IMusicCommand {

    @Override
    public void onInvoke(Guild guild, TextChannel channel, Member invoker, Message message, String[] args) {
        GuildPlayer player = PlayerRegistry.get(guild);
        VoiceChannel vc = player.getUserCurrentVoiceChannel(invoker);
        player.setCurrentTC(channel);
        try {
            player.joinChannel(vc);
            if (vc != null) {
                channel.sendMessage(MessageFormat.format(I18n.get(guild).getString("joinJoining"), vc.getName()))
                        .queue();
            }
        } catch (IllegalStateException ex) {
            if(vc != null) {
                channel.sendMessage(MessageFormat.format(I18n.get(guild).getString("joinErrorAlreadyJoining"), vc.getName()))
                        .queue();
            } else {
                throw ex;
            }
        }
    }

    @Override
    public String help(Guild guild) {
        String usage = "{0}{1}\n#";
        return usage + I18n.get(guild).getString("helpJoinCommand");
    }
}
