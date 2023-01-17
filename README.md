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
        <version>2.0.0</version>
    </dependency>
</dependencies>
```

## Dependencies

* **FactionsUUID** : https://www.spigotmc.org/resources/factionsuuid.1035/

## Features

* **MySQL Support** | Database & tables **auto-creation**
* Placeholders to **display** the **position** in the **ranking** of **player factions** (in the **chat** for example)
* Requests are **asynchronous** to get best performances
* Support all versions **from 1.7**
* **Auto-update** leadboard every 5 minutes
* **Console** can **execute commands** to add and remove points from Faction for your **events like KOTH/Totem/DTC...**

## Installation

* I. Download the [lastest source code](https://github.com/thisisnzed/FactionRanking/) of FactionRanking
* II. Put the file to your folder **plugins**
* III. Run the server
* IV. Configure database in **plugins/FactionRanking/config.yml**
* V. Restart the server

## Placeholders

* **Position** : %factionranking_pos%
* **Rank** : %factionranking_rank%
