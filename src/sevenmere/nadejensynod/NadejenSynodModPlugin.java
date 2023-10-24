package sevenmere.nadejensynod;

import java.awt.Color;

import java.util.*;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.campaign.CustomCampaignEntityAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.util.Misc;

import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;

import com.fs.starfarer.api.campaign.FactionAPI;

public class NadejenSynodModPlugin extends BaseModPlugin {
    @Override
    public void onApplicationLoad() throws Exception {
        super.onApplicationLoad();

        // Test that the .jar is loaded and working, using the most obnoxious way possible.
        //throw new RuntimeException("Template mod loaded! Remove this crash in TemplateModPlugin.");
    }

    @Override
    public void onNewGame() {
        super.onNewGame();

        SectorAPI sector = Global.getSector();
        EconomyAPI globalEconomy = Global.getSector().getEconomy();


        FactionAPI synod = sector.getFaction("nadejensynod");
        //friendly-neutral
        synod.setRelationship("nadejensynod_tanjanfellowship",1f);
        synod.setRelationship("persean",0.6f);
        synod.setRelationship("independent",0.3f);
        synod.setRelationship("player",0f);
        //negative
        synod.setRelationship("hegemony",-0.2f);
        synod.setRelationship("sindrian_diktat",-0.2f);
        synod.setRelationship("lions_guard",-0.2f);
        synod.setRelationship("pirates",-0.2f);
        //enemy
        synod.setRelationship("luddic_church",-0.6f);
        synod.setRelationship("knights_of_ludd", -0.6f);
        synod.setRelationship("luddic_path",-0.6f);
        synod.setRelationship("tritachyon",-0.6f);
        //nonstandart
        synod.setRelationship("derelict", -0.5f);
        synod.setRelationship("remnant", -0.5f);
        synod.setRelationship("omega", -0.5f);
        //mod
        //synod.setRelationship("kadur_remnant",0.15f);

        FactionAPI fellows = sector.getFaction("nadejensynod_tanjanfellowship");
        //friendly-neutral
        fellows.setRelationship("pirates",1f);
        fellows.setRelationship("nadejensynod",1f);
        fellows.setRelationship("persean",0.2f);
        //negative
        fellows.setRelationship("player",-0.2f);
        fellows.setRelationship("independent",-0.2f);
        //enemy
        fellows.setRelationship("hegemony",-0.8f);
        fellows.setRelationship("sindrian_diktat",-0.8f);
        fellows.setRelationship("lions_guard",-0.8f);
        fellows.setRelationship("luddic_church",-0.8f);
        fellows.setRelationship("knights_of_ludd", -0.8f);
        fellows.setRelationship("luddic_path",-0.8f);
        fellows.setRelationship("tritachyon",-0.8f);
        //nonstandart
        fellows.setRelationship("derelict", -0.5f);
        fellows.setRelationship("remnant", -0.5f);
        fellows.setRelationship("omega", -0.5f);
        //mod
        //fellows.setRelationship("kadur_remnant",0.15f);



        StarSystemAPI system = sector.createStarSystem("Nadeje");

        system.setBackgroundTextureFilename("graphics/backgrounds/background6.jpg");

        PlanetAPI NadejeStar = system.initStar(
                "nadeje",
                "star_orange",
                500,
                5320,
                3550,
                250);

        system.setLightColor(new Color(255, 240, 200));

        PlanetAPI MariannaPlanet = system.addPlanet("marianna",NadejeStar, "Marianna", "lava_minor", 37,90,1300,35);
        Misc.initConditionMarket(MariannaPlanet);
        //MariannaPlanet.setFaction(Factions.INDEPENDENT);

        MarketAPI MariannaMarket = Global.getFactory().createMarket("marianna_marketid",MariannaPlanet.getName(),0);
        //MariannaMarket.setFactionId(Factions.INDEPENDENT);
        MariannaMarket.setPlanetConditionMarketOnly(true);
        MariannaMarket.addCondition(Conditions.VERY_HOT);
        MariannaMarket.addCondition(Conditions.EXTREME_TECTONIC_ACTIVITY);
        MariannaMarket.addCondition(Conditions.NO_ATMOSPHERE);
        MariannaMarket.addCondition(Conditions.ORE_ULTRARICH);
        MariannaMarket.addCondition(Conditions.RARE_ORE_RICH);
        MariannaMarket.setPrimaryEntity(MariannaPlanet);
        MariannaPlanet.setMarket(MariannaMarket);
        MariannaMarket.setSurveyLevel(MarketAPI.SurveyLevel.FULL);


        PlanetAPI ElifPlanet = system.addPlanet("elif", NadejeStar, "Elif", "gas_giant",0,250,3500,60);
        Misc.initConditionMarket(ElifPlanet);
        ElifPlanet.setFaction("nadejensynod");

        MarketAPI ElifMarket = Global.getFactory().createMarket("elif_marketid", ElifPlanet.getName(),4);
        ElifMarket.setFactionId("nadejensynod");
        ElifMarket.setPlanetConditionMarketOnly(false);
        ElifMarket.addCondition(Conditions.HOT);
        ElifMarket.addCondition(Conditions.HIGH_GRAVITY);
        ElifMarket.addCondition(Conditions.VOLATILES_PLENTIFUL);
        ElifMarket.addCondition(Conditions.POPULATION_4);
        ElifMarket.addCondition("nadejensynod_nadejenmajority");
        ElifMarket.setPrimaryEntity(ElifPlanet);
        ElifPlanet.setMarket(ElifMarket);
        ElifMarket.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        ElifMarket.getTariff().modifyFlat("generator",0.3f);

        ElifMarket.addIndustry(Industries.POPULATION);
        ElifMarket.addIndustry(Industries.SPACEPORT);
        ElifMarket.addIndustry(Industries.MINING);
        ElifMarket.addIndustry(Industries.GROUNDDEFENSES);

        ElifMarket.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        ElifMarket.addSubmarket(Submarkets.SUBMARKET_BLACK);
        ElifMarket.addSubmarket(Submarkets.SUBMARKET_OPEN);

        globalEconomy.addMarket(
                ElifMarket, //The market to add obviously!
                false //The "withJunkAndChatter" flag. It will add space debris in orbit and radio chatter sound effects.*
        );


        PlanetAPI SelinPlanet = system.addPlanet("selin", ElifPlanet, "Selin", "arid", 0, 85, 600, 17); //id, focus entity (i.e. star id), name, class, angle, radius, distance from focus, orbiting period
        Misc.initConditionMarket(SelinPlanet);
        SelinPlanet.setFaction("nadejensynod");

        MarketAPI SelinMarket = Global.getFactory().createMarket("selin_marketId", SelinPlanet.getName(), 5);
        SelinMarket.setFactionId("nadejensynod");
        SelinMarket.setPlanetConditionMarketOnly(false); //This market doesn't just represent planet conditions.
        SelinMarket.addCondition(Conditions.HABITABLE);
        SelinMarket.addCondition(Conditions.HOT);
        SelinMarket.addCondition(Conditions.POLLUTION);
        SelinMarket.addCondition(Conditions.ORE_MODERATE);
        SelinMarket.addCondition(Conditions.RARE_ORE_SPARSE);
        SelinMarket.addCondition(Conditions.ORGANICS_TRACE);
        SelinMarket.addCondition(Conditions.FARMLAND_POOR);
        SelinMarket.addCondition(Conditions.POPULATION_5);
        SelinMarket.addCondition("nadejensynod_nadejenmajority");
        SelinMarket.setPrimaryEntity(SelinPlanet); //Tell the "market" that it's on our planet.
        SelinPlanet.setMarket(SelinMarket); //Likewise, tell our planet that it has a market.
        SelinMarket.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        SelinMarket.getTariff().modifyFlat("generator", 0.3f);

        SelinMarket.addIndustry(Industries.POPULATION);
        SelinMarket.addIndustry(Industries.SPACEPORT);
        SelinMarket.addIndustry(Industries.STARFORTRESS_MID);
        //SelinMarket.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.CORRUPTED_NANOFORGE));
        SelinMarket.addIndustry("yanicharheavyindustry");
        SelinMarket.addIndustry(Industries.HIGHCOMMAND);
        SelinMarket.addIndustry(Industries.HEAVYBATTERIES);
        SelinMarket.addIndustry(Industries.FUELPROD);

        SelinMarket.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        SelinMarket.addSubmarket(Submarkets.SUBMARKET_BLACK);
        SelinMarket.addSubmarket(Submarkets.SUBMARKET_OPEN);
        SelinMarket.addSubmarket(Submarkets.GENERIC_MILITARY);

        globalEconomy.addMarket(
                SelinMarket, //The market to add obviously!
                false //The "withJunkAndChatter" flag. It will add space debris in orbit and radio chatter sound effects.*
        );


        CustomCampaignEntityAPI YanicharStation = system.addCustomEntity(
                "yanichar", //id
                "Yanichar Station", //display name
                "station_side02", //types are found in data/config/custom_entities.json
                "nadejensynod"
        );
        YanicharStation.setCircularOrbitPointingDown(
                SelinPlanet,
                0, //Angle
                223, //orbit radius
                23 //orbit period
        );
        YanicharStation.setRadius(50);
        SelinMarket.getConnectedEntities().add(YanicharStation);
        YanicharStation.setMarket(SelinMarket);


        PlanetAPI ZofiaPlanet = system.addPlanet("zofia", NadejeStar, "Zofia", "terran-eccentric", 75, 110, 4700, 90);
        Misc.initConditionMarket(ZofiaPlanet);
        ZofiaPlanet.setFaction("nadejensynod");

        MarketAPI ZofiaMarket = Global.getFactory().createMarket("zofia_marketId", ZofiaPlanet.getName(), 8);
        ZofiaMarket.setFactionId("nadejensynod");
        ZofiaMarket.setPlanetConditionMarketOnly(false);
        ZofiaMarket.addCondition(Conditions.HABITABLE);
        ZofiaMarket.addCondition(Conditions.EXTREME_WEATHER);
        ZofiaMarket.addCondition(Conditions.ORE_SPARSE);
        ZofiaMarket.addCondition(Conditions.RARE_ORE_SPARSE);
        ZofiaMarket.addCondition(Conditions.ORGANICS_PLENTIFUL);
        ZofiaMarket.addCondition(Conditions.FARMLAND_BOUNTIFUL);
        ZofiaMarket.addCondition("nadejensynod_nadejenmajority");
        ZofiaMarket.addCondition(Conditions.POPULATION_8);
        ZofiaMarket.setPrimaryEntity(ZofiaPlanet);
        ZofiaPlanet.setMarket(ZofiaMarket);
        ZofiaMarket.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        ZofiaMarket.getTariff().modifyFlat("generator", 0.3f);

        ZofiaMarket.addIndustry(Industries.POPULATION);
        ZofiaMarket.addIndustry(Industries.SPACEPORT);
        ZofiaMarket.addIndustry(Industries.HEAVYBATTERIES);
        ZofiaMarket.addIndustry(Industries.FARMING);
        ZofiaMarket.addIndustry(Industries.MINING);
        ZofiaMarket.addIndustry(Industries.LIGHTINDUSTRY);
        ZofiaMarket.addIndustry(Industries.REFINING);
        ZofiaMarket.addIndustry(Industries.ORBITALSTATION_MID);

        ZofiaMarket.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        ZofiaMarket.addSubmarket(Submarkets.SUBMARKET_BLACK);
        ZofiaMarket.addSubmarket(Submarkets.SUBMARKET_OPEN);

        globalEconomy.addMarket(
                ZofiaMarket, //The market to add obviously!
                false //The "withJunkAndChatter" flag. It will add space debris in orbit and radio chatter sound effects.*
        );


        PlanetAPI TanjaPlanet = system.addPlanet("tanja", NadejeStar, "Tanja", "frozen3",85,75,7750,182);
        Misc.initConditionMarket(TanjaPlanet);
        TanjaPlanet.setFaction("nadejensynod_tanjanfellowship");
        //TanjaPlanet.setFaction("pirates");

        MarketAPI TanjaMarket = Global.getFactory().createMarket("tanja_marketid", TanjaPlanet.getName(),5);
        TanjaMarket.setFactionId("nadejensynod_tanjanfellowship");
        TanjaMarket.setPlanetConditionMarketOnly(false);
        TanjaMarket.addCondition(Conditions.COLD);
        TanjaMarket.addCondition(Conditions.THIN_ATMOSPHERE);
        TanjaMarket.addCondition(Conditions.LOW_GRAVITY);
        TanjaMarket.addCondition(Conditions.VOLATILES_PLENTIFUL);
        TanjaMarket.addCondition(Conditions.ORE_RICH);
        TanjaMarket.addCondition(Conditions.RARE_ORE_ULTRARICH);
        TanjaMarket.addCondition(Conditions.RUINS_SCATTERED);
        TanjaMarket.addCondition(Conditions.POPULATION_5);
        TanjaMarket.addCondition("nadejensynod_nadejenpatriotism");
        TanjaMarket.setPrimaryEntity(TanjaPlanet);
        TanjaPlanet.setMarket(TanjaMarket);
        TanjaMarket.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        TanjaMarket.getTariff().modifyFlat("generator",0.3f);

        TanjaMarket.addIndustry(Industries.POPULATION);
        TanjaMarket.addIndustry(Industries.SPACEPORT);
        TanjaMarket.addIndustry(Industries.WAYSTATION);
        TanjaMarket.addIndustry(Industries.MINING);
        TanjaMarket.addIndustry(Industries.LIGHTINDUSTRY);
        TanjaMarket.addIndustry(Industries.HEAVYBATTERIES);
        TanjaMarket.addIndustry(Industries.PATROLHQ);

        TanjaMarket.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        TanjaMarket.addSubmarket(Submarkets.SUBMARKET_BLACK);
        TanjaMarket.addSubmarket(Submarkets.SUBMARKET_OPEN);
        TanjaMarket.setFreePort(true);

        globalEconomy.addMarket(
                TanjaMarket, //The market to add obviously!
                false //The "withJunkAndChatter" flag. It will add space debris in orbit and radio chatter sound effects.*
        );


        PlanetAPI MaraPlanet = system.addPlanet("mara",NadejeStar, "Mara", "barren", 10,60,9400,235);
        Misc.initConditionMarket(MaraPlanet);
        //MaraPlanet.setFaction(Factions.INDEPENDENT);

        MarketAPI MaraMarket = Global.getFactory().createMarket("mara_marketid",MariannaPlanet.getName(),0);
        //MaraMarket.setFactionId(Factions.INDEPENDENT);
        MaraMarket.setPlanetConditionMarketOnly(true);
        MaraMarket.addCondition(Conditions.VERY_COLD);
        MaraMarket.addCondition(Conditions.DARK);
        MaraMarket.addCondition(Conditions.NO_ATMOSPHERE);
        MaraMarket.addCondition(Conditions.LOW_GRAVITY);
        MaraMarket.addCondition(Conditions.ORE_ULTRARICH);
        MaraMarket.addCondition(Conditions.RARE_ORE_ULTRARICH);
        MaraMarket.setPrimaryEntity(MaraPlanet);
        MaraPlanet.setMarket(MaraMarket);
        MaraMarket.setSurveyLevel(MarketAPI.SurveyLevel.FULL);


        system.addAsteroidBelt(NadejeStar,100,1900,280,35,42, Terrain.ASTEROID_BELT, "Voskreseniye Belt");
        system.addRingBand(NadejeStar, "misc", "rings_asteroids0", 280f, 0, Color.gray, 280f, 1900, 40f);
        system.addAsteroidBelt(NadejeStar,1600,9550,800,240,260, Terrain.ASTEROID_BELT, "Pagana Belt");
        system.addRingBand(NadejeStar, "misc", "rings_asteroids0", 800f, 0, Color.gray, 800f, 9550, 250f);
        //To make the star appear correctly in the game it is necessary to add hyperspace points.
        //This is the easiest way to handle it.
        //In this case we want to make sure we add a fringe jump point or else no one will be able to leave
        //our star system without a transverse jump! So the second "true" is kind of important.
        //autogenerateHyperspaceJumpPoints(
        //	boolean generateEntrancesAtGasGiants, //Create jump point at our gas giants?
        //	boolean generateFringeJumpPoint) //Create a jump point at the edge of the system?
        system.autogenerateHyperspaceJumpPoints(true, true);

        //descriptions
        //NadejeStar.setCustomDescriptionId("nadejensynod_nadeje");
        ElifPlanet.setCustomDescriptionId("nadejensynod_elif");
        SelinPlanet.setCustomDescriptionId("nadejensynod_selin");
        ZofiaPlanet.setCustomDescriptionId("nadejensynod_zofia");
        YanicharStation.setCustomDescriptionId("nadejensynod_yanichar");
        TanjaPlanet.setCustomDescriptionId("nadejensynod_tanja");



        SectorEntityToken relay = system.addCustomEntity("nadeje_relay", // unique id
                "Nadeje Relay", // name - if null, defaultName from custom_entities.json will be used
                "comm_relay", // type of object, defined in custom_entities.json
                "nadejensynod"); // faction
        relay.setCircularOrbitPointingDown(NadejeStar, 60, 10500, 310);

        SectorEntityToken sensors = system.addCustomEntity("nadeje_array", // unique id
                "Nadeje Sensor Array", // name - if null, defaultName from custom_entities.json will be used
                "sensor_array_makeshift", // type of object, defined in custom_entities.json
                "nadejensynod"); // faction
        sensors.setCircularOrbitPointingDown(NadejeStar, 350, 6000, 260);


        system.updateAllOrbits();
        // The code below requires that Nexerelin is added as a library (not a dependency, it's only needed to compile the mod).
//        boolean isNexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");

//        if (!isNexerelinEnabled || SectorManager.getManager().isCorvusMode()) {
//                    new MySectorGen().generate(Global.getSector());
            // Add code that creates a new star system (will only run if Nexerelin's Random (corvus) mode is disabled).
//        }
    }
}
