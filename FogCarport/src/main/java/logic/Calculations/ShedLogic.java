/*
 *  
 */
package logic.Calculations;

import data.DataFacade;
import data.DataFacadeImpl;
import data.exceptions.DataException;
import data.models.MaterialModel;
import data.models.OrderModel;
import data.models.PartslistModel;

/**
 * Contains logic regarding the Bill of Materials for a shed in the carport.
 * Adds materials needed to build a shed in the carport to the partslist.
 *   
 *   Going off of this with regards to assumptions.
 *   https://datsoftlyngby.github.io/dat2sem2019Spring/Modul4/Fog/CAR01_HR.pdf
 *   
 *   Things to take into account: 
 *   Width of the shed
 *   Length of the shed
 *   Type of wood used for the shed
 *   Type of wood used for the floor (if they even want a floor)
 * 
 * @author
 */
public class ShedLogic
{

    /*
    MATERIALS - edit here if you want new default materials.
     */
    private final int skruer70mm = 26; // 4,5x70mm. skruer (400 stk)
    private final int skruer50mm = 27; // 4,5x50mm Skruer (300 stk)
    private final int stalddørsgreb = 17; // Stalddørsgreb 50x75
    private final int taglægte = 12; // 38x73 mm. taglægte T1
    private final int thængsel = 18; // T-hængsel 390mm
    private final int vinkelbeslag = 19; // Vinkelbeslag
    private final int beslagsskruer = 21; // 5,0x40mm. Beslagskruer (250 stk)
    private final int reglar2400 = 6; // 45x95 Reglar ubh. 240cm
    private final int reglar3600 = 7; // 45x95 Reglar ubh. 360cm
    private final int postid = 4; // 97x97 mm. trykimp. Stolpe

    /* 
    Other info
    */
    private final int postdistance = 3100; // Distance between posts.
    private final String helptext = "shed"; // indicator for what helptext to grab from database.

    public ShedLogic()
    {
    }
    
    /**
     * Get a PartslistModel containing all materials needed to add a shed to the carport.
     * This is the only public method in this class.
     * @param order containing info about the shed and carport.
     * @return PartslistModel
     * @throws DataException 
     */
    public PartslistModel addShed(OrderModel order) throws DataException
    {
        // The partslist we're going to return.
        PartslistModel bom = new PartslistModel();

        // Guard
        if (order == null)
        {
            throw new DataException("Ordren er tom.");
        }
        // If the customer doesn't want a shed.
        if (order.getShed_walls_id() == 0
                || order.getShed_length() == 0
                || order.getShed_width() == 0)
        {
            return bom;
        }

        // DataFacade, so we can use it's methods and get materials.
        DataFacade db = DataFacadeImpl.getInstance();

        /* 
        "Standard" was an old feature that is now deprecated. 
        Used in the first sprint for the simple partslist. 
         */
//        boolean standard = false;
//        if (standard == true)
//        {
//            simpleShed(bom);
//        } else
        // VARIABLES:
        int width = order.getShed_width();
        int length = order.getShed_length();

        // The type of wood used for the walls on the shed. No quantity on it yet.
        MaterialModel wood = db.getMaterial(order.getShed_walls_id(), helptext);

        // MATERIALS NEEDED NO MATTER WHAT FOR THE DOOR
        addDoorMaterials(bom, db);

        // IF FLOOR IS CHOSEN:
        if (order.getShed_floor_id() != 0)
        {
            // Get the floor-info out of the Database.
            MaterialModel floor = db.getMaterial(order.getShed_floor_id(), helptext);
            // ADD X AMOUNT OF FLOOR DEPENDING ON WIDTH AND LENGTH TO THE PARTSLIST.
            addFloor(bom, floor, length, width, db);
        }

        // AND THE REST OF THE MATERIALS.
        addMaterials(bom, wood, length, width, db, order);

        return bom;
    }

