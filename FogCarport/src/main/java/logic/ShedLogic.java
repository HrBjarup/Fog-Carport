/*
 *  
 */
package logic;

import data.DataFacade;
import data.DataFacadeImpl;
import data.exceptions.LoginException;
import data.models.MaterialModel;
import data.models.OrderModel;
import data.models.PartslistModel;

/**
 * Contains logic regarding the Bill of Materials for a shed in the carport.
 *
 * @author Malte
 */
public class ShedLogic
{

    private static ShedLogic instance = null;

    public synchronized static ShedLogic getInstance()
    {
        if (instance == null)
        {
            instance = new ShedLogic();
        }
        return instance;
    }

    /*
    https://datsoftlyngby.github.io/dat2sem2019Spring/Modul4/Fog/CAR01_HR.pdf
    Going off of this with regards to assumptions.
    I assume that this method is only called if the stuff in order regarding shed is not null.
    Things to take into account: 
    Width of the shed
    Length of the shed
    Type of wood used for the sheds "beklædning"
    Type of wood used for the floor (if they even want a floor)
     */
    PartslistModel addShed(PartslistModel bom, OrderModel order) throws LoginException
    {
        DataFacade db = DataFacadeImpl.getInstance();

        boolean standard = false;
        if (standard == true)
        {
            simpleShed(bom);
        } else
        {

            // VARIABLES IF NOT STANDARD SHED:
            int width = order.getShed_width();
            int length = order.getShed_length();
            MaterialModel wood = db.getMaterial(order.getShed_walls_id());

            // MATERIALS NEEDED NO MATTER WHAT - DOOR
            addDoorMaterials(bom, wood, db);

            // IF FLOOR IS CHOSEN:
            if (order.getShed_floor_id() != 0)
            {
                MaterialModel floor = db.getMaterial(order.getShed_floor_id());
                // ADD X AMOUNT OF FLOOR DEPENDING ON WIDTH AND LENGTH HERE TO BOM
                addFloor(bom, floor, length, width, db);
            }

            // AND THE REST
            addMaterials(bom, wood, length, width, db);

        }

        return bom;
    }

    private void simpleShed(PartslistModel bom)
    {
        //<editor-fold defaultstate="collapsed" desc="MATERIALS FOR STANDARD SHED">
        // BELOW IS MATERIALS USED FOR THE SIMPLE ALGORITHM
        // KEEP THIS FOR THE BUTTON NAMED "Standard Redskabsrum" AS SHOWN IN VIDEO
        // So if you check the box called "standard redskabsrum" simply use this list of materials.
        /* Screws and misc. */
        MaterialModel stalddørsgreb = new MaterialModel(75, "Stalddørsgreb", "Stalddørsgreb 50x75", 1, 0, 0);
        stalddørsgreb.setHelptext("til dør i skur");
        stalddørsgreb.setQuantity(1);
        stalddørsgreb.setUnit("sæt");
        stalddørsgreb.setPrice(10);
        bom.addMaterial(stalddørsgreb);

        MaterialModel thængsel = new MaterialModel(390, "T-hængsel", "T-Hængsel 390 mm.", 1, 0, 0);
        thængsel.setHelptext("til dør i skur");
        thængsel.setQuantity(2);
        thængsel.setUnit("Stk.");
        thængsel.setPrice(10);
        bom.addMaterial(thængsel);

        MaterialModel skruer70 = new MaterialModel(70, "Skruer70", "4,5 x 70 mm. Skruer 200 stk.", 1, 0, 0);
        skruer70.setHelptext("til montering af yderste bræt ved beklædning");
        skruer70.setQuantity(3);
        skruer70.setUnit("Pk.");
        skruer70.setPrice(10);
        bom.addMaterial(skruer70);

        MaterialModel skruer50 = new MaterialModel(50, "Skruer50", "4,5 x 50 mm. Skruer 350 stk.", 1, 0, 0);
        skruer50.setHelptext("til montering af yderste bræt ved beklædning");
        skruer50.setQuantity(2);
        skruer50.setUnit("Pk.");
        skruer50.setPrice(10);
        bom.addMaterial(skruer50);

        /* Wood */
        MaterialModel bræt210 = new MaterialModel(210, "bræt", "19x100 mm. trykimp. Bræt", 100, 2100, 19);
        bræt210.setHelptext("Beklædning af skur 1 på 2");
        bræt210.setQuantity(148);
        bræt210.setUnit("Stk.");
        bræt210.setPrice(100);
        bom.addMaterial(bræt210);

        MaterialModel løsholt360 = new MaterialModel(360, "løsholt", "45x95 Reglar ubh.", 95, 3600, 45);
        løsholt360.setHelptext("Løsholter i gavle af skur");
        løsholt360.setQuantity(6);
        løsholt360.setUnit("Stk.");
        løsholt360.setPrice(123);
        bom.addMaterial(løsholt360);

        MaterialModel løsholt240 = new MaterialModel(240, "løsholt", "45x95 Reglar ubh.", 95, 2400, 45);
        løsholt240.setHelptext("Løsholter i siderne af skur");
        løsholt240.setQuantity(4);
        løsholt240.setUnit("Stk.");
        løsholt240.setPrice(112);
        bom.addMaterial(løsholt240);
        //</editor-fold>
    }

