# Minecoin! [![forthebadge](http://forthebadge.com/images/badges/60-percent-of-the-time-works-every-time.svg)](http://forthebadge.com) [![Build Status](http://ci.kingtux.me/job/Minecoin/badge/icon)](http://ci.kingtux.me/job/Minecoin/)
Minecoin is a plugin that allows another economy which is seperate from vault into your server!
I originally created this to be a secondary type of Economy however, it does not have to be!
Metrics link: https://bstats.org/plugin/bukkit/Minecoin/
Support:
[![Discord](https://imgur.com/MFRRBn4.png)](https://discord.gg/dcZfcSK)

Documents: https://docs.kingtux.me/minecoin/

Example: 
```
        MineCoinAPI mineCoinAPI = MineCoinAPI.getInstance();
        mineCoinAPI.addBalance(e.getPlayer(), 2);
        mineCoinAPI.subtractBalance(e.getPlayer(), 1);
        e.getPlayer().sendMessage("Your Balance is: " + mineCoinAPI.getBalance(e.getPlayer()));```