    //<editor-fold defaultstate="collapsed" desc="MATERIALS FOR STANDARD SHED">
    /**
     * Materials for the standard, simple shed. Made in first sprint. Maybe we
     * won't use this in production.
     *
     * @param bom
     */
    @Deprecated
    void simpleShed(PartslistModel bom)
    {
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
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MATERIALS FOR THE FLOOR">
    /**
     * Adds materials to the Partslist for the floor, if selected.
     *
     * @param floor MaterialModel
     * @param length of the Shed.
     * @param width of the Shed.
     * @return PartslistModel
     */
    void addFloor(PartslistModel bom, MaterialModel floor, int length, int width, DataFacade db) throws DataException
    {
        int floorwidth = floor.getWidth(); // Width of the boards used for the floor
        int floorlength = floor.getLength(); // Length of the boards used for the floor

        int woodwidthamount = width / floorwidth; // Get amount of boards needed for width of shed
        if (width % floorwidth > 0)
        {
            woodwidthamount++; // If we need an extra to get 100% coverage.
        }

        int woodlengthamount = length / floorlength; // Get amount of boards needed for length of shed
        if (length % floorlength > 0)
        {
            woodlengthamount++; // If we need an extra to get 100% coverage.
        }

        int amountofwood = woodlengthamount * woodwidthamount; // Length * Width = amount of boards needed in total for shed floor.
        floor.setQuantity(amountofwood);
        bom.addMaterial(floor);

        // 4 screws per board #27 - 300 in a pack
        int screwamount = amountofwood * 4;
        MaterialModel screws = db.getMaterial(this.skruer50mm, helptext); // 300 in one pack.
        addScrews(bom, screws, 300, screwamount);
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MATERIALS FOR THE DOOR">
    /**
     * Adds always needed materials to the Partslist. Materials for the door,
     * etc. 2 vandrette stivere 38x73 2 lag beklædningsbrædder skråstiver lægte
     * dørgreb - stalddørsgreb - #17 material hængsler skruer til alt dette
     *
     * @param wood type of wood selected for the shed.
     * @return PartslistModel
     */
    void addDoorMaterials(PartslistModel bom, DataFacade db) throws DataException
    {
        MaterialModel stalddørsgreb = db.getMaterial(this.stalddørsgreb, helptext);
        stalddørsgreb.setQuantity(1);
        bom.addMaterial(stalddørsgreb); // Stalddørsgreb for the door.

        MaterialModel laegte = db.getMaterial(taglægte, helptext); // 38x73mm taglægte.
        laegte.setQuantity(1);
        bom.addMaterial(laegte); // for the backside of the door.

        MaterialModel hængsel = db.getMaterial(thængsel, helptext);
        hængsel.setQuantity(2);
        bom.addMaterial(hængsel); // T-hængsel for the door.
    }
    //</editor-fold>

    /**
     * Materials for the rest. 
     * 12 boards per 30cm. 
     * 9 screws per board. 
     * 3x 4,5x50mm and 6x 4,5x70mm. 
     * 1 reglar 20 cm above ground. 
     * 1 reglar 110 cm above ground. 
     * 1 reglar for every second post. 
     * 1 extra reglar at the ends of the shed, because the top one on the sides are straps 
     * install girders in angle brackets
     * 4 bracket screws per bracket.
     *
     * @param bom PartslistModel
     * @param wood MaterialModel. Wood used for the sheds walls.
     * @param length of the shed
     * @param width of the shed
     */
    void addMaterials(PartslistModel bom, MaterialModel wood, int length, int width, DataFacade db, OrderModel order) throws DataException
    {
        // pressure treated boards for the walls:
        int amountofwood = (((length + width) * 2) / wood.getWidth()) + 1; // Two length sides and two width sides and one spare board.
        wood.setQuantity(amountofwood);
        bom.addMaterial(wood);

        // Adding screws for the walls.
        // Amount of Skruer 4,5x50mm used for the boards for the wall.
        int amountofscrews50 = 3 * amountofwood;
        MaterialModel skruer50 = db.getMaterial(this.skruer50mm, helptext); // 300 in one pack.
        addScrews(bom, skruer50, 300, amountofscrews50);
        // Amount of Skruer 4,5x70mm used for the boards for the wall.
        int amountofscrews70 = 6 * amountofwood;
        MaterialModel skruer70 = db.getMaterial(this.skruer70mm, helptext); // 400 in one pack.
        addScrews(bom, skruer70, 400, amountofscrews70);

        // Adding additional posts if needed
        posts(length, order, width, db, bom);

        // Adding reglar. One side needs 3, and on the other side you just mount the walls on the strap.
        int vinkelbeslagamount = reglar(width, db, bom, 3);
        vinkelbeslagamount += reglar(length, db, bom, 2);

        // anglebrackets #19 2 per reglar
        MaterialModel vinkelbeslag = db.getMaterial(this.vinkelbeslag, helptext);
        vinkelbeslag.setQuantity(vinkelbeslagamount);
        bom.addMaterial(vinkelbeslag);

        // bracket screws #21 4 per bracket
        MaterialModel beslagsskruer = db.getMaterial(this.beslagsskruer, helptext);
        int screwamount = 4 * vinkelbeslagamount;
        addScrews(bom, beslagsskruer, 100, screwamount);

    }

    /**
     * Calculate the amount of posts needed extra for the shed.
     *
     * @param length of the shed.
     * @param order
     * @param width of the shed.
     * @param db
     * @param bom
     * @throws LoginException
     */
    void posts(int length, OrderModel order, int width, DataFacade db, PartslistModel bom) throws DataException
    {
        int postquantity = 0;
        if (length == order.getLength() && width == order.getWidth())
        {
            // If the shed covers the entire carport, then add no additional posts.
        } else if ((width < order.getWidth() && length > postdistance) || (length < order.getLength() && width > postdistance)) // if carport is absurdly long and shed is aswell...
        {
            postquantity++; //one for the corner
            if (length > postdistance) // We only need additional posts if the length is longer than the distance between two posts.
            {
                int templength = length;
                postquantity--; //one removed for the one at the end.
                while (templength > 0)
                {
                    postquantity++; // Add post
                    templength -= postdistance; // Remove the distance.
                }
            }

            if (width > postdistance) // We only need additional posts if the width is longer than the distance between two posts.
            {
                int tempwidth = width;
                postquantity--; //one removed for the one at the end.
                while (tempwidth > 0)
                {
                    postquantity++; // Add a post
                    tempwidth -= postdistance; // Remove the distance.
                }
            }
        } else
        {
            postquantity++; // the one at the corner
        }
        MaterialModel post = db.getMaterial(postid, helptext);
        post.setQuantity(postquantity);
        bom.addMaterial(post);
    }

    /**
     * Calculate the amount of reglar needed.
     *
     * @param width of the side you're calculating.
     * @param db
     * @param bom
     * @param side one side needs 2, another needs 3.
     * @return amount of reglar for that side.
     * @throws LoginException
     */
    int reglar(int width, DataFacade db, PartslistModel bom, int side) throws DataException
    {
        MaterialModel reglar;
        if (postdistance < 2400 || width < 2400)
        {
            reglar = db.getMaterial(this.reglar2400, helptext); // 2400 reglar
        } else
        {
            reglar = db.getMaterial(this.reglar3600, helptext); // 3600 reglar
        }
        int amountofreglar = side * (width / postdistance);
        int restreglar = width % postdistance;
        if (restreglar > 0) // If customer needs ekstra.
        {
            amountofreglar = side + amountofreglar;
        }
        reglar.setQuantity(amountofreglar);
        bom.addMaterial(reglar);
        return amountofreglar * 2; // used for angle brackets. Need 2 per reglar.
    }

    //<editor-fold defaultstate="collapsed" desc="CALCULATOR FOR THE SCREWS">
    /**
     * Add screws to the partslist.
     *
     * @param bom
     * @param screws type of screw
     * @param packamount amount of screws in a pack.
     * @param screwamount amount of screws needed in total.
     */
    void addScrews(PartslistModel bom, MaterialModel screws, int packamount, int screwamount)
    {
        int restskruer = screwamount % packamount;
        int amountofpacks = screwamount / packamount;
        if (restskruer > 0)
        {
            amountofpacks++;
        }
        screws.setQuantity(amountofpacks);
        bom.addMaterial(screws);
    }
    //</editor-fold>
}