    /**
     * Adds materials to the Partslist for the floor, if selected.
     *
     * @param floor MaterialModel
     * @param length of the Shed.
     * @param width of the Shed.
     * @return PartslistModel
     */
    private void addFloor(PartslistModel bom, MaterialModel floor, int length, int width, DataFacade db) throws LoginException
    {
        int floorwidth = floor.getWidth();
        int floorlength = floor.getLength();
        
        int woodwidthamount = width/floorwidth; // Get amount of boards needed for width of shed
        if (width % floorwidth > 0){
            woodwidthamount++;
        }
        
        int woodlengthamount = length/floorlength; // Get amount of boards needed for length of shed
        if (length % floorwidth > 0){
            woodlengthamount++;
        }
        
        int amountofwood = woodlengthamount*woodwidthamount; // Length * Width = amount of boards needed in total for shed floor.
        floor.setQuantity(amountofwood);
        bom.addMaterial(floor);
        
        // 4 screws per board #27 - 300 in a pack
        int screwamount = amountofwood * 4;
        MaterialModel screws = db.getMaterial(27); // 300 in one pack.
        addScrews(bom, db, screws, 300, screwamount);
    }
   
    /**
     * Adds always needed materials to the Partslist. 
     * Materials for the door, etc.
     * 2 vandrette stivere 38x73 
     * 2 lag beklædningsbrædder 
     * skråstiver 
     * lægte
     * dørgreb - 
     * stalddørsgreb - 
     * #17 material hængsler 
     * skruer til alt dette
     *
     * @param wood type of wood selected for the shed.
     * @return PartslistModel
     */
    private void addDoorMaterials(PartslistModel bom, MaterialModel wood, DataFacade db) throws LoginException
    {
        MaterialModel stalddørsgreb = db.getMaterial(17); 
        stalddørsgreb.setQuantity(1);
        bom.addMaterial(stalddørsgreb); // Stalddørsgreb for the door.
        
        MaterialModel laegte = db.getMaterial(12); // 38x73mm taglægte.
        laegte.setQuantity(1);
        bom.addMaterial(laegte); // for the backside of the door.
        
        MaterialModel hængsel = db.getMaterial(18);
        hængsel.setQuantity(2);
        bom.addMaterial(hængsel); // T-hængsel for the door.
    }

    /**
     * Materials for the rest. 
     * 12 brædder per 30cm. 
     * 9 skruer per bræt. 
     * 3x 4,5x50mm og 6x 4,5x70mm. 
     * 1 løsholt 20 cm over jorden. 
     * 1 løsholt 110 cm over jorden. 
     * 1 for hver anden stolpe. 45x95 
     * 1 ekstra løsholt i enderne, fordi den øverste i siderne er remmene 
     * løsholter monteres i vinkelbeslag
     * 4 beslagsskruer per beslagsflade.
     *
     * @param bom
     * @param wood
     * @param length
     * @param width
     */
    private void addMaterials(PartslistModel bom, MaterialModel wood, int length, int width, DataFacade db) throws LoginException
    {
        // Trykimp brædder til beklædning:
        int amountofwood = (12 * ((length / 300) + (width / 300))) * 2; // Two length sides and two width sides.
        wood.setQuantity(amountofwood);
        bom.addMaterial(wood);

        // Adding skruer for the beklædning.
        // Amount of Skruer 4,5x50mm used for beklædningsbrædder.
        int amountofskruer50 = 3 * amountofwood;
        MaterialModel skruer50 = db.getMaterial(27); // 300 in one pack.
        addScrews(bom, db, skruer50, 300, amountofskruer50);
        // Amount of Skruer 4,5x70mm used for beklædningsbrædder.
        int amountofskruer70 = 6 * amountofwood;
        MaterialModel skruer70 = db.getMaterial(26); // 400 in one pack.
        addScrews(bom, db, skruer70, 400, amountofskruer70);
        
        // Adding reglar. One side needs 3, and on the other side you just mount the beklædning on the rem.
        int vinkelbeslagamount = reglar(width, db, bom, 3);
        vinkelbeslagamount = vinkelbeslagamount + reglar(length, db, bom, 2);
        
        // Vinkelbeslag #19 2 per reglar
        MaterialModel vinkelbeslag = db.getMaterial(19);
        vinkelbeslag.setQuantity(vinkelbeslagamount);
        bom.addMaterial(vinkelbeslag);
        
        // Beslagsskruer #21 4 per beslag
        MaterialModel beslagsskruer = db.getMaterial(21);
        int amountofskruer = 4 * vinkelbeslagamount;
        int restskruer = amountofskruer % 100; // 100 in one pack
        int amountofpacks = amountofskruer / 100;
        if (restskruer > 0) // If customer needs another pack.
        {
            amountofpacks++;
        }
        beslagsskruer.setQuantity(amountofpacks);
        bom.addMaterial(beslagsskruer);
        
        
    }

    private int reglar(int width, DataFacade db, PartslistModel bom, int side) throws LoginException
    {
        // 2400 reglar #6
        int amountofreglar = side * (width/2000); // One is 2400 long. One pole each 2m. You can cover two poles with one 2400mm regel.
        MaterialModel reglar = db.getMaterial(6);
        int restreglar = width % 2000;
        if (restreglar > 0) // If customer needs ekstra.
        {
            amountofreglar = side + amountofreglar;
        }
        reglar.setQuantity(amountofreglar);
        bom.addMaterial(reglar);
        return amountofreglar * 2; // used for vinkelbeslag. Need 2 per reglar.
    }    
    
    /**
     * add screws to the partslist. 
     * @param bom
     * @param db
     * @param screws type of screw
     * @param packamount amount of screws in a pack.
     * @param screwamount amount of screws needed in total.
     */
    private void addScrews(PartslistModel bom, DataFacade db, MaterialModel screws, int packamount, int screwamount){
        int restskruer = screwamount % packamount;
        int amountofpacks = screwamount / packamount;
        if (restskruer > 0)
        {
            amountofpacks++;
        }
        screws.setQuantity(amountofpacks);
        bom.addMaterial(screws);
    }
}