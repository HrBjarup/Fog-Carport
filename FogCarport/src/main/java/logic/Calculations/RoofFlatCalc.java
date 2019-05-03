package logic.Calculations;

import data.DataFacade;
import data.DataFacadeImpl;
import data.exceptions.AlgorithmException;
import data.exceptions.LoginException;
import data.models.MaterialModel;
import data.models.OrderModel;
import data.models.PartslistModel;
import java.util.HashMap;
import javafx.scene.paint.Material;

/**
 * This class handles materials needed for a flat roof on the carport.
 *
 * It is meant to return a PartslistModel with the items, which are then
 * appended to the 'master list' ("bill of materials").
 *
 * @see PartslistModel
 *
 * @author Runi
 *
 * Material used for creation:
 * https://datsoftlyngby.github.io/dat2sem2019Spring/Modul4/Fog/CP01_DUR.pdf
 *
 * Material rules:
 *
 * Spær: Every 50cm
 *
 * Stern: 8x (two per building side: one under, one over)
 *
 *
 * Vandbrædt: 3x (size: 19x100. 1x front, 1x each side)
 *
 * Universalbeslag: 2x per spær. (Remember: Right/Left orientation) (9 screws
 * per beslag)
 *
 * Hulbånd: 1x (10m). 2 screws pr. spær
 *
 * !Plastic roofing:
 *
 * !Outermost parts must extend 5cm beyond its Spær to account for water.
 *
 * !Roof parts have a 2 'wave' overlay (10cm?) 12 screws per cm^2.
 *
 * Tagpap roofing: Size dependant calculation
 *
 * Lægter: To be determined
 *
 *
 */
public class RoofFlatCalc
{

    private int amountOfScrews; //total amount of screws needed
    private DataFacade DAO; //data accessor
    private PartslistModel roofMaterials; //items to be returned to master list
    Rules rules;
    Helptext helptext;

//    public RoofFlatCalc(DataFacade dataF, PartslistModel roofMaterials, HashMap<String, Integer> rules)
//    {
//        this.amountOfScrews = 0;
//        this.dataF = dataF;
//        this.roofMaterials = roofMaterials;
//        this.rules = rules;
//    }
    public RoofFlatCalc()
    {
        amountOfScrews = 0;
        this.DAO = DataFacadeImpl.getInstance();
        this.roofMaterials = new PartslistModel();
        rules = new Rules();
        //helptext = new Helptext();
    }

    /**
     * Master calculation; takes use of other methods to calculate all the parts
     * needed for a flat roof.
     *
     * @param order
     * @return
     * @throws data.exceptions.AlgorithmException
     * @throws data.exceptions.LoginException
     */
    public PartslistModel calculateFlatRoofStructure(OrderModel order) throws AlgorithmException, LoginException
    {
        /* calculate always needed (independent) items */
        roofMaterials.addPartslist(calculateMainParts(order));
        /* calculate items based on type of roof tile */
        roofMaterials.addPartslist(calculateDependantParts(order));
        /* calculate other */
        roofMaterials.addPartslist(calculateMiscellaneous(order));

        return roofMaterials;
    }

    /**
     *
     * Calculates main parts that are generally always needed for the flat roof
     * part of the construction.
     *
     * @param order order in question
     *
     * @return
     * @throws data.exceptions.LoginException
     */
    protected PartslistModel calculateMainParts(OrderModel order) throws LoginException
    {
        PartslistModel woodMaterials = new PartslistModel();

        /* Get materials */
        //MaterialModel bargeboards = DAO.getMaterial(54); //update id
        //MaterialModel bands = DAO.getMaterial(54); //update id
        /* set helptext */
 /* Calculate amount of materials needed */
        PartslistModel rafters = calculateRafters(order);
        PartslistModel fascias = calculateFascias(order);
        PartslistModel bargeboards = calculateBargeboard(order);
        PartslistModel bands = calculateBand(order);
        PartslistModel fittings = calculateFittings(rafters);

        /* update quantities */
        // rafters.setQuantity(rafters);
        //fascias.setQuantity(fascias);
        // bargeboards.setQuantity(bargeboards);
        // bands.setQuantity(bands);
        // fittings.setQuantity(fittings);
        /* Add material*/
        woodMaterials.addPartslist(rafters);
        woodMaterials.addPartslist(fascias);
        woodMaterials.addPartslist(bargeboards);
        woodMaterials.addPartslist(bands);
        woodMaterials.addPartslist(fittings);

        return woodMaterials;

    }

    /**
     * Adds parts that depends on the roof of choice
     *
     * @param order
     * @return
     * @throws data.exceptions.AlgorithmException
     */
    protected PartslistModel calculateDependantParts(OrderModel order) throws AlgorithmException
    {
        PartslistModel dependantParts = new PartslistModel();

        int roofSelection = order.getRoof_tiles_id();
        //the ID selected for roof tiles. 
        //0 = no roof/no choice. || 28,* 29 = plastic. Other = felt ("tagpap").

        switch (roofSelection) //could also be done with multiple if-statements
        {
            //plastic roof
            case 28:
            case 29:
                dependantParts.addPartslist(calculatePlasticRoof(order));

                break;
            //felt roof
            case 47:
            case 48:
                dependantParts.addPartslist(calculateFeltRoof(order));
                break;
            default:
                throw new AlgorithmException(1, "Error #1: No suitable roof ID selected");
        }
        return dependantParts;
    }

