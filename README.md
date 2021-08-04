## Description

FactionRanking is a plugin that allows server administrators to put factions in competition in different rankings (working for **1.7** and **highter**)

## Developers

You can reuse FactionRanking but make sure you comply with the [LICENSE](https://github.com/thisisnzed/FactionRanking/blob/main/LICENSE).

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>com.factionranking</groupId>
        <artifactId>FactionRanking</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
```

## Dependencies

* **FactionsUUID** : https://www.spigotmc.org/resources/factionsuuid.1035/
* **Vault** : https://www.spigotmc.org/resources/vault.34315/
* **Essentials** : https://www.spigotmc.org/resources/essentialsx.9089/

## Features

* **MySQL Support** | Database & tables **auto-creation**
* Placeholders to **display** the position** in the **ranking of **player factions** (in the **chat** for example)
* Fully **[configurable](https://github.com/thisisnzed/FactionRanking/blob/main/src/main/resources/config.yml)**
* Fully **[translatable](https://github.com/thisisnzed/FactionRanking/blob/main/src/main/resources/lang.yml)**
* Plugin is started **asynchronously** to get best performances
* Support all versions **from 1.7**
* **Auto-update** leadboards every X minutes
* **One GUI** for each ranking (customizable)
* **Console** can **execute commands** to add and remove points from Faction for your **events like KOTH/Totem/DTC...**

## Installation

* I. Download the [last release](https://github.com/thisisnzed/FactionRanking/releases) of FactionRanking
* II. Put the file to your folder **plugins**
* III. Run the server
* IV. Configure database in **plugins/Ranking/config.yml**
* V. Restart the server

## Placeholders

There are **three** rankings and for each there is **one** placeholder to display the position in the ranking of the faction of player.

* **Ranking - Global** : %factionranking_globalrank%
* **Ranking - Farm** : %factionranking_farmrank%
* **Ranking - PvP** : %factionranking_pvprank%
