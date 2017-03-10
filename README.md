# assetto-corsa-discord-bot

Reads acServer stdin feed and creates events to broadcast with discord-bot.

Brief usage:

./acServer | assetto-corsa-discord-bot-0.0.1-SNAPSHOT.jar

Only tested in Linux environment but should work with Windows too.

You need application.properties file in the directory where application is used. There must be

TOKEN=<your discord bot token here>

See: https://github.com/BtoBastian/Javacord/wiki/Create-bot-and-add-it-to-a-server