    /**
     * Calculates materials needed for the flat plastic roof
     *
     * @return
     */
    private PartslistModel calculatePlasticRoof(OrderModel order)
    {
        /* Set up return <model>*/
        PartslistModel plasticRoof = new PartslistModel();

        /* Get MaterialModel to return */

 /* Calculation begin */

 /* Update quantities */

 /* Update price */

 /* Update helptext */

 /* Add to <model> */

 /* Return <model>*/
        return plasticRoof;
    }

    /**
     * calculates materials needed for the flat felt roof.
     *
     * @param order
     * @return
     */
    private PartslistModel calculateFeltRoof(OrderModel order)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param order
     * @return
     */
    protected PartslistModel calculateMiscellaneous(OrderModel order)
    {
        PartslistModel miscMaterials = new PartslistModel();

        //return miscMaterials;
        //return new UnsupportedOperationException("wtf");
        return null;
    }

    /**
     * calculates rafter ("spær") for carport roof
     *
     * @param order
     * @return
     */
    private PartslistModel calculateRafters(OrderModel order) throws LoginException
    {
        MaterialModel rafters = DAO.getMaterial(54);
        return null;
    }

    /**
     * calculates fascia ("stern") for carport roof
     *
     * @param order
     * @return
     */
    private PartslistModel calculateFascias(OrderModel order) throws LoginException
    {
        PartslistModel fascias = new PartslistModel();

        /* set up variables */
        int width = order.getWidth();
        int length = order.getLength();

        /* get materials from DB */
        MaterialModel fascia4800 = DAO.getMaterial(1); //length 4800mm
        MaterialModel fascia5400 = DAO.getMaterial(2); //length 5400mm
        MaterialModel fascia6000 = DAO.getMaterial(3); //length 6000mm

        /* Begin calculation, add to partslist */
        fasciaHelper(fascias, length);
        fasciaHelper(fascias, width);

        /* return partslist */
        return fascias;
    }

    /**
     * Helps calculating amount of Fascias depending on material length and
     * remaining length.
     *
     * @param remainingLength
     * @return
     * @throws LoginException
     */
    private PartslistModel fasciaHelper(PartslistModel fascias, int remainingLength) throws LoginException
    {
        //PartslistModel fascias = new PartslistModel(); //needs to initialize
        MaterialModel fascia = null;

        while (remainingLength > 0)
        {
            if (remainingLength < 4800) //would have been nice to use MaterialModel.getLength but then we would have to load it from db.
            {
                fascia = DAO.getMaterial(1); //length 4800
            }
            else if (remainingLength > 4800 && remainingLength < 5400)
            {
                fascia = DAO.getMaterial(2); //length 5400
            }
            else if (remainingLength > 5400)
            {
                fascia = DAO.getMaterial(3); //length 6000
            }

            if (fascia != null)
            {
                int qty = materialCalculateAmount(fascia, remainingLength);
                int fasciaStandard = rules.get("fasciaStandard"); //2 fascia per side of the house
                qty = (qty) * (fasciaStandard) * (2); //qty * 2 boards * 2 sides per carport
                fascia.setQuantity(qty);
                int lengthDifference = (remainingLength) - (fascia.getLength() * qty);
                remainingLength -= lengthDifference;
                fascias.addMaterial(fascia);
                fasciaHelper(fascias, remainingLength); //recursion
            }
        }
        return fascias;

//        while (remainingLength > fasciaLength) // || remainingLength > 0
//        {
//            amountOfFascia++;
//            remainingLength -= fasciaLength;
//        }
//
//        return amountOfFascia;
    }

    /**
     * Calculates amount of the specific material needed to reach optimal
     * size/price usage.
     *
     * @param item item in question (MaterialModel)
     * @param remainingLength length needed to calculate
     * @return
     */
    private int materialCalculateAmount(MaterialModel item, int remainingLength)
    {
        int itemLength = item.getLength();
        int itemQty = item.getQuantity();

        while (remainingLength > 0 || remainingLength >= itemLength)
        {
            itemQty++;
            remainingLength -= itemLength;
        }

        if (remainingLength > 0 && remainingLength < 4800) //smallest value

        {
            return itemQty;
        }
        return 0;
    }

    /**
     * calculates bargeboard ("vandbrædt") for carport roof
     *
     * @param order
     * @return
     */
    private PartslistModel calculateBargeboard(OrderModel order)
    {
        return null;
    }

    /**
     * calculate universalbeslag & screws for carport roof
     *
     * @param rafterAmount amount of rafters needed. calculated in earlier
     * method.
     * @return returns a list of materials (to later add to bill of materials)
     */
    private PartslistModel calculateFittings(PartslistModel rafters) throws LoginException
    {
        /* Rafter amount */
        int rafterAmount = rafters.getBillOfMaterials().get(0).getQuantity(); //hackish solution. update?

        /*PartsListModel to return */
        PartslistModel fittingsAndScrews = new PartslistModel();

        /* Get standards */
        int fittingStandard = rules.get("rafterFittings"); //2
        int screwStandard = rules.get("fittingScrews"); //9

        /* Calculation begin */
        int fittingsAmount = (rafterAmount * fittingStandard); //2 fittings per rafter
        //int screwAmount = (fittingsAmount*screwStandard); //9 screws per fitting

        /* Get materials from database */
        MaterialModel fittingRight = DAO.getMaterial(15);
        MaterialModel fittingLeft = DAO.getMaterial(16);
        MaterialModel fittingScrews = DAO.getMaterial(21);
        //amountOfScrews += (fittingsAmount * screwAmount);

        /* update quantities */
        fittingRight.setQuantity(fittingsAmount);
        fittingLeft.setQuantity(fittingsAmount);
        fittingScrews.setQuantity(1); //1 pack is 250
        //setScrewQty 

        /* update price */
        fittingRight.setPrice((fittingRight.getPrice() * fittingRight.getQuantity()));
        fittingLeft.setPrice((fittingLeft.getPrice() * fittingLeft.getQuantity()));
        //setScrewPrice 

        /* Update helptext */
        //fittingRight.setHelptext(helptext.get(fittingRight));
        //fittingLeft.setHelptext(helptext.get(fittingLeft));
        fittingRight.setHelptext("Til montering	af spær	på rem");
        fittingLeft.setHelptext("Til montering	af spær	på rem");
        fittingScrews.setHelptext("Til montering af universalbeslag + hulbånd");


        /* Add to PartsListModel */
        fittingsAndScrews.addMaterial(fittingRight);
        fittingsAndScrews.addMaterial(fittingLeft);
        fittingsAndScrews.addMaterial(fittingScrews);
        //addScrews 

        /* Return PartsListModel */
        return fittingsAndScrews;
    }

    /**
     * calculate hulbånd for carport roof
     *
     * @param order
     * @return
     */
    private PartslistModel calculateBand(OrderModel order) throws LoginException
    {
        PartslistModel bandParts = new PartslistModel();
        int bandAmount = 1; //used to determine band quantity. we always want one.

        /*get MaterialModel to return */
        MaterialModel band = DAO.getMaterial(23);
        MaterialModel bandScrews = DAO.getMaterial(21);

        /* Calculation begin */
        int bandLength = band.getLength(); //10000mm (10m)
        int shedLength = order.getShed_length();
        int carportLength = order.getLength();

        //band runs from shed to front
        int bandCoverLength = (carportLength - shedLength); //band does not cover shed

        int bandEffectiveLength = bandCoverLength * 2; //we need to cover diagonally (two lengths)

        bandEffectiveLength -= bandLength; //we start at one, so lets remove bandLength from calculation.
        while (bandEffectiveLength - bandLength >= 0)
        {
            ++bandAmount;
            bandEffectiveLength -= bandLength;
        }

        /* update quantities */
        band.setQuantity(bandAmount);
        bandScrews.setQuantity(1); // 250 a package
        //screws too (2 pr. spær crossed)

        /* update price */
        band.setPrice(band.getQuantity() * band.getPrice());
        /* Update helptext */
        band.setHelptext("Til vindkryds på spær");
        bandScrews.setHelptext("Til montering af universalbeslag + hulbånd");
        /* add to PartslistModel */
        bandParts.addMaterial(band);
        bandParts.addMaterial(bandScrews);

        /* Return PartslistModel */
        return bandParts;

    }

    /**
     * Calculates roof tiles (plastic) for carport roof
     *
     * @param order
     * @return
     */
    private PartslistModel calculatePlasticTiles(OrderModel order)
    {
        return null;
    }

    /**
     * Calculates roof tiles (felt) ("tagpap") for carport roof
     *
     * @param order
     * @return
     */
    private PartslistModel calculateFeltTiles(OrderModel order)
    {
        return null;
    }

    private static class Rules extends HashMap<String, Integer>
    {

        public Rules()
        {
            this.put("rafterLength", 500); //1 rafter per 500mm (50cm)
            this.put("fasciaStandard", 2); //2 boards per side (one lower, one upper) = 8 boards total.
            this.put("bargeStandard", 3); //1 board for every side except back. (due to carport tilt)
            this.put("rafterFittings", 2); //2 fittings per rafter
            this.put("fittingScrews", 9); // 9 screws per fitting

            this.put("PlasticTileExtend", 50); //plastic tiles extend 50mm (5cm) beyond the roof edge
            this.put("PlasticTileOverlap", 100); //Plastic tiles overlap eachother approximately 100mm (10cm)
            this.put("PlasticTileScrews", 12); //12 screws per cm^2

            /*this.put("band", -1); */ //hulbånd not needed
        }
    }

    private class Helptext extends HashMap<MaterialModel, String>
    {

        public Helptext() throws LoginException
        {
            this.put(DAO.getMaterial(15), "whatup");
        }

    }

}